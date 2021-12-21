package boxoffice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeekBoxOfficeResponseDto {
    private BoxOfficeResult boxOfficeResult;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BoxOfficeResult {
        private String boxofficeType; // 박스오피스 종류를 출력합니다.
        private String showRange; // 박스오피스 조회 일자를 출력합니다.
        private String yearWeekTime;
        private List<WeekBoxOfficeList> weeklyBoxOfficeList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WeekBoxOfficeList {
        private String rnum;                // 순번을 출력합니다.
        private String rank;                // 해당일자의 박스오피스 순위를 출력합니다.
        private String rankInten;            // 전일대비 순위의 증감분을 출력합니다.
        private String rankOldAndNew;        // 랭킹에 신규진입여부를 출력합니다. “OLD” : 기존 , “NEW” : 신규
        private String movieCd;                // 영화의 대표코드를 출력합니다.
        private String movieNm;                // 영화명(국문)을 출력합니다.
        private String openDt;                // 영화의 개봉일을 출력합니다.
        private String salesAmt;            // 해당일의 매출액을 출력합니다.
        private String salesShare;            // 해당일자 상영작의 매출총액 대비 해당 영화의 매출비율을 출력합니다.
        private String salesInten;            // 전일 대비 매출액 증감분을 출력합니다.
        private String salesChange;            // 전일 대비 매출액 증감 비율을 출력합니다.
        private String salesAcc;            // 누적매출액을 출력합니다.
        private String audiCnt;                // 해당일의 관객수를 출력합니다.
        private String audiInten;            // 전일 대비 관객수 증감분을 출력합니다.
        private String audiChange;            // 전일 대비 관객수 증감 비율을 출력합니다.
        private String audiAcc;                // 누적관객수를 출력합니다.
        private String scrnCnt;                // 해당일자에 상영한 스크린수를 출력합니다.
        private String showCnt;                // 해당일자에 상영된 횟수를 출력합니다.
    }
}
