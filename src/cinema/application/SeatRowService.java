package cinema.application;

import cinema.domain.SeatRow;
import cinema.repository.SeatRowRepository;

import java.util.List;

public class SeatRowService {
    private final SeatRowRepository seatRowRepository;

    public SeatRowService(SeatRowRepository seatRowRepository) {
        this.seatRowRepository = seatRowRepository;
    }

    public void save(SeatRow seatRow) {
        seatRowRepository.insert(seatRow);
    }

    public SeatRow findByRow(String row) {
        return seatRowRepository.selectByRow(row)
                .orElseThrow(() -> new AssertionError("존재하지 않는 row입니다. row: " + row));
    }

    public List<SeatRow> selectByTheater(Long theaterId) {
        return seatRowRepository.selectByTheater(theaterId);
    }
}
