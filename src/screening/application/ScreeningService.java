package screening.application;

import cinema.application.CinemaService;
import cinema.repository.CinemaRepository;
import screening.domain.Screening;
import screening.repository.ScreeningRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final CinemaService cinemaService = new CinemaService(new CinemaRepository());

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    public void save(Screening screening) {
        screeningRepository.insert(screening);
    }

    public List<Screening> findByDate(LocalDate localDate) {
        return screeningRepository.selectByDate(localDate);
    }

    public List<Screening> findAll() {
        return screeningRepository.selectAll();
    }

    public List<Screening> findByCinema(List<Screening> screenings, Long cinemaId) {
        return screenings.stream()
                .filter(screening -> screening.getTheater().getId().equals(cinemaId))
                .collect(Collectors.toList());
    }


    public void deleteAll() {
        screeningRepository.deleteAll();
    }

    public List<Screening> findByTitle(List<Screening> screenings, String title) {
        return screenings.stream()
                .filter(screening -> screening.getMovie().getTitle().equals(title))
                .collect(Collectors.toList());
    }

    public List<Screening> findCinemaByRegion(List<Screening> screenings, String region) {
        return screenings.stream()
                .filter(screening -> screening.getTheater().getCinema().getRegion().equals(region))
                .collect(Collectors.toList());
    }

    public Long findCinemaByLocation(List<Screening> screenings, String location) {
        return screenings.stream()
                .filter(screening -> screening.getTheater().getCinema().getLocation().equals(location))
                .map(screening -> screening.getTheater().getId())
                .findFirst().get();
    }
}
