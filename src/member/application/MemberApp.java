package member.application;

import member.Repository.MemberRepository;
import member.domain.Member;

import java.util.Scanner;

public class MemberApp {
    private final MemberService managerService = new MemberService(new MemberRepository());

    Scanner sc = new Scanner(System.in);

    public void run(Member member) {
        boolean runApp = true;
        while(runApp) {
            renderFirstMenu(member);
            int firstMenuChoose = sc.nextInt();
            switch (firstMenuChoose) {
            	// 예매 하기
                case 1:
                    break;
                
                // 예매 정보 확인
                case 2: {
                    break;
                }
                // 박스오피스
                case 3: {
                    System.out.println("로그아웃.");
                    break;
                }
                // 비밀번호 변경
                case 4: {
                    System.out.print("비밀번호를 변경하려면 Y(y)를 입력하세요>> ");
                    if(checkYesOrNo(sc.next())) {
                        changePassword(member);
                    }
                    break;
                }
                // 로그아웃
                case 5: {
                    System.out.println("로그아웃.");
                    runApp = false;
                    break;
                }
                default: {
                    System.out.println("잘못된 입력입니다.");
                    break;
                }
            }
        }
    }

    private void renderFirstMenu(Member member) {
        System.out.println();
        System.out.println("=========== " + member.getName() + "님 안녕하세요 ===========");
        System.out.println("1.예매 하기 | 2.예매 정보 확인 | 3.박스오피스 | 4.비밀번호 변경 | 5.로그아웃");
        System.out.println("==================================================");
        System.out.print("입력>> ");
    }

    private boolean checkYesOrNo(String yesOrNo) {
        return yesOrNo.equals("Y") || yesOrNo.equals("y");
    }

    private void changePassword(Member member) {
        checkPassword(member);
        System.out.print("변경할 비밀번호를 입력하세요>> ");
        String changePassword = sc.next();
        System.out.print("변경할 비밀번호를 재입력하세요>> ");
        String rePassword = sc.next();
        managerService.changePassword(member, changePassword, rePassword);
        System.out.println("변경완료!");
    }

    private void checkPassword(Member member) {
        System.out.println("비밀번호 변경을 위해 비밀번호를 재확인하겠습니다.");
        System.out.print("비밀번호를 재입력하세요>> ");
        String password = sc.next();
        managerService.checkPassword(member, password);
    }
}
