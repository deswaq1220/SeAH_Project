package SeAH.savg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpecialController {

    // 조회
    @GetMapping(value = "/special")
    public String specialForm(Model model){
        return "page/special";
    }


    // 저장

}
