package member.application;

import boxoffice.Application.BoxOfficeApp;
import member.Repository.MemberRepository;
import member.domain.Member;
import reservation.application.ReservationApp;
import reservation.application.ReservationService;
import reservation.domain.Reservation;
import reservation.domain.ReservationSeats;
import reservation.domain.ReservationStatus;
import reservation.repository.ReservationRepository;
import reservation.repository.ReservationSeatsRepository;
import share.App;

import java.util.List;
import java.util.Scanner;

public class MemberApp {
    private final MemberService managerService = new MemberService(new MemberRepository());
    private final ReservationService reservationService =
            new ReservationService(new ReservationRepository(), new ReservationSeatsRepository());
    private ReservationApp reservationApp = new ReservationApp();
    private App app;


    private void setApp(App app) {
        this.app = app;
    }

    Scanner sc = new Scanner(System.in);

    public void run(Member member) throws Exception {
        boolean runApp = true;
        while (runApp) {
            renderFirstMenu(member);
            int firstMenuChoose = sc.nextInt();
            switch (firstMenuChoose) {
                case 1:
                    reservationApp.run(member);
                    break;
                case 2: {
                    List<Reservation> reservationList = reservationService.findByMember(member.getId());
                    renderReservations(reservationList);
                    System.out.print("상세보기를 원하시면 번호를 입력하세요(나가기: 0)>> ");
                    int selectReservation = sc.nextInt();
                    if (selectReservation == 0) {
                        break;
                    }
                    if (reservationList.get(selectReservation - 1) == null) {
                        System.out.println("잘못된 입력입니다.");
                        break;
                    }
                    String s = renderDetail(reservationList.get(selectReservation - 1));
                    if (s.equals("Y") || s.equals("y")) {
                        refund(reservation);
                    }

                    break;
                }
                case 3: {
                    setApp(new BoxOfficeApp());
                    app.run();
                    break;
                }
                case 4: {
                    renderPasswordChangePage();
                    if (checkYesOrNo(sc.next())) {
                        changePassword(member);
                    }
                    break;
                }
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

    private void renderPasswordChangePage() {
        System.out.println("                           비밀번호 변경");
        System.out.println("=====================================================================");
        System.out.print("비밀번호를 변경하려면 Y(y)를 입력하세요>> ");
    }

    private void renderFirstMenu(Member member) {
        System.out.println("                            Yedam 영화관");
        System.out.println("=====================================================================");
        System.out.println("                                           " + member.getName() + "님 안녕하세요");
        System.out.println();
        System.out.println("1.예매 하기 | 2.예매 정보 확인 | 3.박스오피스 | 4.비밀번호 변경 | 5.로그아웃");
        System.out.println("=====================================================================");
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

    private void renderReservations(List<Reservation> reservationList) {
        System.out.println();
        System.out.println("                          예매 목록.");
        System.out.println("=====================================================================");
        int i = 1;
        for (Reservation reservation : reservationList) {
            System.out.println(i + ".   " + reservation.toString());
            i++;
        }
        System.out.println();
        System.out.println("=====================================================================");
    }

    private String renderDetail(Reservation reservation) {
        System.out.println();
        System.out.println("                             상세 정보.");
        System.out.println("=====================================================================");
        System.out.println("영화정보: " + reservation.getScreening());
        System.out.println("인   원: 일반 - " + reservation.getAudienceCount());
//        System.out.println("좌   석: " + reservation.get);
        System.out.println("결제금액: " + reservation.getFee() + "원");
        System.out.print("좌   석: ");
        List<ReservationSeats> seats1 = reservationService.findSeats(reservation.getId());
        seats1.iterator().forEachRemaining(seats -> System.out.print(seats + "   "));
        System.out.println();
        System.out.println("=====================================================================");
        if (reservation.getStatus() != ReservationStatus.WATCHING) {
            System.out.println("환불하시겠습니까?(Y/N)>>");
            return sc.next();
        }
        System.out.println("=====================================================================");
        return "";
    }

    private void refund(Reservation reservation) {
        reservationService.refund(reservation);
    }
}