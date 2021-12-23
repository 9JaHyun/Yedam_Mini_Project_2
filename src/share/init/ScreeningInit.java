package share.init;

import cinema.application.TheaterService;
import cinema.repository.CinemaRepository;
import cinema.repository.TheaterRepository;
import movie.application.MovieService;
import movie.respository.MovieRepository;
import screening.application.ScreeningService;
import screening.domain.Screening;
import screening.repository.ScreeningRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ScreeningInit {
    ScreeningService screeningService = new ScreeningService(new ScreeningRepository());
    TheaterService theaterService = new TheaterService(new TheaterRepository(), new CinemaRepository());
    MovieService movieService = new MovieService(new MovieRepository());

    public void init() {
        screeningService.deleteAll();
        LocalDate day25 = LocalDate.of(2021, 12, 25);
        LocalDate day26 = LocalDate.of(2021, 12, 26);

        List<Screening> screenings = List.of(
                new Screening(LocalDateTime.of(day25, LocalTime.of(9, 30))
                        , movieService.findByName("스파이더맨")
                        , theaterService.findTheater("서울", "강남", "1관"))
                , new Screening(LocalDateTime.of(day25, LocalTime.of(10, 0))
                        , movieService.findByName("스파이더맨")
                        , theaterService.findTheater("서울", "강남", "2관"))
                , new Screening(LocalDateTime.of(day25, LocalTime.of(11, 0))
                        , movieService.findByName("스파이더맨")
                        , theaterService.findTheater("서울", "강동", "1관"))
                , new Screening(LocalDateTime.of(day26, LocalTime.of(9, 30))
                        , movieService.findByName("모가디슈")
                        , theaterService.findTheater("서울", "강남", "3관"))
                , new Screening(LocalDateTime.of(day26, LocalTime.of(10, 0))
                        , movieService.findByName("스파이더맨")
                        , theaterService.findTheater("서울", "강남", "1관"))
                , new Screening(LocalDateTime.of(day26, LocalTime.of(11, 0))
                        , movieService.findByName("모가디슈")
                        , theaterService.findTheater("대구", "대구이시아", "3관"))
                , new Screening(LocalDateTime.of(day26, LocalTime.of(12, 30))
                        , movieService.findByName("매트릭스")
                        , theaterService.findTheater("대구", "대구신세계(동대구)", "1관"))
                , new Screening(LocalDateTime.of(day25, LocalTime.of(11, 30))
                        , movieService.findByName("듄")
                        , theaterService.findTheater("대구", "대구신세계(동대구)", "2관"))
                , new Screening(LocalDateTime.of(day25, LocalTime.of(11, 30))
                        , movieService.findByName("스파이더맨")
                        , theaterService.findTheater("대구", "대구신세계(동대구)", "3관"))
                , new Screening(LocalDateTime.of(day26, LocalTime.of(11, 30))
                        , movieService.findByName("스파이더맨")
                        , theaterService.findTheater("대구", "대구이시아", "4관"))
                , new Screening(LocalDateTime.of(day26, LocalTime.of(11, 30))
                        , movieService.findByName("스파이더맨")
                        , theaterService.findTheater("대구", "대구이시아", "2관")));
        screenings.iterator()
                .forEachRemaining(screening -> screeningService.save(screening));

    }
}
