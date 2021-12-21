package boxoffice.Application;

import boxoffice.boxofficelist.BoxOfficeService;
import boxoffice.boxofficelist.DailyBoxOfficeListDto;
import boxoffice.boxofficelist.WeekBoxOfficeListDto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class BoxOfficeApp {
    BoxOfficeService boxOfficeService = new BoxOfficeService();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmdd");
    SimpleDateFormat dateTimeRenderFormatter = new SimpleDateFormat("yyyy년 mm월 dd일");
    SimpleDateFormat weekTimeRenderFormatter = new SimpleDateFormat("yyyy년 mm월 W째주");
    Calendar cal = Calendar.getInstance();
    Scanner scanner = new Scanner(System.in);

    public void run() throws Exception {
        boolean runApp = true;

        while (runApp){
            renderBoxOfficeMenu();
            int selectMenu = scanner.nextInt();
            switch (selectMenu){
                case 1:
                    System.out.println("                      " + dateTimeRenderFormatter.format(cal.getTime()) + "의 박스오피스 순위");
                    System.out.println("======================================================================");
                    List<DailyBoxOfficeListDto> dailyBoxOfficeListDtos = boxOfficeService.searchByToday(simpleDateFormat.format(cal.getTime()));
                    renderTodayBoxOffice(dailyBoxOfficeListDtos);
                    break;
                case 2:
                    System.out.println("                    " + weekTimeRenderFormatter.format(cal.getTime()) + "의 박스오피스 순위");
                    System.out.println("======================================================================");
                    List<WeekBoxOfficeListDto> weekBoxOfficeListDtos = boxOfficeService.searchByWeek(simpleDateFormat.format(cal.getTime()));
                    renderWeekBoxOffice(weekBoxOfficeListDtos);
                    break;
                case 3:
                    System.out.println("종료합니다.");
                    runApp = false;
            }
        }
    }

    private void renderBoxOfficeMenu() {
        System.out.println("        박스오피스");
        System.out.println("===========================");
        System.out.println("1. 오늘의 순위 | 2. 주간 순위");
        System.out.println("===========================");
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
        System.out.println("============================================================");
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
        System.out.println("============================================================");
        WeekBoxOfficeListDto rank1 = list.get(0);
        System.out.printf("현재 박스오피스 1위 %s의 누적관객수는 %s명 입니다.", rank1.getMovieNm(), rank1.getAudiAcc());
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

    private String findFirstDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return simpleDateFormat.format(cal.getTime());

    }
}
