import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"controllers", "dto", "entities", "serviceBeans", "config"})
public class Soa3Application {
	public static void main(String[] args) {
		SpringApplication.run(Soa3Application.class, args);
	}

}
