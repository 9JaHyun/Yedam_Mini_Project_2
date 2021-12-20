package cinema.application;

import cinema.domain.Cinema;
import cinema.repository.CinemaRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CinemaService {
    private final CinemaRepository cinemaRepository;

    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public void save(Cinema cinema) {
        cinemaRepository.insert(cinema);
    }

    public List<Cinema> findAll() {
        return cinemaRepository.selectAll();
    }

    public List<Cinema> findCinemaByRegion(List<Cinema> cinemas, String region) {
        return cinemas.stream()
                .filter(cinema -> cinema.getRegion().equals(region))
                .collect(Collectors.toList());
    }

    public Long findCinemaByLocation(List<Cinema> cinemas, String location) {
        return cinemas.stream()
                .filter(cinema -> cinema.getLocation().equals(location))
                .map(Cinema::getId)
                .findAny()
                .orElseThrow(() -> new AssertionError("잘못된 위치정보입니다. / location: " + location));
    }

    public void deleteAll() {
        cinemaRepository.deleteAll();
    }

}
