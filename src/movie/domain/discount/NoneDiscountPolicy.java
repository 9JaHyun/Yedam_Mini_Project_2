package movie.domain.discount;

import screening.domain.Screening;

public class NoneDiscountPolicy extends DiscountPolicy {
    @Override
    protected int getDiscountAmount(Screening Screening) {
        return 0;
    }
}
