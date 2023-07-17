package SeAH.savg.service;

import SeAH.savg.dto.AttendanceDTO;
import SeAH.savg.entity.Attendance;
import SeAH.savg.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    //교육생 출석등록하기
    //@Transactional
    public void attendEdu(String attend_employee_department
                                      , String attend_employee_name
                                      , String attend_employee_number){

        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setDepartment(attend_employee_department);
        attendanceDTO.setName(attend_employee_name);
        attendanceDTO.setEmployee_number(attend_employee_number);

        Attendance attendance = attendanceDTO.toEntity(attendanceDTO);
        attendanceRepository.save(attendance);
    }

}
