package reservation.application;

import cinema.application.SeatRowService;
import cinema.application.SeatService;
import cinema.domain.SeatRow;
import cinema.repository.SeatRepository;
import cinema.repository.SeatRowRepository;
import member.domain.Member;
import movie.domain.Movie;
import reservation.domain.Reservation;
import reservation.repository.ReservationRepository;
import screening.application.ScreeningService;
import screening.domain.Screening;
import screening.repository.ScreeningRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservationApp {
    ReservationService reservationService = new ReservationService(new ReservationRepository());
    ScreeningService screeningService = new ScreeningService(new ScreeningRepository());
    SeatService seatService = new SeatService(new SeatRepository());
    SeatRowService seatRowService = new SeatRowService(new SeatRowRepository());

    Scanner sc = new Scanner(System.in);

    public void run(Member member) {
        // 영화 선택
        List<Screening> screeningsByDate = selectScreeningsByDate();
        List<Screening> screeningsByTitle = selectScreeningByTitle(screeningsByDate);
        Long cinemaId = selectCinema(screeningsByTitle);
        List<Screening> finalScreenings = screeningService.findByCinema(screeningsByTitle, cinemaId);
        Screening finalScreening = selectFinalScreening(finalScreenings);

        // 인원 선택
        int[] audiences = selectAudienceCount();
        while(audiences[0] == 0){
            audiences = selectAudienceCount();
        }

        // 좌석 선택
        Long theaterId = finalScreening.getTheater().getId();
        List<SeatRow> seatRows = seatRowService.selectByTheater(theaterId);
        renderSeat(seatRows);
        List<String> selectSeats = selectSeat(audiences, seatRows);


        // 최종
        renderFinalReservation(finalScreening);
        System.out.println("결제하시겠습니까?(Y/N)>> ");
        String yesOrNo = sc.next();
        if (!checkYesOrNo(yesOrNo)) {
            return;
        }

        // 결제
        int totalAudienceCount = audiences[1] + audiences[2] + audiences[3];
        Reservation reservation = finalScreening.reserve(member, totalAudienceCount);

        // 예약
        System.out.println("=================== 예약완료! ===================");
        reservationService.save(reservation);

    }

    private void renderFinalReservation(Screening finalScreening) {
        System.out.println("================== 예매 정보 ==================");
        System.out.println("영화 제목: " + finalScreening.getMovie().getTitle());
        System.out.println("시작 시각: " + finalScreening.getStartTime());
        System.out.println("종료 시각: " + finalScreening.getStartTime().plus(finalScreening.getMovie().getRunningTime()));
        System.out.println("상영 위치: " + finalScreening.getTheater().getName());
        System.out.println("=============================================");
    }

    private List<String> selectSeat(int[] audiences, List<SeatRow> seatRows) {
        int totalAudienceCount = audiences[1] + audiences[2] + audiences[3];
        List<String> seatList = new ArrayList<>();
        for (int i = totalAudienceCount; i < 0; i--) {
            System.out.print("좌석 선택(ex: A1, A6)>> ");
            String seat = sc.next();
            seatList.add(seat);
        }
        return seatList;
    }

    private void renderSeat(List<SeatRow> seatRows) {
        StringBuilder sb = new StringBuilder();
        List<StringBuilder> sbList = new ArrayList<>();
        sb.append("   ┌-----------------------------------------------┐\n")
        .append("   │           스          크          린           │\n")
        .append("   └-----------------------------------------------┘\n");
        for (SeatRow seatRow : seatRows) {
            sb.append(seatRow.getRowName())
                    .append("  ")
                    .append(seatService.renderSeatRow(seatRow.getId()))
                    .append("\n");
        }
        System.out.println(sb);
        System.out.println();
    }

    private int[] selectAudienceCount() {
        System.out.println();
        boolean audienceRunner = true;
        int normalAudienceCount = 0;
        int youthAudienceCount = 0;
        int preferentialAudienceCount = 0;

        while(audienceRunner) {
            System.out.println("========== 인원수 선택 ==========");
            System.out.print("일반>> ");
            normalAudienceCount = sc.nextInt();
            boolean a = validateAudienceCount(normalAudienceCount);
            System.out.print("청소년>> ");
            youthAudienceCount = sc.nextInt();
            boolean b = validateAudienceCount(youthAudienceCount);
            System.out.print("우대>> ");
            preferentialAudienceCount = sc.nextInt();
            boolean c = validateAudienceCount(preferentialAudienceCount);
            audienceRunner = !(a && b && c);
        }
        System.out.println("====== 인원 =====");
        renderAudienceCount(normalAudienceCount, youthAudienceCount, preferentialAudienceCount);
        System.out.print("확인(Y/N)>> ");
        String next = sc.next();
        int result = checkYesOrNoToInt(next);

        System.out.println("================");
        return new int[]{result, normalAudienceCount, youthAudienceCount, preferentialAudienceCount};
    }

    private int checkYesOrNoToInt(String next) {
        return next.equals("Y") || next.equals("y") ? 1 : 0;
    }

    private boolean checkYesOrNo(String next) {
        return next.equals("Y") || next.equals("y");
    }

    private boolean validateAudienceCount(int count) {
        if(count < 0){
            System.out.println("잘못된 입력입니다. 입력: " + count);
            return false;
        }
        return true;
    }

    private void renderAudienceCount(int normalAudienceCount, int youthAudienceCount, int preferentialAudienceCount) {
        System.out.println("일반: " + normalAudienceCount);
        System.out.println("청소년: " + youthAudienceCount);
        System.out.println("우대: " + preferentialAudienceCount);
    }

    private List<Screening> selectScreeningsByDate() {
        renderDate();
        LocalDate selectDate = selectDate();
        renderScreeningsByDate(selectDate);
        return screeningService.findByDate(selectDate);
    }

    private LocalDate selectDate() {
        System.out.print("예매 일시를 선택하세요>> ");
        int selectDate = sc.nextInt();
        return LocalDate.now().plusDays(selectDate);
    }

    private Long selectCinema(List<Screening> screenings) {
        List<Screening> screeningsByRegion = selectByRegion(screenings);
        return selectByLocation(screeningsByRegion);
    }

    private void renderDate() {
        System.out.println();
        System.out.println("=========== 날짜 선택 ===========");
        for (int j = 0; j <= 7 ; j++) {
            System.out.println(j + ". " + LocalDate.now().plusDays(j).toString());
        }
        System.out.println("===============================");
    }

    private void renderScreeningsByDate(LocalDate localDate) {
        System.out.println();
        System.out.println("=========== 상영작 선택 ===========");
        screeningService.findByDate(localDate).stream()
                .map(Screening::getMovie)
                .map(Movie::getTitle)
                .distinct()
                .forEach(System.out::println);
        System.out.println("===============================");
    }

    private String selectTitle() {
        System.out.print("상영작을 선택하세요>> ");
        return sc.next();
    }

    private List<Screening> selectScreeningByTitle(List<Screening> screenings) {
        String title = selectTitle();
        return screeningService.findByTitle(screenings, title);
    }

    private List<Screening> selectByRegion(List<Screening> screenings) {
        System.out.println();
        System.out.println("=========== 지역 선택 ===========");
        screenings.stream().map(screening -> screening.getTheater().getCinema().getRegion())
                .distinct()
                .forEach(System.out::println);
        System.out.println("===============================");
        System.out.print("지역을 선택하세요>> ");
        String region = sc.next();
        return screeningService.findCinemaByRegion(screenings, region);
    }

    private Long selectByLocation(List<Screening> screenings) {
        System.out.println();
        System.out.println("=========== " + screenings.get(0).getTheater().getCinema().getRegion() + " 지점 선택 ===========");
        screenings.stream().map(screening -> screening.getTheater().getCinema().getLocation())
                .distinct()
                .forEach(System.out::println);
        System.out.println("===============================");
        System.out.print("지점을 선택하세요>> ");
        String location = sc.next();
        return screeningService.findCinemaByLocation(screenings, location);
    }

    private Screening selectFinalScreening(List<Screening> screenings) {
        System.out.println();
        System.out.println("================= 시간대 목록 =================");
        for (int i = 0; i < screenings.size(); i++) {
            System.out.println(i + ". " + screenings.get(i).toString());
        }
        System.out.println("=================================");
        System.out.print("영화 선택>> ");
        return screenings.get(sc.nextInt());
    }

    private int checkDiscountPolicy(Screening screening) {
        LocalTime earlyMorning = LocalTime.of(8, 0);
        if (screening.getStartTime().toLocalTime().isBefore(earlyMorning)) {
            return 2000;
        }
        return 0;
    }
}
