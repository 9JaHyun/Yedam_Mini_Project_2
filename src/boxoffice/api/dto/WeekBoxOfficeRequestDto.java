package boxoffice.api.dto;

import java.util.LinkedHashMap;
import java.util.Map;


public class WeekBoxOfficeRequestDto {
    private final String key = "d796cbcb4385877a5fa12ed44aa3a2a0";
    private String targetDt;
    private String weekGb = "0";
    private String itemPerPage;
    private String multiMovieYn;
    private String repNationCd;
    private String wideAreaCd;

    public WeekBoxOfficeRequestDto(String targetDt) {
        this.targetDt = targetDt;
    }

    public Map<String, String> toLinkedHashMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("key", key);
        map.put("targetDt", targetDt);
        map.put("weekGb", weekGb);
        map.put("itemPerPage", itemPerPage);
        map.put("multiMovieYn", multiMovieYn);
        map.put("repNationCd", repNationCd);
        map.put("wideAreaCd", wideAreaCd);
        return map;
    }

    public String getKey() {
        return key;
    }
}
