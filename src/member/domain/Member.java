package member.domain;

import lombok.Getter;

@Getter
public class Member {
    private Long id;
    private String name;
    private String password;

    private MemberLevel memberLevel;
    public Member(String name, String password, MemberLevel memberLevel) {
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

    public void changePassword(String password) {
        this.password = password;
    }
}
