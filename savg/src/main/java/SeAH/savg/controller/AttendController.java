package SeAH.savg.controller;

import SeAH.savg.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AttendController {

    private final AttendanceService attendanceService;

    //교육생 출석등록하기
    @PostMapping("/usereduatten")
    public String registerAttendEdu(Model model
                                   ,@RequestParam("employee_department") String attend_employee_department
                                   ,@RequestParam("employee_name") String attend_employee_name
                                   ,@RequestParam("employee_number") String attend_employee_number) throws Exception {

        attendanceService.attendEdu(attend_employee_department, attend_employee_name, attend_employee_number);

        //model.addAttribute("employee_department", attend_employee_department);
        //model.addAttribute("employee_name", attend_employee_name);
        //model.addAttribute("employee_number", attend_employee_number);

        return "/usereduatten";
    }

}
