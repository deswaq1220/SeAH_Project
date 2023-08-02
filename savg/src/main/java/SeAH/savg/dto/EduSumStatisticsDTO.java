package SeAH.savg.dto;

import SeAH.savg.constant.edustate;
import SeAH.savg.entity.Edu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EduSumStatisticsDTO {

    private edustate eduCategory;
    private String eduSumTime;

    private static ModelMapper modelMapper = new ModelMapper();

    public static EduSumStatisticsDTO of(Edu edu){
        return modelMapper.map(edu, EduSumStatisticsDTO.class);
    }

    public Edu toEntity(){return modelMapper.map(this, Edu.class);}

}
