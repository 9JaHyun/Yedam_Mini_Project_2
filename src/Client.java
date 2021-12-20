
import member.application.MemberLoginApp;
import share.init.CinemaInit;
import share.init.MovieInit;

public class Client {
    public static void main(String[] args) {
        MovieInit movieInit = new MovieInit();
        CinemaInit cinemaInit = new CinemaInit();
        MemberLoginApp start = new MemberLoginApp();
        movieInit.init();
        cinemaInit.init();
        start.run();
    }


}
