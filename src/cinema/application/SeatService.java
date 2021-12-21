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

    public StringBuilder renderSeatRow(Long rowNum) {
        StringBuilder sb = new StringBuilder();
        List<Seat> rows = findByRowNum(rowNum);
        String seatStatus;
        for (Seat seat : rows) {
            switch (seat.getSeatStatus()){
                case EMPTY:
                    seatStatus = seat.getColumn() + "□ ";
                    break;
                case RESERVED:
                    seatStatus = seat.getColumn() + "■ ";
                    break;
                case REPAIR:
                    seatStatus = seat.getColumn() + "X ";
                    break;
                default:
                    seatStatus = seat.getColumn() + "? ";
                    break;
            }
            sb.append(seatStatus);
        }
        return sb;
    }
}
