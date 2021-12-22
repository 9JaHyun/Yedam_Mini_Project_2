package cinema.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Cinema {
    private Long id;
    private String region;
    private String location;
    private List<Theater> theaters = new ArrayList<>();

    public Cinema(Long cinemaId, String region, String location) {
        this.id = cinemaId;
        this.region = region;
        this.location = location;
    }

    public Cinema(String region, String location) {
        this.location = location;
        this.region = region;
    }

    public Cinema(Long id, String location, String region, List<Theater> theaters) {
        this.id = id;
        this.location = location;
        this.region = region;
        this.theaters = theaters;
    }

    @Override
    public String toString() {
        return "영화관[지역: " + region +
                " 지점: " + location + "]";
    }
}
