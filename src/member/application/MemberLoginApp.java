package member.application;

import member.Repository.MemberRepository;
import member.domain.Member;

import java.util.Scanner;

public class MemberLoginApp {
    private final MemberLoginService memberLoginService = new MemberLoginService(new MemberRepository());
    private final MemberApp managerApp = new MemberApp();

    public void run() {
        Scanner sc = new Scanner(System.in);
        boolean runApp = true;
        while(runApp) {
            renderFirstMenu();
            int firstMenuChoose = sc.nextInt();
            switch (firstMenuChoose) {
                case 1:
                    Member member = signIn(sc);
                    System.out.println("환영합니다. " + member.getName() + "님!!!");
                    managerApp.run(member);
                    break;

                case 2: {
                    signUp(sc);
                    System.out.println("회원가입이 성공적으로 이루어졌습니다. 로그인페이지로 이동합니다.");
                    signIn(sc);
                    break;
                }

                case 3: {
                    runApp = false;
                }
            }
        }
    }
    private void renderFirstMenu() {
        System.out.println();
        System.out.println("=========== Yedam 영화관 ===========");
        System.out.println("1.로그인 | 2.회원가입 | 3. 나가기");
        System.out.println("===================================");
        System.out.print("입력>> ");
    }

    private Member signIn(Scanner scanner) {
        System.out.println();
        System.out.println("=============== 로그인 ===============");
        System.out.print("1. 아이디>> ");
        String name = scanner.next();
        System.out.print("2. 비밀번호>> ");
        String password = scanner.next();
        System.out.println("====================================");
        return memberLoginService.signIn(name, password);
    }

    private void signUp(Scanner scanner) {
        System.out.println();
        System.out.println("============= 회원 가입 =============");
        System.out.print("1. 아이디>> ");
        String name = scanner.next();
        System.out.print("2. 비밀번호>> ");
        String password = scanner.next();
        System.out.print("3. 비밀번호 재입력>> ");
        String rePassword = scanner.next();
        System.out.println("====================================");
        memberLoginService.signUp(name, password, rePassword);
    }

}
