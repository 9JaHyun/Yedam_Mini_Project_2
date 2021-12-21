package boxoffice.boxofficelist;

import boxoffice.api.BoxOfficeClient;
import boxoffice.api.dto.DailyBoxOfficeRequestDto;
import boxoffice.api.dto.DailyBoxOfficeResponseDto;
import boxoffice.api.dto.WeekBoxOfficeRequestDto;
import boxoffice.api.dto.WeekBoxOfficeResponseDto;

import java.util.ArrayList;
import java.util.List;

import static boxoffice.api.dto.DailyBoxOfficeResponseDto.*;
import static boxoffice.api.dto.WeekBoxOfficeResponseDto.*;

public class BoxOfficeService {
    private final BoxOfficeClient boxOfficeClient = new BoxOfficeClient();

    public List<DailyBoxOfficeListDto> searchByToday(String date) throws Exception {
        DailyBoxOfficeRequestDto requestDto = new DailyBoxOfficeRequestDto(date);
        DailyBoxOfficeResponseDto responseDto = boxOfficeClient.searchToday(requestDto);
        List<DailyBoxOfficeListDto> listDtos = new ArrayList<>();

        List<DailyBoxOfficeList> movieList = responseDto.getBoxOfficeResult().getDailyBoxOfficeList();

        for (DailyBoxOfficeList dailyBoxOfficeList : movieList) {
            listDtos.add(fillListDto(dailyBoxOfficeList));
        }
        return listDtos;
    }

    public List<WeekBoxOfficeListDto> searchByWeek(String date) throws Exception {
        WeekBoxOfficeRequestDto requestDto = new WeekBoxOfficeRequestDto(date);
        WeekBoxOfficeResponseDto responseDto = boxOfficeClient.searchWeek(requestDto);
        List<WeekBoxOfficeListDto> listDtos = new ArrayList<>();

        List<WeekBoxOfficeList> movieList = responseDto.getBoxOfficeResult().getWeeklyBoxOfficeList();

        for (WeekBoxOfficeList weekBoxOfficeList : movieList) {
            listDtos.add(fillListDto(weekBoxOfficeList));
        }
        return listDtos;
    }

    private DailyBoxOfficeListDto fillListDto(DailyBoxOfficeList dailyBoxOfficeList) {
        DailyBoxOfficeListDto result = new DailyBoxOfficeListDto();
        result.setRank(dailyBoxOfficeList.getRank());
        result.setRankInten(dailyBoxOfficeList.getRankInten());
        result.setRankOldAndNew(dailyBoxOfficeList.getRankOldAndNew());
        result.setMovieNm(dailyBoxOfficeList.getMovieNm());
        result.setAudiAcc(dailyBoxOfficeList.getAudiAcc());
        return result;
    }

    private WeekBoxOfficeListDto fillListDto(WeekBoxOfficeList weekBoxOfficeList) {
        WeekBoxOfficeListDto result = new WeekBoxOfficeListDto();
        result.setRank(weekBoxOfficeList.getRank());
        result.setRankInten(weekBoxOfficeList.getRankInten());
        result.setRankOldAndNew(weekBoxOfficeList.getRankOldAndNew());
        result.setMovieNm(weekBoxOfficeList.getMovieNm());
        result.setAudiAcc(weekBoxOfficeList.getAudiAcc());
        return result;
    }
}
