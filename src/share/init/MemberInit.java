package share.init;

import member.Repository.MemberRepository;
import member.application.MemberLoginService;
import member.application.MemberService;
import member.domain.Member;
import member.domain.MemberLevel;

public class MemberInit {
    private final MemberRepository memberRepository = new MemberRepository();

    public void init() {
        Member member = new Member("admin", "admin", MemberLevel.ADMIN);
        memberRepository.insert(member);
    }

}
