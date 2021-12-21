package reservation.application;

import reservation.domain.Reservation;
import reservation.repository.ReservationRepository;

import java.util.List;

public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void save(Reservation reservation) {
        reservationRepository.insert(reservation);
    }

    public Reservation findById(Long id) {
        return reservationRepository.selectById(id)
                .orElseThrow(() -> new AssertionError("해당 Reservation을 찾을 수 없습니다. / reservation_id: " + id));
    }

    public List<Reservation> findByMember(Long id) {
        return reservationRepository.selectByMemberId(id);
    }

    public void deleteById(Long id) {
        reservationRepository.delete(id);
    }


}
