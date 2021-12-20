package movie.domain.discount;

import screening.domain.Screening;

public class AmountDiscountPolicy extends DiscountPolicy {
    private int discountAmount;

    public AmountDiscountPolicy(int amount, DiscountCondition... conditions) {
        super(conditions);
        this.discountAmount = amount;
    }

    @Override
    protected int getDiscountAmount(Screening Screening) {
        return discountAmount;
    }
}
