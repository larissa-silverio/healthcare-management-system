package healthcare.management.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;  // ← ADICIONE

@SpringBootApplication
@ComponentScan(basePackages = {"com.sghss", "healthcare.management.demo"})  // ← ADICIONE
public class DemoheathcareApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoheathcareApplication.class, args);
	}

}
