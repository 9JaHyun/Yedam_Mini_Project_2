package cinema.domain;


import java.util.Optional;

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

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return name;
    }
}
