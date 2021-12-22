package cinema.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
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
}
