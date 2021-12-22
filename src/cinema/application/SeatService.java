package cinema.application;

import cinema.domain.Seat;
import cinema.domain.SeatStatus;
import cinema.repository.SeatRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SeatService {
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public void save(Seat seat) {
        seatRepository.insert(seat);
    }

    public Seat findByCol(int col) {
        return seatRepository.selectByColNumber(col)
                .orElseThrow(() -> new AssertionError("존재하지 않는 col입니다. row: " + col));
    }

    public List<Seat> findByRowNum(Long rowNum) {
        return seatRepository.selectByRowNumber(rowNum);
    }

    public void updateSeat(Seat seats) {
        seatRepository.update(seats);
    }
}
