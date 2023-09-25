package SeAH.savg.controller;

import SeAH.savg.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/admin/confirm")
    public ResponseEntity<?> checkAdmin(@RequestBody Map<String, String> requestData){

        String loginId = requestData.get("loginId");
        String loginPw = requestData.get("loginPw");

        List<String> confirm = loginService.checkAdmin(loginId, loginPw);

        return new ResponseEntity<>(confirm, HttpStatus.OK);
    }




}