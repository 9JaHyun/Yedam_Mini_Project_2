package movie.application;

import movie.domain.Movie;
import movie.respository.MovieRepository;

import java.util.List;

public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void save(Movie movie) {
        movieRepository.insert(movie);
    }

    public Movie findById(Long movieId) {
        return movieRepository.selectById(movieId)
                .orElseThrow(() -> new AssertionError("해당 id값의 영화를 찾을 수 없습니다. / id: " + movieId));
    }

    public Movie findByName(String name) {
        return movieRepository.selectByTitle(name).get();
    }

    public List<Movie> findAll() {
        return movieRepository.selectAll();
    }

    public void delete(Long movieId) {
        movieRepository.delete(movieId);
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }
}
