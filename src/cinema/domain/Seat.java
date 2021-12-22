package cinema.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Seat {
    private Long id;
    private int column;
    private SeatStatus seatStatus;
    private SeatRow row;

    public Seat(Long id, int column, SeatStatus seatStatus, SeatRow row) {
        this.id = id;
        this.column = column;
        this.seatStatus = seatStatus;
        this.row = row;
    }

    public Seat(int column, SeatStatus seatStatus, SeatRow row) {
        this.column = column;
        this.seatStatus = seatStatus;
        this.row = row;
    }

    public String printSeatStatus() {
        switch (seatStatus) {
            case EMPTY:
                return column + "□ ";
            case PICKED:
                return column + "● ";
            case RESERVED:
                return column + "■ ";
            case REPAIR:
                return column + "X ";
            default:
                return column + "? ";
        }
    }

    public String toString() {
        return row.getRowName() + column;
    }
}
