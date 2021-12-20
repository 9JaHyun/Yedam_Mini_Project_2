package member.application;

import member.Repository.MemberRepository;
import member.domain.Member;

public class MemberLoginService {
    private final MemberRepository memberRepository;

    public MemberLoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findByName(String name){
        return memberRepository.selectByName(name)
                .orElse(null);
    }

    public Member signIn(String name, String password) {
        Member member = findByName(name);
        if (!validateSignInPassword(member, name, password)) {
            throw new AssertionError("로그인 정보가 일치하지 않습니다.");
        }
        return member;
    }

    public void signUp(String name, String password, String rePassword) {
        if(validateSignUpName(name)){
            throw new AssertionError("중복되는 ID입니다.");
        }
        if (!validateSignUpPassword(password, rePassword)) {
            throw new AssertionError("비밀번호와 비밀번호 재입력이 일치하지 않습니다.");
        }
        Member manager = Member.createMember(name, password);
        memberRepository.insert(manager);
    }

    private boolean validateSignUpName(String name) {
        Member findManager = findByName(name);
        if (findManager == null) {
            return false;
        }
        return findManager.getName().equals(name);
    }

    private boolean validateSignInPassword(Member member, String name, String password) {
        if(member == null) {return false;}
        return member.getName().equals(name) &&
                member.getPassword().equals(password);
    }

    private boolean validateSignUpPassword(String password, String rePassword) {
        return password.equals(rePassword);
    }
 
}
