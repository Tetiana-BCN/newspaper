package com.newspaper.newspaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewspaperApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewspaperApplication.class, args);

	}
}
/*
 * package com.newspaper.newspaper;
 * 
 * import java.util.Properties;
 * 
 * import org.springframework.boot.SpringApplication;
 * import org.springframework.boot.autoconfigure.SpringBootApplication;
 * 
 * import io.github.cdimascio.dotenv.Dotenv;
 * 
 * @SpringBootApplication
 * public class NewspaperApplication {
 * 
 * public static void main(String[] args) {
 * // Cargar .env
 * Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
 * Properties props = new Properties();
 * dotenv.entries().forEach(entry -> props.setProperty(entry.getKey(),
 * entry.getValue()));
 * 
 * SpringApplication app = new SpringApplication(NewspaperApplication.class);
 * app.setDefaultProperties(props);
 * app.run(args);
 * }
 * }
 */