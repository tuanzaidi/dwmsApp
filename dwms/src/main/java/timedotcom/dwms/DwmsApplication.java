package timedotcom.dwms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "timedotcom.dwms.model")
public class DwmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DwmsApplication.class, args);
	}

}
