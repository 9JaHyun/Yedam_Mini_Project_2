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

    public Cinema findById(Long id) {
        return cinemaRepository.selectById(id).orElseThrow(() -> new AssertionError("잘못된 cinema 정보입니다. id: " + id));
    }

    public List<Cinema> findAll() {
        return cinemaRepository.selectAll();
    }

    public Cinema findCinemaByLocation(String region, String location) {
        return cinemaRepository.selectByRegionAndLocation(region, location)
                .orElseThrow(() -> new AssertionError("적절하지 않은 입력입니다. / region: " + region + " / location: " + location));
    }

    public void deleteAll() {
        cinemaRepository.deleteAll();
    }


    private List<Cinema> findCinemaByRegion(String region) {
        return findAll().stream()
                .filter(cinema -> cinema.getRegion().equals(region))
                .collect(Collectors.toList());
    }

}
