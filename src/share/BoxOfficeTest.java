package share;

import boxoffice.Application.BoxOfficeApp;
import boxoffice.api.BoxOfficeClient;
import boxoffice.api.dto.DailyBoxOfficeRequestDto;
import boxoffice.api.dto.DailyBoxOfficeResponseDto;
import boxoffice.api.dto.WeekBoxOfficeRequestDto;
import boxoffice.api.dto.WeekBoxOfficeResponseDto;

public class BoxOfficeTest {
    public static void main(String[] args) throws Exception {
        BoxOfficeApp boxOfficeApp = new BoxOfficeApp();
        boxOfficeApp.run();
    }
}
