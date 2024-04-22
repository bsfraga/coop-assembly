package br.com.cooperativeassembly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CooperativeassemblyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CooperativeassemblyApplication.class, args);
	}

}
