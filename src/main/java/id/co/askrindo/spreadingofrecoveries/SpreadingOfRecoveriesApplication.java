package id.co.askrindo.spreadingofrecoveries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class SpreadingOfRecoveriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpreadingOfRecoveriesApplication.class, args);
	}

}