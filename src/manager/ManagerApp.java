package manager;

import cinema.application.CinemaService;
import cinema.application.SeatRowService;
import cinema.application.SeatService;
import cinema.application.TheaterService;
import cinema.domain.*;
import cinema.repository.CinemaRepository;
import cinema.repository.SeatRepository;
import cinema.repository.SeatRowRepository;
import cinema.repository.TheaterRepository;
import movie.application.MovieService;
import movie.domain.Movie;
import movie.respository.MovieRepository;
import screening.application.ScreeningService;
import screening.domain.Screening;
import screening.repository.ScreeningRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class ManagerApp {
    private final ScreeningService screeningService = new ScreeningService(new ScreeningRepository());
    MovieService movieService = new MovieService(new MovieRepository());
    TheaterService theaterService = new TheaterService(new TheaterRepository(), new CinemaRepository());
    CinemaService cinemaService = new CinemaService(new CinemaRepository());
    SeatRowService seatRowService = new SeatRowService(new SeatRowRepository());
    SeatService seatService = new SeatService(new SeatRepository());

    Scanner sc = new Scanner(System.in);

    public void run() {
        boolean appRunner = true;
        while (appRunner) {
            renderManagerMenu();
            System.out.print("입력>> ");
            int chooseMenu = sc.nextInt();
            switch (chooseMenu) {
                case 1:
                    Screening screening = addScreening();
                    screeningService.save(screening);
                    break;
                case 2:
                    manageSeat();
                    break;
                case 3:
                    System.out.println("종료합니다.");
                    appRunner = false;
                    break;

                default:
                    System.out.println("잘못 입력하였습니다.");
            }
        }

    }

    private void manageSeat() {
        boolean done = true;
        Theater theater = selectTheater();
        List<SeatRow> seatRows = seatRowService.selectByTheater(theater.getId());
        Map<String, List<Seat>> seatMap = getSeatMap(seatRows);
        while (done) {
            renderSeatByMap(seatMap);
            Map<String, List<Seat>> newSeatMap = selectSeats(seatMap);
            renderSeatByMap(newSeatMap);
            System.out.print("계속 수정하시겠습니까? >>");
            if (!yesOrNo(sc.next())) {
                seatMap = newSeatMap;
                done = false;
            }
        }
        updateSeatInfo(seatMap);
    }

    private Screening addScreening() {
        while (true) {
            Movie movie = selectMovie();
            LocalDateTime startTime = selectScreeningTime();
            Theater theater = selectTheater();

            System.out.print("저장하시겠습니까?(Y/N)>> ");
            if (yesOrNo(sc.next())) {
                return new Screening(startTime, movie, theater);
            }
        }
    }

    private boolean yesOrNo(String next) {
        return next.equals("Y") || next.equals("y");
    }

    private LocalDateTime selectScreeningTime() {
        System.out.println("                            시간선택");
        System.out.println("=====================================================================");
        System.out.print("원하는 날짜를 [년 월 일] 순으로 입력하세요(2021 12 23)>> ");
        LocalDate date = LocalDate.of(sc.nextInt(), sc.nextInt(), sc.nextInt());
        System.out.print("원하는 시간을 [시간 분] 순으로 입력하세요(11 30)>> ");
        LocalTime time = LocalTime.of(sc.nextInt(), sc.nextInt());
        return LocalDateTime.of(date, time);
    }

    private Theater selectTheater() {
        String region = selectRegion();
        String location = selectLocation(region);
        Cinema cinema = cinemaService.findCinemaByLocation(region, location);
        renderTheater(cinema);
        String theaterName = sc.next();
        return theaterService.findTheater(cinema.getRegion(), cinema.getLocation(), theaterName);
    }

    private String selectRegion() {
        renderRegion();
        return sc.next();
    }

    private void renderRegion() {
        System.out.println("                            지역선택");
        System.out.println("=====================================================================");
        cinemaService.findAll().stream()
                .map(Cinema::getRegion)
                .distinct()
                .forEach(System.out::println);
        System.out.print("지역을 선택하세요 >>");
    }

    private String selectLocation(String region) {
        renderLocation(region);
        return sc.next();
    }

    private void renderLocation(String region) {
        System.out.println("                            영화관 선택");
        System.out.println("=====================================================================");
        cinemaService.findAll().stream()
                .filter(cinema -> cinema.getRegion().equals(region))
                .map(Cinema::getLocation)
                .forEach(System.out::println);
        System.out.print("영화관을 선택하세요 >>");
    }

    private void renderTheater(Cinema cinema) {
        System.out.println("                            상영관 선택");
        System.out.println("=====================================================================");
        theaterService.findByCinemaId(cinema.getId())
                .forEach(System.out::println);
        System.out.print("상영관을 선택하세요>> ");
    }

    private Movie selectMovie() {
        renderMovies();
        int pickedMovie = sc.nextInt();
        return movieService.findAll().get(pickedMovie);
    }

    private void renderMovies() {
        System.out.println("                            영화선택");
        System.out.println("=====================================================================");
        List<Movie> movies = movieService.findAll();
        int i = 0;
        for (Movie movie : movies) {
            System.out.println(i + ". " + movie.toString());
            i++;
        }
        System.out.println("=====================================================================");
        System.out.print("추가할 영화를 선택하세요.>> ");
    }

    private void renderManagerMenu() {
        System.out.println("                            영화관관리");
        System.out.println("=====================================================================");
        System.out.println("1.상영작 관리   |   2.영화관 좌석관리   |   3.종료");
        System.out.println("=====================================================================");
    }

    private Map<String, List<Seat>> getSeatMap(List<SeatRow> seatRows) {
        Map<String, List<Seat>> seatMap = new LinkedHashMap<>();
        for (SeatRow seatRow : seatRows) {
            seatMap.put(seatRow.getRowName(), seatService.findByRowNum(seatRow.getId()));
        }
        return seatMap;
    }

    private void renderSeatByMap(Map<String, List<Seat>> pickSeatMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("   ┌-----------------------------------------------┐\n")
                .append("   │           스          크          린           │\n")
                .append("   └-----------------------------------------------┘\n");
        for (String s : pickSeatMap.keySet()) {
            List<Seat> seats = pickSeatMap.get(s);
            sb.append(s).append("  ");
            for (Seat seat : seats) {
                sb.append(seat.printSeatStatus())
                        .append("");
            }
            sb.append("\n");
        }
        System.out.println(sb);
        System.out.println();
    }

    private Map<String, List<Seat>> selectSeats(Map<String, List<Seat>> seatMap) {
        String nextSeat = selectSeat();
        String[] seatInfo = splitRowAndCol(nextSeat);
        List<Seat> rowInfo = seatMap.get(seatInfo[0]);
        rowInfo.get(Integer.parseInt(seatInfo[1]) - 1).setSeatStatus(SeatStatus.REPAIR);
        seatMap.replace(seatInfo[0], rowInfo);
        return seatMap;
    }

    private String selectSeat() {
        System.out.print("사용중지 할 좌석을 선택하세요(예시: A10)>> ");
        return sc.next();
    }

    private String[] splitRowAndCol(String nextSeat) {
        String row = nextSeat.substring(0, 1);
        String col = nextSeat.substring(1);
        return new String[]{row, col};
    }

    private void updateSeatInfo(Map<String, List<Seat>> pickSeatMap) {
        List<Seat> reserveSeats = new ArrayList<>();
        for (String s : pickSeatMap.keySet()) {
            List<Seat> seats = pickSeatMap.get(s);
            for (Seat seat : seats) {
                seatService.updateSeat(seat);
            }
        }
    }
}
