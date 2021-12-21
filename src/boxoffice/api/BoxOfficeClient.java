package boxoffice.api;

import boxoffice.api.dto.DailyBoxOfficeRequestDto;
import boxoffice.api.dto.DailyBoxOfficeResponseDto;
import boxoffice.api.dto.WeekBoxOfficeRequestDto;
import boxoffice.api.dto.WeekBoxOfficeResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;

public class BoxOfficeClient {

    public DailyBoxOfficeResponseDto searchToday(DailyBoxOfficeRequestDto requestDto) throws Exception {
        KobisOpenAPIRestService kobisOpenAPIRestService = new KobisOpenAPIRestService(requestDto.getKey());
        String dailyBoxOffice = kobisOpenAPIRestService.getDailyBoxOffice(true, requestDto.toLinkedHashMap());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(dailyBoxOffice, DailyBoxOfficeResponseDto.class);
    }

    public WeekBoxOfficeResponseDto searchWeek(WeekBoxOfficeRequestDto requestDto) throws Exception {
        KobisOpenAPIRestService kobisOpenAPIRestService = new KobisOpenAPIRestService(requestDto.getKey());
        String weeklyBoxOffice = kobisOpenAPIRestService.getWeeklyBoxOffice(true, requestDto.toLinkedHashMap());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(weeklyBoxOffice, WeekBoxOfficeResponseDto.class);
    }


}
