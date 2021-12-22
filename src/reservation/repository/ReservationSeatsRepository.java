package reservation.repository;

import cinema.domain.Seat;
import cinema.repository.SeatRepository;
import reservation.domain.Reservation;
import reservation.domain.ReservationSeats;
import share.OracleData;
import share.StatementMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationSeatsRepository {
    OracleData dataSource = OracleData.getInstance();
    ReservationRepository reservationRepository = new ReservationRepository();
    SeatRepository seatRepository = new SeatRepository();

    public void insert(ReservationSeats seats) {
        jdbcTemplate(con -> {
            String insertQuery = "insert into RESERVATION_SEATS values(HIBERNATE_SEQUENCE.NEXTVAL, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setLong(1, seats.getReservation().getId());
            ps.setLong(2, seats.getSeat().getId());
            return ps;
        });
    }

    public Optional<ReservationSeats> selectById(Long seatId) {
        Optional<ReservationSeats> seat = Optional.empty();
        String selectQuery = "select * from RESERVATION_SEATS where RESERVATION_SEATS_ID = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)){
            ps.setLong(1, seatId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seat = Optional.of(reservationSeatMapper(rs));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return seat;
    }

    public List<ReservationSeats> selectByReservationId(Long reservationId) {
        List<ReservationSeats> seats = new ArrayList<>();
        ReservationSeats seat = null;
        String selectQuery = "select * from RESERVATION_SEATS where RESERVATION_ID = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)){
            ps.setLong(1, reservationId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seats.add(reservationSeatMapper(rs));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    private ReservationSeats reservationSeatMapper(ResultSet rs) throws SQLException {
        Long seatId = rs.getLong(1);
        Reservation reservation = reservationRepository.selectById(rs.getLong(2)).get();
        Seat seat = seatRepository.selectById(rs.getLong(3)).get();

        return new ReservationSeats(seatId, reservation, seat);
    }

    public List<ReservationSeats> selectAll() {
        List<ReservationSeats> seats = new ArrayList<>();
        ReservationSeats seat = null;
        String selectQuery = "select * from RESERVATION";
        try(Connection con = dataSource.connect();
            PreparedStatement ps = con.prepareStatement(selectQuery)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seat = reservationSeatMapper(rs);
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public void deleteByReservationId(Long reservationId) {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from RESERVATION_SEATS where RESERVATION_ID = ?";
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
