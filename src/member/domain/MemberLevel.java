package member.domain;

public enum MemberLevel {
    MEMBER("MEMBER"),
    VIP("VIP"),
    VIP_PREMIUM("VIP_PREMIUM"),
    VVIP("VVIP"),
    ADMIN("ADMIN");

    private String value;

    MemberLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
