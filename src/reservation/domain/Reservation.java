package reservation.domain;

import screening.domain.Screening;
import member.domain.Member;

public class Reservation {
    private Long id;
    private ReservationStatus status;
    private Member member;
    private Screening screening;
    private int fee;
    private int audienceCount;

    public Reservation(Member member, Screening screening, int fee, int audienceCount) {
        this.member = member;
        this.screening = screening;
        this.fee = fee;
        this.audienceCount = audienceCount;
    }

    public Reservation(Long id, ReservationStatus status, Member member, int fee, int audienceCount) {
        this.id = id;
        this.status = status;
        this.member = member;
        this.fee = fee;
        this.audienceCount = audienceCount;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Member getMember() {
        return member;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(int audienceCount) {
        this.audienceCount = audienceCount;
    }
}
