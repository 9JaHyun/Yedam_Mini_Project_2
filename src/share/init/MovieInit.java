package share.init;

import movie.application.MovieService;
import movie.domain.Genre;
import movie.domain.Movie;
import movie.respository.MovieRepository;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MovieInit {
    MovieService movieService = new MovieService(new MovieRepository());
    public void init() {
        movieService.deleteAll();
        List<Movie> movies = List.of(
                new Movie("스파이더맨", Genre.HERO, Duration.of(148, ChronoUnit.MINUTES), 11000)
                , new Movie("킹스맨", Genre.ACTION, Duration.of(100, ChronoUnit.MINUTES), 11000)
                , new Movie("매트릭스", Genre.SF, Duration.of(110, ChronoUnit.MINUTES), 11000)
                , new Movie("모가디슈", Genre.ACTION, Duration.of(121, ChronoUnit.MINUTES), 11000)
                , new Movie("싱크홀", Genre.COMEDY, Duration.of(113, ChronoUnit.MINUTES), 11000)
                , new Movie("베놈2", Genre.SF, Duration.of(97, ChronoUnit.MINUTES), 11000)
                , new Movie("듄", Genre.SF, Duration.of(155, ChronoUnit.MINUTES), 11000)
                , new Movie("보이스", Genre.CRIMINAL, Duration.of(109, ChronoUnit.MINUTES), 11000)
                , new Movie("크루엘라", Genre.CRIMINAL, Duration.of(133, ChronoUnit.MINUTES), 11000));

        movies.iterator()
                .forEachRemaining(movie -> movieService.save(movie));

    }
}
