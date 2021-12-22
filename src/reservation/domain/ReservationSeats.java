package reservation.domain;

import cinema.domain.Seat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationSeats {
    private Long id;
    private Reservation reservation;
    private Seat seat;

    public ReservationSeats(Reservation reservation, Seat seat) {
        this.reservation = reservation;
        this.seat = seat;
    }

    public ReservationSeats(Long id, Reservation reservation, Seat seat) {
        this.id = id;
        this.reservation = reservation;
        this.seat = seat;
    }

    @Override
    public String toString() {
        return seat.toString();
    }
}
