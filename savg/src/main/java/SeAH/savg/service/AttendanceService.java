package SeAH.savg.service;

import SeAH.savg.dto.AttendanceDTO;
import SeAH.savg.dto.EduDTO;
import SeAH.savg.entity.Attendance;
import SeAH.savg.entity.Edu;
import SeAH.savg.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public static LocalDateTime nowFromZone(){
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }

    //(교육생) 출석등록하기
    @Transactional

    public void attendEdu(String attendEmployeeDepartment,
                          String attendEmployeeName,
                          String attendEmployeeNumber,
                          String eduId){

        LocalDateTime attenTime = nowFromZone();
        System.out.println("nowFromZone:    "+ attenTime);

        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setAttenDepartment(attendEmployeeDepartment);
        attendanceDTO.setAttenName(attendEmployeeName);
        attendanceDTO.setAttenEmployeeNumber(attendEmployeeNumber);
        attendanceDTO.setEduId(eduId);
        attendanceDTO.setAttenTime(attenTime);

        Attendance attendance = attendanceDTO.toEntity();
        System.out.println("저장중인 attendance:    "+ attendance);
        attendanceRepository.save(attendance);
    }



    //출석 삭제
    public void deleteAtten(Long attenId) {
        attendanceRepository.deleteById(attenId);
    }

}
