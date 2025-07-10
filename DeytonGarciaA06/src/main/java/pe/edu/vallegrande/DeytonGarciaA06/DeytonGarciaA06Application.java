package pe.edu.vallegrande.DeytonGarciaA06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DeytonGarciaA06Application {

	public static void main(String[] args) {
		SpringApplication.run(DeytonGarciaA06Application.class, args);
	}

}
