package cinema.repository;

import cinema.domain.Cinema;
import share.OracleData;
import share.StatementMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// cinema_id, location, region, theater_id
public class CinemaRepository {
    OracleData dataSource = OracleData.getInstance();

    public void insert(Cinema cinema) {
        jdbcTemplate(con -> {
            String insertQuery = "insert into CINEMA values(HIBERNATE_SEQUENCE.NEXTVAL, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setString(1, cinema.getLocation());
            ps.setString(2, cinema.getRegion());
            return ps;
        });
    }

    public Optional<Cinema> selectById(Long cinemaId) {
        Optional<Cinema> cinema = Optional.empty();
        String selectQuery = "select * from CINEMA where CINEMA_ID = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)){
            ps.setLong(1, cinemaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cinema = Optional.of(cinemaMapper(rs));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return cinema;
    }

    public List<Cinema> selectAll() {
        List<Cinema> cinemas = new ArrayList<>();
        Cinema cinema = null;
        String selectQuery = "select * from CINEMA";
        try(Connection con = dataSource.connect();
            PreparedStatement ps = con.prepareStatement(selectQuery)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cinema = cinemaMapper(rs);
                    cinemas.add(cinema);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemas;
    }

    public void delete(Long cinemaId) {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from CINEMA where CINEMA_ID = ?";
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setLong(1, cinemaId);
            return ps;
        });
    }

    public void deleteAll() {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from CINEMA";
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

    // cinema_id, location, region, theater_id
    private Cinema cinemaMapper(ResultSet rs) throws SQLException {
        Long cinemaId = rs.getLong(1);
        String location = rs.getString(2);
        String region = rs.getString(3);

        return new Cinema(cinemaId, location, region);
    }
}
