package ede.desafiogat;

import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.Stack;

@SpringBootApplication
@EnableScheduling
public class DesafiogatApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DesafiogatApplication.class, args);
	}

	@Bean
	public JSONParser jsonParser(){return new JSONParser();}

}
