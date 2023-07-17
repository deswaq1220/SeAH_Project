package SeAH.savg.controller;

import SeAH.savg.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AttendController {

   // private final AttendanceService attendService;

    //교육생 출석등록하기
    /*@PostMapping("/usereduatten")
    public String registerAttendEdu(Model model
                                   ,String attend_employee_department
                                   ,String attend_employee_name
                                   ,String attend_employee_number){

        attendService.attendEdu(attend_employee_department, attend_employee_name, attend_employee_number);

        return "/usereduatten";
    }
   */
}
