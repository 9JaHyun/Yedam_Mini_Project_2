package screening.domain;

import member.domain.Member;
import movie.domain.Movie;
import reservation.domain.Reservation;
import cinema.domain.Cinema;

import java.time.LocalDateTime;

public class Screening {
    private Long id;
    private int seatAmount;
    private LocalDateTime startTime;

    private Movie movie;
    private Cinema cinema;

    public Screening(Long id, int seatAmount, LocalDateTime startTime, Movie movie, Cinema cinema) {
        this.id = id;
        this.seatAmount = seatAmount;
        this.startTime = startTime;
        this.movie = movie;
        this.cinema = cinema;
    }

    public Screening(int seatAmount, LocalDateTime startTime, Movie movie, Cinema cinema) {
        this.seatAmount = seatAmount;
        this.startTime = startTime;
        this.movie = movie;
        this.cinema = cinema;
    }

    public Reservation reserve(Member member, int audienceCount) {
        return new Reservation(member, this, calculateFee(), audienceCount);
    }

    private int calculateFee() {
        return movie.calculateMovieFee(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeatAmount() {
        return seatAmount;
    }

    public void setSeatAmount(int seatAmount) {
        this.seatAmount = seatAmount;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    @Override
    public String toString() {
        return "Screening{" +
                "제목: " + movie.getTitle() +
                ", 시작시간: " + startTime +
                ", 상영위치: " + cinema.getTheaters().get(0) +
                '}';
    }
}
