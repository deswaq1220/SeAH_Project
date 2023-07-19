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
    public void attendEdu(String attendEmployeeDepartment
                                      , String attendEmployeeName
                                      , String attendEmployeeNumber){

        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setAttenDepartment(attendEmployeeDepartment);
        attendanceDTO.setAttenName(attendEmployeeName);
        attendanceDTO.setAttenEmployeeNumber(attendEmployeeNumber);

        Attendance attendance = attendanceDTO.toEntity();
        System.out.println(attendance);
        attendanceRepository.save(attendance);
    }

}
