package tn.esprit.gestiondustock1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GestionDuStock1Application {

	public static void main(String[] args) {
		SpringApplication.run(GestionDuStock1Application.class, args);
	}

}
