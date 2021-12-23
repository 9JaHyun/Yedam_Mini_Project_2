package movie.domain;

import lombok.Getter;
import movie.domain.discount.AmountDiscountPolicy;
import movie.domain.discount.CultureDayCondition;
import movie.domain.discount.DiscountPolicy;
import movie.domain.discount.PeriodCondition;
import movie.exception.InvalidScreeningException;
import screening.domain.Screening;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
public class Movie {
    private Long id;
    private String title;
    private Genre genre;
    private Duration runningTime;
    private int fee;
    private List<DiscountPolicy> discountPolicies = List.of(new AmountDiscountPolicy(5000, new CultureDayCondition())
            , new AmountDiscountPolicy(1000, new PeriodCondition(LocalTime.of(8, 0))));

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
        for (DiscountPolicy discountPolicy : discountPolicies) {
            fee -= (discountPolicy.calculateDiscountAmount(screening));
        }

        return fee;
    }

    public LocalDateTime getEndTimeFrom(LocalDateTime whenStarted) {
        return whenStarted.plus(runningTime);
    }

    @Override
    public String toString() {
        return "타이틀: " + title +
                ", 장르: " + genre +
                ", 상영시간: " + runningTime.toMinutes() + "분";
    }
}
