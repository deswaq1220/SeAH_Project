package SeAH.savg.service;

import SeAH.savg.entity.Login;
import SeAH.savg.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;


    public List<String> checkAdmin(String loginId, String loginPw) {
        Login findAdmin = loginRepository.findByLoginId(loginId);

        List<String> confirm = new ArrayList<>();


        if(findAdmin == null){
            return null;
        }else if(findAdmin.getLoginPw().equals(loginPw) && findAdmin.getAdminYN().equals("Y")){
            confirm.add("approval");
            confirm.add("admin");
            return confirm;
        }else if(findAdmin.getLoginPw().equals(loginPw) && findAdmin.getAdminYN().equals("N")){
            confirm.add("approval");
            confirm.add("guest");
            return confirm;
        }
        return null;
    }

}