
import manager.ManagerApp;
import member.application.MemberLoginApp;
import member.domain.Member;
import member.domain.MemberLevel;
import reservation.application.ReservationService;
import reservation.repository.ReservationRepository;
import reservation.repository.ReservationSeatsRepository;
import share.init.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("init data?(Y/N)>> ");
        String yesOrNO = sc.next();
        if (yesOrNO.equals("Y") || yesOrNO.equals("y")) {
            initAll();
        }
        MemberLoginApp start = new MemberLoginApp();
        start.run();
        sc.close();
    }

    public static void initAll() {
        MemberInit memberInit = new MemberInit();
        MovieInit movieInit = new MovieInit();
        CinemaInit cinemaInit = new CinemaInit();
        TheaterInit theater = new TheaterInit();
        ScreeningInit screeningInit = new ScreeningInit();
        SeatRowInit seatRowInit = new SeatRowInit();
        SeatInit seatInit = new SeatInit();

        memberInit.init();
        movieInit.init();
        cinemaInit.init();
        theater.init();
        screeningInit.init();
        seatRowInit.init();
        seatInit.init();
    }
}
