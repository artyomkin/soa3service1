import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@ComponentScan({"controllers", "dto", "entities", "serviceBeans", "config"})
public class Soa3Application {
	public static void main(String[] args) {
		SpringApplication.run(Soa3Application.class, args);
	}

}
