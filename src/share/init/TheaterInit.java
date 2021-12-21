package share.init;

import cinema.application.CinemaService;
import cinema.application.TheaterService;
import cinema.domain.Cinema;
import cinema.domain.Theater;
import cinema.repository.CinemaRepository;
import cinema.repository.TheaterRepository;

import java.util.List;

public class TheaterInit {
    TheaterService theaterService = new TheaterService(new TheaterRepository(), new CinemaRepository());
    CinemaService cinemaService = new CinemaService(new CinemaRepository());

    public void init() {
        theaterService.deleteAll();
        Cinema s1 = cinemaService.findCinemaByLocation("서울", "강동");
        Cinema s2 = cinemaService.findCinemaByLocation("서울", "강남");
        Cinema s3 = cinemaService.findCinemaByLocation("서울", "동대문");
        Cinema kk1 = cinemaService.findCinemaByLocation("경기", "고양스타필드");
        Cinema kk2 = cinemaService.findCinemaByLocation("경기", "분당");
        Cinema kk3 = cinemaService.findCinemaByLocation("경기", "수원");
        Cinema d1 = cinemaService.findCinemaByLocation("대구", "대구신세계(동대구)");
        Cinema d2 = cinemaService.findCinemaByLocation("대구", "대구이시아");
        Cinema d3 = cinemaService.findCinemaByLocation("대구", "대구(칠성로)");

        List<Theater> theaters = List.of(
                new Theater("1관", s1)
                , new Theater("2관", s1)
                , new Theater("3관", s1)
                , new Theater("4관", s1)
                , new Theater("5관", s1)
                , new Theater("1관", s2)
                , new Theater("2관", s2)
                , new Theater("3관", s2)
                , new Theater("4관", s2)
                , new Theater("5관", s2)
                , new Theater("1관", s3)
                , new Theater("2관", s3)
                , new Theater("3관", s3)
                , new Theater("4관", s3)
                , new Theater("5관", s3)

                , new Theater("1관", kk1)
                , new Theater("2관", kk1)
                , new Theater("3관", kk1)
                , new Theater("4관", kk1)
                , new Theater("5관", kk1)
                , new Theater("1관", kk2)
                , new Theater("2관", kk2)
                , new Theater("3관", kk2)
                , new Theater("4관", kk2)
                , new Theater("5관", kk2)
                , new Theater("1관", kk3)
                , new Theater("2관", kk3)
                , new Theater("3관", kk3)
                , new Theater("4관", kk3)
                , new Theater("5관", kk3)

                , new Theater("1관", d1)
                , new Theater("2관", d1)
                , new Theater("3관", d1)
                , new Theater("4관", d1)
                , new Theater("5관", d1)
                , new Theater("1관", d2)
                , new Theater("2관", d2)
                , new Theater("3관", d2)
                , new Theater("4관", d2)
                , new Theater("5관", d2)
                , new Theater("1관", d3)
                , new Theater("2관", d3)
                , new Theater("3관", d3)
                , new Theater("4관", d3)
                , new Theater("5관", d3));

        for (Theater theater : theaters) {
            theaterService.save(theater);
        }


    }
}
