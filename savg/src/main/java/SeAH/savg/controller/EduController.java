package SeAH.savg.controller;

import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.EduFormDTO;
import SeAH.savg.service.EduService;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class EduController {

    private final EduService eduService;

    public EduController(EduService eduService) {
        this.eduService = eduService;
    }

    //교육 등록
    @PostMapping(value = "/edu/reg")
    public String EduWrite(@Valid EduDTO eduDTO, Model model) throws Exception {

        Long eduId = eduService.createEdu(eduDTO);
        model.addAttribute("eduFormDTO", new EduFormDTO());
        return "page/eduReg";
    }

    @GetMapping("/edu/reg")
    public String showEduForm(Model model) {
        model.addAttribute("eduFormDTO", new EduFormDTO());
        return "page/edureg";
    }
}
