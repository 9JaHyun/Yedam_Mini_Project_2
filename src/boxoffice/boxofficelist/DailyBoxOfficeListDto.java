package boxoffice.boxofficelist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyBoxOfficeListDto {
    private String rank;                // 해당일자의 박스오피스 순위를 출력합니다.
    private String rankInten;            // 전일대비 순위의 증감분을 출력합니다.
    private String rankOldAndNew;        // 랭킹에 신규진입여부를 출력합니다. “OLD” : 기존 , “NEW” : 신규
    private String movieNm;                // 영화명(국문)을 출력합니다.
    private String audiAcc;                // 누적관객수를 출력합니다.
}
