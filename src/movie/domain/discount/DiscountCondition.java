package movie.domain.discount;

import screening.domain.Screening;

public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening);
}
