package cinema.domain;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private Long id;
    private String location;
    private String region;
    private List<Theater> theaters = new ArrayList<>();

    public Cinema(Long cinemaId, String location, String region) {
        this.id = cinemaId;
        this.location = location;
        this.region = region;
    }

    public Cinema(String location, String region) {
        this.location = location;
        this.region = region;
    }

    public Cinema(Long id, String location, String region, List<Theater> theaters) {
        this.id = id;
        this.location = location;
        this.region = region;
        this.theaters = theaters;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegion() {
        return region;
    }

    public List<Theater> getTheaters() {
        return theaters;
    }

    public void setTheaters(List<Theater> theaters) {
        this.theaters = theaters;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "지역: " + region + '\'' +
                "지점: '" + location + '\'' +
                '}';
    }
}
