package movie.domain.discount;

import screening.domain.Screening;

import java.time.LocalTime;

public class PeriodCondition implements DiscountCondition{
    private final LocalTime startTime;

    public PeriodCondition(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return startTime.compareTo(screening.getStartTime().toLocalTime()) <= 0;
    }
}
