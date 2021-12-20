package member.domain;

import reservation.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private Long id;
    private String name;
    private String password;

    private MemberLevel memberLevel;
    private Member(String name, String password, MemberLevel memberLevel) {
        this.name = name;
        this.password = password;
        this.memberLevel = memberLevel;
    }

    public Member(Long id, String name, String password, MemberLevel memberLevel) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.memberLevel = memberLevel;
    }

    public static Member createMember(String name, String password) {
        return new Member(name, password, MemberLevel.MEMBER);
    }

    public static Member createMember(Long id, String name, String password, MemberLevel memberLevel) {
        return new Member(id, name, password, memberLevel);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public MemberLevel getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(MemberLevel memberLevel) {
        this.memberLevel = memberLevel;
    }

    public Long getId() {
        return id;
    }
}
