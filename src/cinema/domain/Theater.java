package cinema.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Theater {
    private Long id;
    private String name;
    private Cinema cinema;

    public Theater(Long id, String name, Cinema cinema) {
        this.id = id;
        this.name = name;
        this.cinema = cinema;
    }

    public Theater(String name, Cinema cinema) {
        this.name = name;
        this.cinema = cinema;
    }


    @Override
    public String toString() {
        return name;
    }
}
