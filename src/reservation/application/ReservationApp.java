package reservation.application;

import cinema.application.SeatRowService;
import cinema.application.SeatService;
import cinema.domain.Seat;
import cinema.domain.SeatRow;
import cinema.domain.SeatStatus;
import cinema.repository.SeatRepository;
import cinema.repository.SeatRowRepository;
import member.domain.Member;
import movie.domain.Movie;
import reservation.domain.Reservation;
import reservation.domain.ReservationSeats;
import reservation.repository.ReservationRepository;
import reservation.repository.ReservationSeatsRepository;
import screening.application.ScreeningService;
import screening.domain.Screening;
import screening.repository.ScreeningRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReservationApp {
    ReservationService reservationService = new ReservationService(new ReservationRepository(),
                                                                new ReservationSeatsRepository());
    ReservationSeatsService reservationSeatsService = new ReservationSeatsService(new ReservationSeatsRepository());
    ScreeningService screeningService = new ScreeningService(new ScreeningRepository());
    SeatService seatService = new SeatService(new SeatRepository());
    SeatRowService seatRowService = new SeatRowService(new SeatRowRepository());
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
    StringBuilder sb = new StringBuilder();

    Scanner sc = new Scanner(System.in);

    public void run(Member member) {
        boolean seatSelect = true;

        // 영화 선택
        List<Screening> screeningsByDate = selectScreeningsByDate();
        List<Screening> screeningsByTitle = selectScreeningByTitle(screeningsByDate);
        Long cinemaId = selectCinema(screeningsByTitle);
        List<Screening> finalScreenings = screeningService.findByCinema(screeningsByTitle, cinemaId);
        Screening finalScreening = selectFinalScreening(finalScreenings);

        // 인원 선택
        int audiences = selectAudienceCount();


        // 좌석 선택
        Long theaterId = finalScreening.getTheater().getId();
        List<SeatRow> seatRows = seatRowService.selectByTheater(theaterId);

        Map<String, List<Seat>> seatMap = getSeatMap(seatRows);
        Map<String, List<Seat>> tempSeatMap = getSeatMap(seatRows);
        while(seatSelect) {
            renderSeatByMap(seatMap);
            tempSeatMap = selectSeats(audiences, tempSeatMap);
            renderSeatByMap(tempSeatMap);

            System.out.print("확정하시겠습니까?(Y/N) >>");
            if (checkYesOrNo(sc.next())) {
                seatSelect = false;
            }
            tempSeatMap = seatMap;
        }
        List<Seat> seats = reserveSeat(tempSeatMap);

        // 최종
        renderFinalReservation(finalScreening, audiences, seats);
        System.out.print("확정하시겠습니까?(Y/N)>> ");
        String yesOrNo = sc.next();
        if (!checkYesOrNo(yesOrNo)) {
            return;
        }

        // 결제
        Reservation reservation = finalScreening.reserve(member, audiences);
        System.out.println("결제 금액은: " + reservation.getFee() + "원 입니다.");
        System.out.print("결제하시겠습니까?(Y/N)>> ");
        yesOrNo = sc.next();
        if (!checkYesOrNo(yesOrNo)) {
            return;
        }
        Long id = reservationService.save(reservation);
        reservation.setId(id);
        for (Seat seat : seats) {
            seat.setSeatStatus(SeatStatus.RESERVED);
            ReservationSeats reservationSeats = new ReservationSeats(reservation, seat);
            reservationSeatsService.saveSeat(reservationSeats);
            seatService.updateSeat(seat);
        }

        // 예약
        System.out.println("결제 완료!");
        System.out.println("회원페이지로 이동합니다.");
    }


    private void renderFinalReservation(Screening finalScreening, int audienceCount, List<Seat> seats) {
        System.out.println("                             예매 정보");
        System.out.println("=====================================================================");
        System.out.println("영화 제목: " + finalScreening.getMovie().getTitle());
        System.out.println("시작 시각: " + finalScreening.getStartTime().format(dateTimeFormatter));
        System.out.println("종료 시각: " + finalScreening.getStartTime().plus(finalScreening.getMovie().getRunningTime()).format(dateTimeFormatter));
        System.out.println("인    원: " + "일반 - " + audienceCount + "명");
        System.out.print("좌    석: ");
        seats.iterator().forEachRemaining(seat -> System.out.print(seat + "   "));
        System.out.println();
        System.out.println("=====================================================================");
    }

    private Map<String, List<Seat>> selectSeats(int audiences, Map<String, List<Seat>> seatMap) {
        for (int i = audiences; i > 0; i--) {
            String nextSeat = selectSeat();
            String[] seatInfo = splitRowAndCol(nextSeat);
            List<Seat> rowInfo = seatMap.get(seatInfo[0]);
            rowInfo.get(Integer.parseInt(seatInfo[1]) - 1).setSeatStatus(SeatStatus.PICKED);
            seatMap.replace(seatInfo[0], rowInfo);
        }

        return seatMap;
    }

    private String selectSeat() {
        System.out.print("좌석을 선택하세요(예시: A10)>> ");
        return sc.next();
    }

    private String[] splitRowAndCol(String nextSeat) {
        String row = nextSeat.substring(0, 1);
        String col = nextSeat.substring(1);
        return new String[]{row, col};
    }

    private Map<String, List<Seat>> getSeatMap(List<SeatRow> seatRows) {
        Map<String, List<Seat>> seatMap = new LinkedHashMap<>();
        for (SeatRow seatRow : seatRows) {
            seatMap.put(seatRow.getRowName(), seatService.findByRowNum(seatRow.getId()));
        }
        return seatMap;
    }

    private void renderSeatByMap(Map<String, List<Seat>> pickSeatMap) {
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

    private List<Seat> reserveSeat(Map<String, List<Seat>> pickSeatMap) {
        List<Seat> reserveSeats = new ArrayList<>();
        for (String s : pickSeatMap.keySet()) {
            List<Seat> seats = pickSeatMap.get(s);
            for (Seat seat : seats) {
                if (seat.getSeatStatus() == SeatStatus.PICKED) {
                    reserveSeats.add(seat);
                    seatService.updateSeat(seat);
                }
            }
        }
        return reserveSeats;
    }

    private int selectAudienceCount() {
        System.out.println();
        boolean audienceRunner = true;
        int normalAudienceCount = 0;

        while (audienceRunner) {
            System.out.println("                             인원수 선택");
            System.out.println("=====================================================================");
            System.out.print("일반>> ");
            normalAudienceCount = sc.nextInt();
            boolean a = validateAudienceCount(normalAudienceCount);
            System.out.println("=====================================================================");
            System.out.println();
            System.out.println("                              인원");
            System.out.println("=====================================================================");
            System.out.println("일반: " + normalAudienceCount);
            System.out.println("=====================================================================");
            System.out.print("확인(Y/N)>> ");
            String next = sc.next();
            audienceRunner = a && !checkYesOrNo(next);
        }
        return normalAudienceCount;
    }

    private boolean checkYesOrNo(String next) {
        return next.equals("Y") || next.equals("y");
    }

    private boolean validateAudienceCount(int count) {
        if (count < 0) {
            System.out.println("잘못된 입력입니다. 입력: " + count);
            return false;
        }
        return true;
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
        System.out.println("                         날짜 선택");
        System.out.println("=====================================================================");
        for (int j = 0; j <= 7; j++) {
            System.out.println(j + ". " + LocalDate.now().plusDays(j));
        }
        System.out.println("=====================================================================");
    }

    private void renderScreeningsByDate(LocalDate localDate) {
        System.out.println("                             상영작 선택");
        System.out.println("=====================================================================");
        screeningService.findByDate(localDate).stream()
                .map(Screening::getMovie)
                .map(Movie::getTitle)
                .distinct()
                .forEach(System.out::println);
        System.out.println("=====================================================================");
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
        System.out.println("                              지역 선택");
        System.out.println("=====================================================================");
        screenings.stream().map(screening -> screening.getTheater().getCinema().getRegion())
                .distinct()
                .forEach(System.out::println);
        System.out.println("=====================================================================");
        System.out.print("지역을 선택하세요>> ");
        String region = sc.next();
        return screeningService.findCinemaByRegion(screenings, region);
    }

    private Long selectByLocation(List<Screening> screenings) {
        System.out.println("                                " + screenings.get(0).getTheater().getCinema().getRegion() + " 지점 선택");
        System.out.println("=====================================================================");
        screenings.stream().map(screening -> screening.getTheater().getCinema().getLocation())
                .distinct()
                .forEach(System.out::println);
        System.out.println("=====================================================================");
        System.out.print("지점을 선택하세요>> ");
        String location = sc.next();
        return screeningService.findCinemaByLocation(screenings, location);
    }

    private Screening selectFinalScreening(List<Screening> screenings) {
        System.out.println("                            시간대 목록");
        System.out.println("=====================================================================");
        for (int i = 0; i < screenings.size(); i++) {
            System.out.println(i + ". " + screenings.get(i).toString());
        }
        System.out.println("=====================================================================");
        System.out.print("영화 선택>> ");
        return screenings.get(sc.nextInt());
    }
}
