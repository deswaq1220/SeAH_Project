package SeAH.savg.controller;

import SeAH.savg.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usereduatten")
//@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {

    private final AttendanceService attendanceService;

    //페이지 뷰
    @GetMapping("")
    public String showUserEduAtten() {

        return "page/attendance";
    }

    //교육생 출석등록하기
    @PostMapping("/register")
    public String registerAttendEdu(Model model
                                   ,@RequestParam("attenDepartment") String attenDepartment
                                   ,@RequestParam("attenName") String attenName
                                   ,@RequestParam("attenEmployeeNumber") String attenEmployeeNumber) {

        attendanceService.attendEdu(attenDepartment, attenName, attenEmployeeNumber);

        return "page/attendance";
    }

}
