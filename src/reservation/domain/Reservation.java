package reservation.domain;

import lombok.Getter;
import lombok.Setter;
import screening.domain.Screening;
import member.domain.Member;

import java.util.List;

@Getter @Setter
public class Reservation {
    private Long id;
    private ReservationStatus status;
    private Member member;
    private Screening screening;
    private int fee;
    private int audienceCount;
    private List<ReservationSeats> seats;


    public Reservation(Member member, Screening screening, int fee, int audienceCount) {
        this.member = member;
        status = ReservationStatus.PAYMENT;
        this.screening = screening;
        this.fee = fee * audienceCount;
        this.audienceCount = audienceCount;
    }

    public Reservation(Long id, ReservationStatus status, Member member, Screening screening, int fee, int audienceCount) {
        this.id = id;
        this.status = status;
        this.member = member;
        this.screening = screening;
        this.fee = fee;
        this.audienceCount = audienceCount;
    }

    @Override
    public String toString() {
        return screening +
                ",  결제 금액: " + fee + "원" +
                ",  인원: " + audienceCount + "명" +
                ",  상태: " + status;
    }
}
