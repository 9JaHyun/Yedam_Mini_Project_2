package cinema.domain;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public SeatRow getRow() {
        return row;
    }

    public void setRow(SeatRow row) {
        this.row = row;
    }
}
