package SeAH.savg.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Log4j2
public class LoginController {

    @PostMapping("/Main")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password) {

        if ("admin".equals(username) && "seah1234".equals(password)) {
            return "success"; // 로그인 성공
        } else {
            return "failure"; // 로그인 실패
        }
    }
}
