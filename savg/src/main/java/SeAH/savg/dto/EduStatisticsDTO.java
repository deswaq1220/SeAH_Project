package SeAH.savg.dto;

import SeAH.savg.constant.edustate;
import SeAH.savg.entity.Edu;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class EduStatisticsDTO {

    private edustate eduCategory;
    private LocalDateTime eduStartTime;
    private String eduSumTime;
    private String attenName;
    private String attenEmployeeNumber;
    private String attenDepartment;

    private static ModelMapper modelMapper = new ModelMapper();

    public static EduStatisticsDTO of(Edu edu){
        return modelMapper.map(edu, EduStatisticsDTO.class);
    }

    public Edu toEntity(){return modelMapper.map(this, Edu.class);}

}
