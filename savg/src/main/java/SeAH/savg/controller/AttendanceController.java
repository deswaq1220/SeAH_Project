package SeAH.savg.controller;

import SeAH.savg.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usereduatten")
//@CrossOrigin(origins = "http://172.20.10.5:3000")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {

    private final AttendanceService attendanceService;

    //페이지 뷰
    @GetMapping("")
    public String showUserEduAtten() {

        return "page/attendance";
    }

    //교육생 출석등록하기
    @PostMapping("/register")
    public ResponseEntity<?> registerAttendEdu(@RequestBody Map<String, Object> requestData) {
        String attenDepartment = (String) requestData.get("attenDepartment");
        String attenName = (String) requestData.get("attenName");
        String attenEmployeeNumber = (String) requestData.get("attenEmployeeNumber");
        Long eduId = (Long) requestData.get("eduId");
        attendanceService.attendEdu(attenDepartment, attenName, attenEmployeeNumber, eduId);

        return ResponseEntity.ok().build();
    }

    }