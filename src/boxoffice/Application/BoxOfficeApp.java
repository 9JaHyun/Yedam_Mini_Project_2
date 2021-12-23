package boxoffice.Application;

import boxoffice.boxofficelist.BoxOfficeService;
import boxoffice.boxofficelist.DailyBoxOfficeListDto;
import boxoffice.boxofficelist.WeekBoxOfficeListDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class BoxOfficeApp{
    private final BoxOfficeService boxOfficeService = new BoxOfficeService();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyymmdd");
    private final DateTimeFormatter dateTimeRenderFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
    private final DateTimeFormatter weekTimeRenderFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 W째주");



    public void run() throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean runApp = true;

        while (runApp) {
            renderBoxOfficeMenu();
            int selectMenu = scanner.nextInt();
            switch (selectMenu) {
                case 1:
                    LocalDateTime today = LocalDateTime.now();
                    renderToday(today);
                    break;
                case 2:
                    LocalDateTime thisWeek = LocalDateTime.now();
                    renderThisWeek(thisWeek);
                    break;
                case 3:
                    System.out.println("종료합니다.");
                    runApp = false;
            }
        }
    }

    private int selectSearchMenu(Scanner scanner) {
        System.out.print("입력>> ");
        return scanner.nextInt();
    }

    private void renderThisWeek(LocalDateTime localDateTime) throws Exception {
        System.out.println("                 " + localDateTime.format(weekTimeRenderFormatter) + "의 박스오피스 순위");
        System.out.println("======================================================================");
        List<WeekBoxOfficeListDto> weekBoxOfficeListDtos = boxOfficeService.searchByWeek(localDateTime.format(dateTimeFormatter));
        if (weekBoxOfficeListDtos.size() == 0) {
            System.out.println("OOPS! 현재 통신이 원할하지 않습니다. 다시 시도해주세요");
        } else{
            renderWeekBoxOffice(weekBoxOfficeListDtos);
        }
    }

    private void renderToday(LocalDateTime localDateTime) throws Exception {
        System.out.println("                   " + localDateTime.format(dateTimeRenderFormatter) + "의 박스오피스 순위");
        System.out.println("======================================================================");
        List<DailyBoxOfficeListDto> dailyBoxOfficeListDtos = boxOfficeService.searchByToday(localDateTime.format(dateTimeFormatter));
        if (dailyBoxOfficeListDtos.size() == 0) {
            System.out.println("OOPS! 현재 통신이 원할하지 않습니다. 다시 시도해주세요");
        } else {
            renderTodayBoxOffice(dailyBoxOfficeListDtos);
        }
    }

    private void renderBoxOfficeMenu() {
        System.out.println("                박스오피스");
        System.out.println("=======================================");
        System.out.println("1. 오늘의 순위 | 2. 주간 순위 |  3. 종료");
        System.out.println("=======================================");
        System.out.print("입력>> ");
    }

    private void renderTodayBoxOffice(List<DailyBoxOfficeListDto> list) {
        System.out.println("순위  신규  순위변동           작품명");
        list.forEach(dailyBoxOfficeListDto -> System.out.printf("%2d   " +
                        "%s   " +
                        "%2s      " +
                        "%-35s\n",
                Integer.parseInt(dailyBoxOfficeListDto.getRank()),
                (dailyBoxOfficeListDto.getRankOldAndNew().equals("NEW") ? " NEW " : "  -  "),
                renderRankInten(dailyBoxOfficeListDto),
                dailyBoxOfficeListDto.getMovieNm()));
        System.out.println("======================================================================");
        DailyBoxOfficeListDto rank1 = list.get(0);
        System.out.printf("현재 박스오피스 1위 %s의 누적관객수는 %s명 입니다.", rank1.getMovieNm(), rank1.getAudiAcc());
        System.out.println();
    }

    private void renderWeekBoxOffice(List<WeekBoxOfficeListDto> list) {
        System.out.println("순위  신규  순위변동           작품명");
        list.forEach(weekBoxOfficeListDto -> System.out.printf("%2d   " +
                        "%s   " +
                        "%2s      " +
                        "%-35s\n",
                Integer.parseInt(weekBoxOfficeListDto.getRank()),
                (weekBoxOfficeListDto.getRankOldAndNew().equals("NEW") ? " NEW " : "  -  "),
                renderRankInten(weekBoxOfficeListDto),
                weekBoxOfficeListDto.getMovieNm()));
        System.out.println("======================================================================");
        WeekBoxOfficeListDto rank1 = list.get(0);
        System.out.printf("이주 월요일 박스오피스 1위 %s의 누적관객수는 %s명 입니다.", rank1.getMovieNm(), rank1.getAudiAcc());
        System.out.println();
    }

    private String renderRankInten(DailyBoxOfficeListDto dailyBoxOfficeListDto) {
        int num = Integer.parseInt(dailyBoxOfficeListDto.getRankInten());
        if (num > 0) {
            return "▲ " + num;
        } else if (num == 0) {
            return "-  ";
        }
        return "▼ " + Math.abs(num);
    }

    private String renderRankInten(WeekBoxOfficeListDto weekBoxOfficeListDto) {
        int num = Integer.parseInt(weekBoxOfficeListDto.getRankInten());
        if (num > 0) {
            return "▲ " + num;
        } else if (num == 0) {
            return "-  ";
        }
        return "▼ " + Math.abs(num);
    }
}
