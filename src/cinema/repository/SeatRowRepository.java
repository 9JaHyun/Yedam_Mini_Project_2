package cinema.repository;

import cinema.domain.Seat;
import cinema.domain.SeatRow;
import cinema.domain.SeatStatus;
import cinema.domain.Theater;
import share.OracleData;
import share.StatementMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeatRowRepository {
    OracleData dataSource = OracleData.getInstance();
    TheaterRepository theaterRepository = new TheaterRepository();

    public void insert(SeatRow seatRow) {
        jdbcTemplate(con -> {
            String insertQuery = "insert into SEAT_ROW values(HIBERNATE_SEQUENCE.NEXTVAL, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setString(1, seatRow.getRowName());
            ps.setLong(2, seatRow.getTheater().getId());
            return ps;
        });
    }

    public Optional<SeatRow> selectById(Long seatRowId) {
        Optional<SeatRow> seatRow = Optional.empty();
        String selectQuery = "select * from SEAT_ROW where SEAT_ROW_ID = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)){
            ps.setLong(1, seatRowId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seatRow = Optional.of(seatRowMapper(rs));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return seatRow;
    }

    public Optional<SeatRow> selectByRow(String rowNum) {
        Optional<SeatRow> seatRow = Optional.empty();
        String selectQuery = "select * from SEAT_ROW where SEAT_ROW_NUMBER = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)){
            ps.setString(1, rowNum);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seatRow = Optional.of(seatRowMapper(rs));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return seatRow;
    }

    public List<SeatRow> selectByTheater(Long theaterId) {
        List<SeatRow> seats = new ArrayList<>();
        SeatRow seat = null;
        String selectQuery = "select * from SEAT_ROW where THEATER_ID = ?";
        try(Connection con = dataSource.connect();
            PreparedStatement ps = con.prepareStatement(selectQuery)) {
            ps.setLong(1, theaterId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seat = seatRowMapper(rs);
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public List<SeatRow> selectAll() {
        List<SeatRow> seats = new ArrayList<>();
        SeatRow seat = null;
        String selectQuery = "select * from SEAT_ROW";
        try(Connection con = dataSource.connect();
            PreparedStatement ps = con.prepareStatement(selectQuery)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seat = seatRowMapper(rs);
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public void delete(Long seatRowId) {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from SEAT_ROW where SEAT_ROW_ID = ?";
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setLong(1, seatRowId);
            return ps;
        });
    }

    public void deleteAll() {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from SEAT_ROW";
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

    private SeatRow seatRowMapper(ResultSet rs) throws SQLException {
        Long seatRowId = rs.getLong(1);
        String seatRowNumber = rs.getString(2);
        Theater theater = theaterRepository.selectById(rs.getLong(3)).get();

        return new SeatRow(seatRowId, seatRowNumber, theater);
    }


}
