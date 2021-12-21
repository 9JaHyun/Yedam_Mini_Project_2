package share.init;

import cinema.application.CinemaService;
import cinema.application.SeatRowService;
import cinema.application.TheaterService;
import cinema.domain.SeatRow;
import cinema.domain.Theater;
import cinema.repository.CinemaRepository;
import cinema.repository.SeatRowRepository;
import cinema.repository.TheaterRepository;

import java.util.List;

public class SeatRowInit {
    TheaterService theaterService = new TheaterService(new TheaterRepository(), new CinemaRepository());
    SeatRowService seatRowService = new SeatRowService(new SeatRowRepository());

    public void init() {
        Theater theater = theaterService.findTheater("서울", "강동", "1관");
        Theater theater1 = theaterService.findTheater("서울", "강동", "2관");
        Theater theater2 = theaterService.findTheater("서울", "강동", "3관");
        Theater theater3 = theaterService.findTheater("서울", "강동", "4관");
        Theater theater4 = theaterService.findTheater("서울", "강남", "1관");
        Theater theater5 = theaterService.findTheater("서울", "강남", "2관");
        Theater theater6 = theaterService.findTheater("서울", "강남", "3관");
        Theater theater7 = theaterService.findTheater("서울", "강남", "4관");
        Theater theater8 = theaterService.findTheater("대구", "대구이시아", "1관");
        Theater theater9 = theaterService.findTheater("대구", "대구이시아", "2관");
        Theater theater10 = theaterService.findTheater("대구", "대구이시아", "3관");
        Theater theater11 = theaterService.findTheater("대구", "대구이시아", "4관");
        Theater theater12 = theaterService.findTheater("대구", "대구신세계(동대구)", "1관");
        Theater theater13 = theaterService.findTheater("대구", "대구신세계(동대구)", "2관");
        Theater theater14 = theaterService.findTheater("대구", "대구신세계(동대구)", "3관");
        Theater theater15 = theaterService.findTheater("대구", "대구신세계(동대구)", "4관");
        List<SeatRow> seatRows = List.of(
                new SeatRow("A", theater),
                new SeatRow("B", theater),
                new SeatRow("C", theater),
                new SeatRow("D", theater),
                new SeatRow("E", theater),
                new SeatRow("F", theater),
                new SeatRow("G", theater),
                new SeatRow("H", theater),
                new SeatRow("I", theater),
                new SeatRow("A", theater1),
                new SeatRow("B", theater1),
                new SeatRow("C", theater1),
                new SeatRow("D", theater1),
                new SeatRow("E", theater1),
                new SeatRow("F", theater1),
                new SeatRow("G", theater1),
                new SeatRow("H", theater1),
                new SeatRow("I", theater1),
                new SeatRow("A", theater2),
                new SeatRow("B", theater2),
                new SeatRow("C", theater2),
                new SeatRow("D", theater2),
                new SeatRow("E", theater2),
                new SeatRow("F", theater2),
                new SeatRow("G", theater2),
                new SeatRow("H", theater2),
                new SeatRow("I", theater2),
                new SeatRow("A", theater3),
                new SeatRow("B", theater3),
                new SeatRow("C", theater3),
                new SeatRow("D", theater3),
                new SeatRow("E", theater3),
                new SeatRow("F", theater3),
                new SeatRow("G", theater3),
                new SeatRow("H", theater3),
                new SeatRow("I", theater3),
                new SeatRow("A", theater4),
                new SeatRow("B", theater4),
                new SeatRow("C", theater4),
                new SeatRow("D", theater4),
                new SeatRow("E", theater4),
                new SeatRow("F", theater4),
                new SeatRow("G", theater4),
                new SeatRow("H", theater4),
                new SeatRow("I", theater4),
                new SeatRow("A", theater5),
                new SeatRow("B", theater5),
                new SeatRow("C", theater5),
                new SeatRow("D", theater5),
                new SeatRow("E", theater5),
                new SeatRow("F", theater5),
                new SeatRow("G", theater5),
                new SeatRow("H", theater5),
                new SeatRow("I", theater5),
                new SeatRow("A", theater6),
                new SeatRow("B", theater6),
                new SeatRow("C", theater6),
                new SeatRow("D", theater6),
                new SeatRow("E", theater6),
                new SeatRow("F", theater6),
                new SeatRow("G", theater6),
                new SeatRow("H", theater6),
                new SeatRow("I", theater6),
                new SeatRow("A", theater7),
                new SeatRow("B", theater7),
                new SeatRow("C", theater7),
                new SeatRow("D", theater7),
                new SeatRow("E", theater7),
                new SeatRow("F", theater7),
                new SeatRow("G", theater7),
                new SeatRow("H", theater7),
                new SeatRow("I", theater7),
                new SeatRow("A", theater8),
                new SeatRow("B", theater8),
                new SeatRow("C", theater8),
                new SeatRow("D", theater8),
                new SeatRow("E", theater8),
                new SeatRow("F", theater8),
                new SeatRow("G", theater8),
                new SeatRow("H", theater8),
                new SeatRow("I", theater8),
                new SeatRow("A", theater9),
                new SeatRow("B", theater9),
                new SeatRow("C", theater9),
                new SeatRow("D", theater9),
                new SeatRow("E", theater9),
                new SeatRow("F", theater9),
                new SeatRow("G", theater9),
                new SeatRow("H", theater9),
                new SeatRow("I", theater9),
                new SeatRow("A", theater10),
                new SeatRow("B", theater10),
                new SeatRow("C", theater10),
                new SeatRow("D", theater10),
                new SeatRow("E", theater10),
                new SeatRow("F", theater10),
                new SeatRow("G", theater10),
                new SeatRow("H", theater10),
                new SeatRow("I", theater10));

        for (SeatRow seatRow : seatRows) {
            seatRowService.save(seatRow);
        }
    }
}
