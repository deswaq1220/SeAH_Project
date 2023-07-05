package SeAH.savg;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HelloWorldController {
    @GetMapping("/api/hello")
    public String test() {
        return "Hello, world!";
    }
}
