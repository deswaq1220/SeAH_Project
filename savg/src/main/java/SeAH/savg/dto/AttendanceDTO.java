package SeAH.savg.dto;

import SeAH.savg.entity.Attendance;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AttendanceDTO {

    private Long attenId;                     // id
    private String attenEmployeeNumber;       // 사원번호
    private String attenName;                 // 이름
    private String attenDepartment;           // 부서
    private Long eduId;                       // 교육일지id
    private LocalDateTime attenTime;         // 출석시간

    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 동일 필드명 맵핑

    //Attendance엔티티를 AttendanceDTO로 변환하는 메소드
    public static AttendanceDTO of(Attendance attendance){
        return modelMapper.map(attendance, AttendanceDTO.class);
    }

    //AttendanceDTO를 Attendance 엔티티로 변환해주는 메소드
    public Attendance toEntity(){
        return modelMapper.map(this, Attendance.class);
    }
}
