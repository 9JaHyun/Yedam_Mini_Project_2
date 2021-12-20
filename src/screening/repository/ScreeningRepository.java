package screening.repository;

import cinema.domain.Cinema;
import cinema.repository.CinemaRepository;
import movie.domain.Movie;
import movie.respository.MovieRepository;
import screening.domain.Screening;
import share.OracleData;
import share.StatementMaker;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// cinema_id, location, region, theater_id
public class ScreeningRepository {
    OracleData dataSource = OracleData.getInstance();
    private final CinemaRepository cinemaRepository = new CinemaRepository();
    private final MovieRepository movieRepository = new MovieRepository();

    public void insert(Screening screening) {
        jdbcTemplate(con -> {
            String insertQuery = "insert into SCREENING values(HIBERNATE_SEQUENCE.NEXTVAL, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setInt(1, screening.getSeatAmount());
            ps.setTimestamp(2, Timestamp.valueOf(screening.getStartTime()));
            ps.setLong(3, screening.getCinema().getId());
            ps.setLong(4, screening.getMovie().getId());
            return ps;
        });
    }

    public Optional<Screening> selectById(Long screeningId) {
        Optional<Screening> screening = Optional.empty();
        String selectQuery = "select * from SCREENING where SCREENING_ID = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)){
            ps.setLong(1, screeningId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    screening = Optional.of(screeningMapper(rs));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return screening;
    }

    public List<Screening> selectByDate(LocalDate localDate) {
        List<Screening> screenings = new ArrayList<>();
        Screening screening = null;
        String selectQuery = "select * from SCREENING where START_TIME = ?";
        try (Connection con = dataSource.connect();
             PreparedStatement ps = con.prepareStatement(selectQuery)) {
            ps.setDate(1, Date.valueOf(localDate));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    screening = screeningMapper(rs);
                    screenings.add(screening);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screenings;
    }

    public List<Screening> selectAll() {
        List<Screening> screenings = new ArrayList<>();
        Screening screening = null;
        String selectQuery = "select * from SCREENING";
        try(Connection con = dataSource.connect();
            PreparedStatement ps = con.prepareStatement(selectQuery)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    screening = screeningMapper(rs);
                    screenings.add(screening);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screenings;
    }

    public void delete(Long screeningId) {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from SCREENING where SCREENING_ID = ?";
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setLong(1, screeningId);
            return ps;
        });
    }

    public void deleteAll() {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from SCREENING";
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

    // SCREENING_ID
    // SEAT_AMOUNT
    // START_TIME
    // CINEMA_ID
    // MOVIE_ID
    private Screening screeningMapper(ResultSet rs) throws SQLException {
        Long screeningId = rs.getLong(1);
        int seatAmount = rs.getInt(2);
        LocalDateTime startTime = rs.getTimestamp(3).toLocalDateTime();
        long cinemaId = rs.getLong(4);
        long movieId = rs.getLong(5);

        return new Screening(screeningId, seatAmount, startTime
                , movieRepository.selectById(movieId).get()
                , cinemaRepository.selectById(cinemaId).get());
    }
}
