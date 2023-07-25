package SeAH.savg.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
    public boolean authenticate(String username, String password) {
        return "admin".equals(username) && "seah1234".equals(password);
    }

}
