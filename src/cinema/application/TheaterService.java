package cinema.application;

import cinema.domain.Theater;
import cinema.repository.CinemaRepository;
import cinema.repository.TheaterRepository;

import java.util.List;

public class TheaterService {
    private final TheaterRepository theaterRepository;
    private final CinemaRepository cinemaRepository;

    public TheaterService(TheaterRepository theaterRepository,
                          CinemaRepository cinemaRepository) {
        this.theaterRepository = theaterRepository;
        this.cinemaRepository = cinemaRepository;
    }

    public void save(Theater theater) {
        theaterRepository.insert(theater);
    }

    public Theater findById(Long theaterId) {
        return theaterRepository.selectById(theaterId)
                .orElseThrow(() -> new AssertionError("잘못된 Theater 정보입니다. id: " + theaterId));
    }

    public List<Theater> findByCinemaId(Long cinemaId) {
        return theaterRepository.selectByCinemaId(cinemaId);
    }

    public void deleteAll() {
        theaterRepository.deleteAll();
    }

    //
    public Theater findTheater(String region, String location, String theaterName) {
        Long cinemaId = cinemaRepository.selectByRegionAndLocation(region, location).get().getId();
        return findByCinemaId(cinemaId).stream()
                .filter(theater -> theater.getName().equals(theaterName))
                .findFirst()
                .get();
    }
}