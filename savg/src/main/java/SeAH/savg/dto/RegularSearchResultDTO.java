package SeAH.savg.dto;

import SeAH.savg.constant.RegStatus;
import SeAH.savg.entity.RegularInspection;
import SeAH.savg.entity.RegularInspectionCheck;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class RegularSearchResultDTO {

    private String regularPart;                 // 점검구역(영역)
    private String regularInsName;              // 점검항목
    private LocalDateTime regularDate;          // 관찰일
    private String regularEmpNum;               // 관찰자 사원번호
    private String regularPerson;               // 관찰자
    private int regularInsCount;                // 점검 건수
    private RegStatus regularComplete;             // 모두 조치완료여부
    private String regularId;             // 정기점검id


    public RegularSearchResultDTO mapping(RegularInspection entity1) {
        RegularSearchResultDTO dto = new RegularSearchResultDTO();
        dto.setRegularPart(entity1.getRegularPart());
        dto.setRegularInsName(entity1.getRegularInsName());
        dto.setRegularDate(entity1.getRegTime());
        dto.setRegularPerson(entity1.getRegularPerson());
        dto.setRegularComplete(entity1.getRegularComplete());
        return dto;
    }
}
