package cinema.repository;

import cinema.domain.Cinema;
import cinema.domain.Seat;
import cinema.domain.SeatRow;
import cinema.domain.SeatStatus;
import share.OracleData;
import share.StatementMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeatRepository {
    OracleData dataSource = OracleData.getInstance();
    SeatRowRepository seatRowRepository = new SeatRowRepository();

    public void insert(Seat seat) {
        jdbcTemplate(con -> {
            String insertQuery = "insert into SEAT values(HIBERNATE_SEQUENCE.NEXTVAL, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setInt(1, seat.getColumn());
            ps.setString(2, seat.getSeatStatus().toString());
            ps.setLong(3, seat.getRow().getId());
            return ps;
        });
    }

    public Optional<Seat> selectById(Long seatId) {
        Optional<Seat> seat = Optional.empty();
        String selectQuery = "select * from SEAT where SEAT_ID = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)){
            ps.setLong(1, seatId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seat = Optional.of(seatMapper(rs));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return seat;
    }

    public Optional<Seat> selectByColNumber(int col) {
        Optional<Seat> seat = Optional.empty();
        String selectQuery = "select * from SEAT where SEAT_COL_NUMBER = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)){
            ps.setInt(1, col);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seat = Optional.of(seatMapper(rs));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return seat;
    }

    public List<Seat> selectByRowNumber(Long rowNumber) {
        List<Seat> seats = new ArrayList<>();
        Seat seat = null;
        String selectQuery = "select * from SEAT WHERE ROW_ID = ?";
        try(Connection con = dataSource.connect();
            PreparedStatement ps = con.prepareStatement(selectQuery)) {
            ps.setLong(1, rowNumber);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seat = seatMapper(rs);
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public List<Seat> selectAll() {
        List<Seat> seats = new ArrayList<>();
        Seat seat = null;
        String selectQuery = "select * from SEAT";
        try(Connection con = dataSource.connect();
            PreparedStatement ps = con.prepareStatement(selectQuery)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seat = seatMapper(rs);
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public void delete(Long seatId) {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from SEAT where SEAT_ID = ?";
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setLong(1, seatId);
            return ps;
        });
    }

    public void deleteAll() {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from SEAT";
            PreparedStatement ps = con.prepareStatement(deleteQuery);
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

    private Seat seatMapper(ResultSet rs) throws SQLException {
        Long seatId = rs.getLong(1);
        int seatColNumber = rs.getInt(2);
        SeatStatus seatStatus = SeatStatus.valueOf(rs.getString(3));
        long seatRowId = rs.getLong(4);
        SeatRow seatRow = seatRowRepository.selectById(seatRowId)
                .orElseThrow(() -> new AssertionError("해당 seatrow가 존재하지 않습니다. seatRowId: " + seatRowId));

        return new Seat(seatId, seatColNumber, seatStatus, seatRow);
    }
}
