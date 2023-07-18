package SeAH.savg.controller;

import SeAH.savg.dto.MasterDataFormDTO;
import SeAH.savg.entity.MasterData;
import SeAH.savg.repository.MasterDataRepository;
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

}
