package SeAH.savg.dto;

import SeAH.savg.constant.SpeStatus;
import SeAH.savg.entity.Attendance;
import SeAH.savg.entity.SpecialInspection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SpeInsStatisticsDTO {
    private String speId;                     // id
    private LocalDateTime speDate;          // 점검일

    private String spePart;                 // 점검영역
    //private String speFacility;             // 점검설비

    private String speDanger;               // 위험분류
    private String speInjure;               // 부상부위
    private String speCause;                // 위험원인
    private String speTrap;                 // 실수함정
    private SpeStatus speRiskAssess;        // 위험성평가

    private SpeStatus speComplete;         // 완료여부 : 기본값 NO


    public static ModelMapper modelMapper = new ModelMapper();

    public SpecialInspection toEntity(){ return modelMapper.map(this, SpecialInspection.class); }

}
