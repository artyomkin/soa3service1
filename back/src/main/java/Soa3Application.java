import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Soa3Application {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Soa3Application.class);
		application.setAdditionalProfiles("ssl");
		application.run(args);
	}

}
