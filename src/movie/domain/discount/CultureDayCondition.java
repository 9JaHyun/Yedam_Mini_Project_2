package movie.domain.discount;

import screening.domain.Screening;

import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;

public class CultureDayCondition implements DiscountCondition{
    @Override
    public boolean isSatisfiedBy(Screening screening) {
        return screening.getStartTime().getDayOfMonth() == screening.getStartTime()
                .with(TemporalAdjusters.lastInMonth(DayOfWeek.WEDNESDAY))
                .getDayOfMonth();
    }
}
