package SeAH.savg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityException.class)
public class SavgApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavgApplication.class, args);
	}

}
