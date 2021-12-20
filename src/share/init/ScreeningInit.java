package share.init;

import cinema.application.CinemaService;
import cinema.domain.Cinema;
import cinema.repository.CinemaRepository;
import movie.domain.Genre;
import movie.domain.Movie;
import screening.application.ScreeningService;
import screening.domain.Screening;
import screening.repository.ScreeningRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ScreeningInit {
    ScreeningService screeningService = new ScreeningService(new ScreeningRepository());
    public void init() {
        screeningService.deleteAll();
        List<Screening> cinemas = List.of(
                new Screening(150, LocalDateTime.of(LocalDate.of(2021, 12, 25), LocalTime.of(9, 30))
                        , new Movie("스파이더맨", Genre.HERO, Duration.of(148, ChronoUnit.MINUTES), 9000)
                        , new Cinema("서울", "강동"))
                , new Screening(100, LocalDateTime.of(LocalDate.of(2021, 12, 25), LocalTime.of(10, 0))
                        , new Movie("스파이더맨", Genre.HERO, Duration.of(148, ChronoUnit.MINUTES), 9000)
                        , new Cinema("서울", "강남"))
                , new Screening(100, LocalDateTime.of(LocalDate.of(2021, 12, 25), LocalTime.of(11, 0))
                        , new Movie("스파이더맨", Genre.HERO, Duration.of(148, ChronoUnit.MINUTES), 9000)
                        , new Cinema("서울", "강동"))
                , new Screening(100, LocalDateTime.of(LocalDate.of(2021, 12, 26), LocalTime.of(9, 30))
                        , new Movie("스파이더맨", Genre.HERO, Duration.of(148, ChronoUnit.MINUTES), 9000)
                        , new Cinema("서울", "강남"))
                , new Screening(100, LocalDateTime.of(LocalDate.of(2021, 12, 26), LocalTime.of(10, 0))
                        , new Movie("스파이더맨", Genre.HERO, Duration.of(148, ChronoUnit.MINUTES), 9000)
                        , new Cinema("서울", "강동"))
                , new Screening(100, LocalDateTime.of(LocalDate.of(2021, 12, 26), LocalTime.of(11, 0))
                        , new Movie("스파이더맨", Genre.HERO, Duration.of(148, ChronoUnit.MINUTES), 9000)
                        , new Cinema("서울", "강동"))
                , new Screening(100, LocalDateTime.of(LocalDate.of(2021, 12, 26), LocalTime.of(12, 30))
                        , new Movie("스파이더맨", Genre.HERO, Duration.of(148, ChronoUnit.MINUTES), 9000)
                        , new Cinema("대구", "대구(칠성로)"))
                , new Screening(100, LocalDateTime.of(LocalDate.of(2021, 12, 25), LocalTime.of(11, 30))
                        , new Movie("스파이더맨", Genre.HERO, Duration.of(148, ChronoUnit.MINUTES), 9000)
                        , new Cinema("대구", "대구(칠성로)"))
                , new Screening(100, LocalDateTime.of(LocalDate.of(2021, 12, 25), LocalTime.of(11, 30))
                        , new Movie("스파이더맨", Genre.HERO, Duration.of(148, ChronoUnit.MINUTES), 9000)
                        , new Cinema("대구", "대구(칠성로)"))
                , new Screening(100, LocalDateTime.of(LocalDate.of(2021, 12, 26), LocalTime.of(11, 30))
                        , new Movie("스파이더맨", Genre.HERO, Duration.of(148, ChronoUnit.MINUTES), 9000)
                        , new Cinema("대구", "대구이시아"))
                , new Screening(100, LocalDateTime.of(LocalDate.of(2021, 12, 26), LocalTime.of(11, 30))
                        , new Movie("스파이더맨", Genre.HERO, Duration.of(148, ChronoUnit.MINUTES), 9000)
                        , new Cinema("대구", "대구이시아")));

        cinemas.iterator()
                .forEachRemaining(cinema -> screeningService.save(cinema));

    }
}
