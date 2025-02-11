package Users_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"config", "controller", "model", "repository", "security", "service", "Users_app"})
public class UsersAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersAppApplication.class, args);
	}

}
