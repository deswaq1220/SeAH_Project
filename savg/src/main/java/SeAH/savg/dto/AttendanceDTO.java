package SeAH.savg.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class AttendanceDTO {

    private Long atten_id;           // id
    private String employee_number;       // 사원번호
    private String name;                  // 이름
    private String department;            // 부서

/*    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 동일 필드명 맵핑

    //Attendance 엔티티를 AttendanceDTO로 변환하는 메소드
    public static AttendanceDTO of(Attendance attendance){
        return modelMapper.map
    }
*/
}
