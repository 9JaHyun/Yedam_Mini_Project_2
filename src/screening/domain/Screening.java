package screening.domain;

import cinema.domain.Theater;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import member.domain.Member;
import movie.domain.Movie;
import reservation.domain.Reservation;
import cinema.domain.Cinema;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@AllArgsConstructor
public class Screening {
    private Long id;
    private int seatAmount;
    private LocalDateTime startTime;

    private Movie movie;
    private Theater theater;

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public Screening(int seatAmount, LocalDateTime startTime, Movie movie, Theater theater) {
        this.seatAmount = seatAmount;
        this.startTime = startTime;
        this.movie = movie;
        this.theater = theater;
    }

    public Reservation reserve(Member member, int audienceCount) {
        return new Reservation(member, this, calculateFee(), audienceCount);
    }

    private int calculateFee() {
        return movie.calculateMovieFee(this);
    }

    @Override
    public String toString() {

        return "[영화제목: " + movie.getTitle() +
                ", 상영시간: " + startTime.format(TIME_FORMAT) + " ~ " +
                startTime.plus(movie.getRunningTime()).format(TIME_FORMAT) +
                ", 상영위치: " + getTheater() +
                ']';
    }
}
