package reservation.repository;

import member.Repository.MemberRepository;
import member.domain.Member;
import member.domain.MemberLevel;
import reservation.domain.Reservation;
import reservation.domain.ReservationStatus;
import screening.repository.ScreeningRepository;
import share.OracleData;
import share.StatementMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// RESERVATION_ID
// AUDIENCE_COUNT
// FEE
// STATUS
// MEMBER_ID
public class ReservationRepository {
    private static Long id = 1L;
    OracleData dataSource = OracleData.getInstance();
    MemberRepository memberRepository = new MemberRepository();
    ScreeningRepository screeningRepository = new ScreeningRepository();

    public Long insert(Reservation reserve) {
        jdbcTemplate(con -> {
            String insertQuery = "insert into RESERVATION values(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setLong(1, id);
            ps.setInt(2, reserve.getAudienceCount());
            ps.setInt(3, reserve.getFee());
            ps.setString(4, reserve.getStatus().toString());
            ps.setLong(5, reserve.getMember().getId());
            ps.setLong(6, reserve.getScreening().getId());
            return ps;
        });
        return id++;
    }

    public Optional<Reservation> selectById(Long reservationId) {
        Optional<Reservation> reservation = Optional.empty();
        String selectQuery = "select * from RESERVATION where reservation_id = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)){
            ps.setLong(1, reservationId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservation = Optional.of(reservationMapper(rs));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    public List<Reservation> selectByMemberId(Long memberId) {
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation = null;
        String selectQuery = "select * from RESERVATION where MEMBER_ID = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)){
            ps.setLong(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservations.add(reservationMapper(rs));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    private Reservation reservationMapper(ResultSet rs) throws SQLException {
        Long reservationId = rs.getLong(1);
        int audienceCount = rs.getInt(2);
        int fee = rs.getInt(3);
        ReservationStatus status = ReservationStatus.valueOf(rs.getString(4));
        Long memberId = rs.getLong(5);
        Long screeningId = rs.getLong(6);


        return new Reservation(reservationId, status,
                memberRepository.selectById(memberId),
                screeningRepository.selectById(screeningId).get(),
                fee, audienceCount);
    }

    public List<Reservation> selectAll() {
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation = null;
        String selectQuery = "select * from RESERVATION";
        try(Connection con = dataSource.connect();
            PreparedStatement ps = con.prepareStatement(selectQuery)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservation = reservationMapper(rs);
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public void delete(Long reservationId) {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from RESERVATION where member_id = ?";
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setLong(1, reservationId);
            return ps;
        });
    }

    private void jdbcTemplate(StatementMaker statementMaker) {
        try (Connection con = dataSource.connect();
             PreparedStatement ps = statementMaker.createPreparedStatement(con)) {
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
