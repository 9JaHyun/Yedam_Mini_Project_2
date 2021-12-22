package reservation.application;

import reservation.domain.Reservation;
import reservation.domain.ReservationSeats;
import reservation.domain.ReservationStatus;
import reservation.repository.ReservationRepository;
import reservation.repository.ReservationSeatsRepository;

import java.util.List;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationSeatsRepository reservationSeatsRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationSeatsRepository reservationSeatsRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationSeatsRepository = reservationSeatsRepository;
    }

    public Long save(Reservation reservation) {
       return reservationRepository.insert(reservation);
    }

    public Reservation findById(Long id) {
        return reservationRepository.selectById(id)
                .orElseThrow(() -> new AssertionError("해당 Reservation을 찾을 수 없습니다. / reservation_id: " + id));
    }

    public List<Reservation> findByMember(Long id) {
        return reservationRepository.selectByMemberId(id);
    }

    public void cancel(Long id) {
        deleteById(id);
        reservationSeatsRepository.deleteByReservationId(id);
    }

    public List<ReservationSeats> findSeats(Long reservationId) {
        return reservationSeatsRepository.selectByReservationId(reservationId);
    }

    public void deleteById(Long id) {
        reservationRepository.delete(id);
    }


}
