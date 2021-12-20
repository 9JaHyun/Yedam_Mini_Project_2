package member.application;

import member.Repository.MemberRepository;
import member.domain.Member;

import java.util.List;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository managerRepository) {
        this.memberRepository = managerRepository;
    }

    public Member findById(Long managerId) {
        return memberRepository.selectById(managerId);
    }

    public List<Member> findAll() {
        return memberRepository.selectAll();
    }

    public void update(Member member) {
        memberRepository.update(member);
    }

    public void delete(Long managerId) {
        memberRepository.delete(managerId);
    }

    public void changePassword(Member member, String password, String rePassword) {
        checkChangedPassword(password, rePassword);
        member.changePassword(password);
        memberRepository.update(member);
    }

    private void checkChangedPassword(String password, String rePassword) {
        if (!password.equals(rePassword)) {
            throw new AssertionError("비밀번호 재입력이 일치하지 않습니다.");
        }
    }

    public void checkPassword(Member member, String password) {
        if(!member.getPassword().equals(password)){
            throw new AssertionError("비밀번호가 일치하지 않습니다.");
        }
    }
}
