package movie.respository;

import movie.domain.Genre;
import movie.domain.Movie;
import share.OracleData;
import share.StatementMaker;

import java.sql.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// MOVIE_ID, FEE, GENRE, RUNNING_TIME, TITLE
public class MovieRepository {
    OracleData dataSource = OracleData.getInstance();

    public void insert(Movie movie) {
        jdbcTemplate(con -> {
            String insertQuery = "insert into movie values(HIBERNATE_SEQUENCE.NEXTVAL, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setInt(1, movie.getFee());
            ps.setString(2, movie.getGenre().toString());
            ps.setLong(3, movie.getRunningTime().toMinutes());
            ps.setString(4, movie.getTitle());
            return ps;
        });
    }


    public Optional<Movie> selectById(Long movieId) {
        Optional<Movie> movie = Optional.empty();

        String selectQuery = "select * from MOVIE where MOVIE_ID = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)) {
            ps.setLong(1, movieId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    movie = Optional.of(movieMapper(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public Optional<Movie> selectByTitle(String title) {
        Optional<Movie> movie = Optional.empty();
        String selectQuery = "select * from movie where title = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    movie = Optional.of(movieMapper(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public List<Movie> selectAll() {
        List<Movie> members = new ArrayList<>();
        Movie movie = null;
        String selectQuery = "select * from MOVIE";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    movie = movieMapper(rs);
                    members.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public void delete(Long movieId) {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from MOVIE where MOVIE_ID = ?";
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setLong(1, movieId);
            return ps;
        });
    }

    public void deleteAll() {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from MOVIE";
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

    // MOVIE_ID, FEE, GENRE, RUNNING_TIME, TITLE
    private Movie movieMapper(ResultSet rs) throws SQLException {
        Long movieId = rs.getLong(1);
        int fee = rs.getInt(2);
        Genre genre = Genre.valueOf(rs.getString(3));
        Duration runningTime = Duration.of(rs.getLong(4), ChronoUnit.MINUTES);
        String title = rs.getString(5);

        return new Movie(movieId, title, genre, runningTime, fee);
    }
}
