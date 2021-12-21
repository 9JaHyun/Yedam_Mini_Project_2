package cinema.repository;

import cinema.domain.Theater;
import share.OracleData;
import share.StatementMaker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TheaterRepository {
    OracleData dataSource = OracleData.getInstance();
    private final CinemaRepository cinemaRepository = new CinemaRepository();

    public void insert(Theater theater) {
        jdbcTemplate(con -> {
            String insertQuery = "insert into THEATER values(HIBERNATE_SEQUENCE.NEXTVAL, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setString(1, theater.getName());
            ps.setLong(2, theater.getCinema().getId());
            return ps;
        });
    }

    public Optional<Theater> selectById(Long screeningId) {
        Optional<Theater> theater = Optional.empty();
        String selectQuery = "select * from THEATER where THEATER_ID = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)){
            ps.setLong(1, screeningId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    theater = Optional.of(theaterMapper(rs));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return theater;
    }
    public List<Theater> selectByCinemaId(Long cinemaId) {
        List<Theater> theaters = new ArrayList<>();
        Theater theater = null;
        String selectQuery = "select * from THEATER where CINEMA_ID = ?";
        try(Connection con = dataSource.connect();
            PreparedStatement ps = con.prepareStatement(selectQuery)) {
            ps.setLong(1, cinemaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    theater = theaterMapper(rs);
                    theaters.add(theater);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theaters;
    }


    public List<Theater> selectAll() {
        List<Theater> theaters = new ArrayList<>();
        Theater theater = null;
        String selectQuery = "select * from THEATER";
        try(Connection con = dataSource.connect();
            PreparedStatement ps = con.prepareStatement(selectQuery)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    theater = theaterMapper(rs);
                    theaters.add(theater);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theaters;
    }

    public void delete(Long theaterId) {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from THEATER where THEATER_ID = ?";
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setLong(1, theaterId);
            return ps;
        });
    }

    public void deleteAll() {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from THEATER";
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

    private Theater theaterMapper(ResultSet rs) throws SQLException {
        Long theaterId = rs.getLong(1);
        String name = rs.getString(2);
        long cinemaId = rs.getLong(3);

        return new Theater(theaterId, name, cinemaRepository.selectById(cinemaId).get());
    }


}

