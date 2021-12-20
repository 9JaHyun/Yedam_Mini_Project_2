package share.init;

import cinema.application.CinemaService;
import cinema.domain.Cinema;
import cinema.repository.CinemaRepository;

import java.util.ArrayList;
import java.util.List;

public class CinemaInit {
    CinemaService cinemaService = new CinemaService(new CinemaRepository());
    public void init() {
        cinemaService.deleteAll();
        List<Cinema> cinemas = List.of(
                new Cinema("대구", "대구이시아")
                , new Cinema("대구", "대구(칠성로)")
                , new Cinema("대구", "대구신세계(동대구)")
                , new Cinema("대구", "북대구(칠곡)")
                , new Cinema("서울", "강남")
                , new Cinema("서울", "강동")
                , new Cinema("서울", "동대문")
                , new Cinema("서울", "마곡")
                , new Cinema("서울", "목동")
                , new Cinema("경기", "고양스타필드")
                , new Cinema("경기", "남양주")
                , new Cinema("경기", "동탄")
                , new Cinema("경기", "분당")
                , new Cinema("경기", "수원"));

        cinemas.iterator()
                .forEachRemaining(cinema -> cinemaService.save(cinema));

    }
}
