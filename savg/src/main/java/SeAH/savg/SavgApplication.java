package SeAH.savg;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@EnableBatchProcessing //배치기능 활성화
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SavgApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavgApplication.class, args);
	}

}
