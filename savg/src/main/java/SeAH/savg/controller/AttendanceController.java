package SeAH.savg.controller;

import SeAH.savg.entity.Attendance;
import SeAH.savg.repository.AttendanceRepository;
import SeAH.savg.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usereduatten")
//@CrossOrigin(origins = "http://172.20.10.5:3000")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceRepository attendanceRepository;


  ////사용자 관련(1~2)
    //1. (사용자) 출석 등록 페이지보기
    @GetMapping("")
    public String showUserEduAtten() {
        return "page/attendance";
    }

    //2. (사용자) 출석 등록하기
    @PostMapping("/register")
    public ResponseEntity<?> registerAttendEdu(@RequestBody Map<String, Object> requestData) {
        String attenDepartment = (String) requestData.get("attenDepartment");
        String attenName = (String) requestData.get("attenName");
        String attenEmployeeNumber = (String) requestData.get("attenEmployeeNumber");
        Long eduId = (Long) requestData.get("eduId"); //(조건) front에서 eduId를 보내줘야한다
        attendanceService.attendEdu(attenDepartment, attenName, attenEmployeeNumber, eduId);

        return ResponseEntity.ok().build();
    }


  ////관리자 관련
    //해당 교육일지에 따른 학생 출석 리스트 조회
    @PostMapping("") //(임시, 출석리스트 페이지 만들어지면 변경필요 ★)
    public String showUserEduAttenList(@RequestBody Map<String, Long> requestData) {
        Long eduId = requestData.get("eduId");
        attendanceRepository.findAllByEduId(eduId);

        return "page/attendance2"; //(임시, 출석리스트 페이지 만들어지면 변경필요 ★)
    }



    }