package share.init;

import cinema.application.SeatRowService;
import cinema.application.SeatService;
import cinema.domain.Seat;
import cinema.domain.SeatRow;
import cinema.domain.SeatStatus;
import cinema.repository.SeatRepository;
import cinema.repository.SeatRowRepository;

import java.util.ArrayList;
import java.util.List;

public class SeatInit {
    SeatService seatService = new SeatService(new SeatRepository());
    SeatRowService seatRowService = new SeatRowService(new SeatRowRepository());

    public void init() {
        List<Seat> seats = new ArrayList<>();
        SeatRow a = seatRowService.findByRow("A");
        SeatRow b = seatRowService.findByRow("B");
        SeatRow c = seatRowService.findByRow("C");
        SeatRow d = seatRowService.findByRow("D");
        SeatRow e = seatRowService.findByRow("E");
        SeatRow f = seatRowService.findByRow("F");
        createSeats(seats, a, b, c);
        createSeats(seats, d, e, f);

        for (Seat seat : seats) {
            seatService.save(seat);
        }

    }

    private void createSeats(List<Seat> seats, SeatRow a, SeatRow b, SeatRow c) {
        for (int i = 1; i <= 15; i++) {
            seats.add(new Seat(i, SeatStatus.EMPTY, a));
        }
        for (int i = 1; i <= 15; i++) {
            seats.add(new Seat(i, SeatStatus.EMPTY,b));
        }
        for (int i = 1; i <= 15; i++) {
            seats.add(new Seat(i, SeatStatus.EMPTY, c));
        }
    }
}
