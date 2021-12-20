package movie.domain;

import movie.domain.discount.DiscountPolicy;
import movie.exception.InvalidScreeningException;
import screening.domain.Screening;

import java.time.Duration;
import java.time.LocalDateTime;

public class Movie {
    private Long id;
    private String title;
    private Genre genre;
    private Duration runningTime;
    private int fee;
    private DiscountPolicy discountPolicy;

    public int getFee() {
        return fee;
    }

    public Movie(Long id, String title, Genre genre, Duration runningTime, int fee) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.runningTime = runningTime;
        this.fee = fee;
    }

    public Movie(String title, Genre genre, Duration runningTime, int fee) {
        this.title = title;
        this.genre = genre;
        this.runningTime = runningTime;
        this.fee = fee;
    }

    public int calculateMovieFee(Screening screening) {
        if (screening == null ||
                screening.getStartTime().isBefore(LocalDateTime.now())) {
            throw new InvalidScreeningException("예약할 수 없는 영화입니다.");
        }

        return fee - (discountPolicy.calculateDiscountAmount(screening));
    }

    public LocalDateTime getEndTimeFrom(LocalDateTime whenStarted) {
        return whenStarted.plus(runningTime);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }
}
