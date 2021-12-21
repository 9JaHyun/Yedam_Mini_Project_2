package cinema.domain;

public class SeatRow {
    private Long id;
    private String rowName;
    private Theater theater;

    public SeatRow(Long id, String name, Theater theater) {
        this.id = id;
        this.rowName = name;
        this.theater = theater;
    }

    public SeatRow(String rowName, Theater theater) {
        this.rowName = rowName;
        this.theater = theater;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }
}
