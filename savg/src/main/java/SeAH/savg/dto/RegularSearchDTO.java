package SeAH.savg.dto;

import SeAH.savg.constant.RegStatus;
import SeAH.savg.entity.RegularInspection;
import SeAH.savg.entity.RegularInspectionBad;
import SeAH.savg.entity.RegularInspectionCheck;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class RegularSearchDTO {

    private String regularPart;                 // 점검구역(영역)
    private String regularInsName;              // 점검항목
    private LocalDateTime regularDate;          // 관찰일
    private LocalDateTime regularStartTime;          // 검색시작구간
    private LocalDateTime regularEndTime;          // 검색완료구간
    private String regularEmpNum;               // 관찰자 사원번호
    private String regularPerson;               // 관찰자
    private RegStatus regularCheck;           // 점검상태: 위험성 확인결과 양호=GOOD, 불량=BAD, NA=NA


    public RegularSearchDTO mapping(RegularInspection entity1, RegularInspectionCheck entity2) {
        RegularSearchDTO dto = new RegularSearchDTO();
        dto.setRegularPart(entity1.getRegularPart());
        dto.setRegularInsName(entity1.getRegularInsName());
        dto.setRegularDate(entity1.getRegularDate());
        dto.setRegularPerson(entity1.getRegularPerson());
        dto.setRegularCheck(entity2.getRegularCheck());
        return dto;
    }

    public LocalDateTime getRegularStartTime() {
        return regularStartTime;
    }

    public void setRegularStartTime(LocalDateTime regularStartTime) {
        this.regularStartTime = regularStartTime;
    }

    public LocalDateTime getRegularEndTime() {
        return regularEndTime;
    }

    public void setRegularEndTime(LocalDateTime regularEndTime) {
        this.regularEndTime = regularEndTime;
    }
}
