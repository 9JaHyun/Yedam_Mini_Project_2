
import member.application.MemberLoginApp;
import reservation.application.ReservationApp;
import share.init.*;

public class Client {
    public static void main(String[] args) {
//        initAll();
        MemberLoginApp start = new MemberLoginApp();

        start.run();
        ReservationApp reservationApp = new ReservationApp();
//        Member member = new Member(5L, "test1", "password1", MemberLevel.VVIP);
//        reservationApp.run(member);
    }

    public static void initAll() {
        MovieInit movieInit = new MovieInit();
        CinemaInit cinemaInit = new CinemaInit();
        TheaterInit theater = new TheaterInit();
        ScreeningInit screeningInit = new ScreeningInit();
        SeatRowInit seatRowInit = new SeatRowInit();
        SeatInit seatInit = new SeatInit();

        movieInit.init();
        cinemaInit.init();
        theater.init();
        screeningInit.init();
        seatRowInit.init();
        seatInit.init();
    }
}
