package SeAH.savg.controller;


import SeAH.savg.entity.Attendance;
import SeAH.savg.repository.AttendanceRepository;
import SeAH.savg.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceRepository attendanceRepository;


  ////사용자 관련(1~2)
    //1. (사용자) 출석 등록 페이지보기
    @GetMapping("/register/{eduId}")
    public String showUserEduAtten() {
        return "page/attendance";
    }


    //2. (사용자) 출석 등록하기
    @PostMapping("/register/{eduId}")
    public ResponseEntity<?> registerAttendEdu(@RequestBody Map<String, Object> requestData) {
        String attenDepartment = (String) requestData.get("attenDepartment");
        String attenName = (String) requestData.get("attenName");
        String attenEmployeeNumber = (String) requestData.get("attenEmployeeNumber");
        String eduId = (String) requestData.get("eduId"); //(조건) front에서 eduId를 보내줘야한다

        attendanceService.attendEdu(attenDepartment, attenName, attenEmployeeNumber, eduId);

        return ResponseEntity.ok().build();
    }



  ////관리자 관련
    //해당 교육일지에 따른 학생 출석 리스트 조회
  @GetMapping("/list/{eduId}")
  public ResponseEntity<?> showUserEduAttenList(@PathVariable("eduId") String eduId) {
      List<Attendance> result = attendanceRepository.findAllByEduId(eduId);
      return new ResponseEntity<>(result, HttpStatus.OK);
  }

}

