package screening.application;

import member.domain.Member;
import screening.repository.ScreeningRepository;

import java.time.LocalDate;
import java.util.Scanner;

public class ScreeningApp {
    private final ScreeningService screeningService = new ScreeningService(new ScreeningRepository());

    Scanner sc = new Scanner(System.in);

    public void run(Member member) {
        boolean runApp = true;
        while(runApp) {
            renderDate(member);

            }
        }

    private void renderDate(Member member) {
        System.out.println();
        System.out.println("=========== 영화예매 ===========");
        for (int i = 0; i <= 7 ; i++) {
            System.out.println(i + ". " + LocalDate.now().plusDays(i).toString());
        }
        System.out.println("====================================");
        System.out.println("예매 일시를 선택하세요>> ");
    }
}
