package reservation.application;

import reservation.domain.ReservationSeats;
import reservation.repository.ReservationSeatsRepository;

import java.util.List;

public class ReservationSeatsService {
    private final ReservationSeatsRepository reservationRepository;

    public ReservationSeatsService(ReservationSeatsRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void saveSeat(ReservationSeats seats) {
        reservationRepository.insert(seats);
    }

    public ReservationSeats findById(Long id) {
        return reservationRepository.selectById(id)
                .orElseThrow(() -> new AssertionError("찾을 수 없는 ID 입니다. id: " + id));
    }

    public List<ReservationSeats> findByReservationId(Long id) {
        return reservationRepository.selectByReservationId(id);
    }

    public void deleteByReservationId(Long id) {
        reservationRepository.deleteByReservationId(id);
    }
}
