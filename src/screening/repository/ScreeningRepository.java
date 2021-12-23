package screening.repository;

import cinema.repository.TheaterRepository;
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
    private final TheaterRepository theaterRepository = new TheaterRepository();
    private final MovieRepository movieRepository = new MovieRepository();

    public void insert(Screening screening) {
        jdbcTemplate(con -> {
            String insertQuery = "insert into SCREENING (SCREENING_ID, START_TIME, MOVIE_ID, THEATER_ID) values(HIBERNATE_SEQUENCE.NEXTVAL, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setTimestamp(1, Timestamp.valueOf(screening.getStartTime()));
            ps.setLong(2, screening.getMovie().getId());
            ps.setLong(3, screening.getTheater().getId());
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
        String selectQuery = "select * from SCREENING where to_char(start_time, 'mm/dd') = to_char(?, 'mm/dd')";
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
        LocalDateTime startTime = rs.getTimestamp(2).toLocalDateTime();
        long movieId = rs.getLong(3);
        long theaterId = rs.getLong(4);

        return new Screening(screeningId, startTime
                , movieRepository.selectById(movieId).orElseThrow(() -> new AssertionError("해당 movie 정보가 없습니다. movie: " + movieId))
                , theaterRepository.selectById(theaterId).orElseThrow(() -> new AssertionError("해당 상영관 정보가 없습니다. movie: " + theaterId)));
    }
}
