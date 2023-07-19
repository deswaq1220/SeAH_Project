package SeAH.savg.controller;

import SeAH.savg.dto.EmailFormDTO;
import SeAH.savg.dto.MasterDataFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.MasterData;
import SeAH.savg.repository.EmailRepository;
import SeAH.savg.repository.MasterDataRepository;
import SeAH.savg.service.EmailService;
import SeAH.savg.service.MasterDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MasterDataController {
    private final MasterDataService masterDataService;
    private final MasterDataRepository masterDataRepository;
    private final EmailRepository emailRepository;
    private final EmailService emailService;

    // 설비목록
    @GetMapping(value = "/master")
    public String masterForm(Model model){
        List<MasterData> masterDataList = masterDataRepository.findAll();
        model.addAttribute("masterDataList", masterDataList);
        return "page/master";
    }

    // 설비등록
    @PostMapping(value = "/master")
    public String masterNew(@Valid MasterDataFormDTO masterDataFormDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())
            return "page/master";

        try{
            masterDataService.saveMaster(masterDataFormDTO);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/master";
    }

    // 이메일목록
    @GetMapping(value = "/master/email")
    public String emailForm(Model model){
        List<Email> emailList = emailRepository.findAll();
        model.addAttribute("emailList", emailList);
        return "page/master";
    }

    // 이메일등록
    @PostMapping(value = "/master/email")
    public String emailNew(@Valid EmailFormDTO emailFormDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "page/master";
        try{
            emailService.saveEmail(emailFormDTO);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/master";
    }
}
