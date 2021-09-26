package com.kk08.CyTicketServer;

import com.kk08.CyTicketServer.helpers.IdHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URISyntaxException;

@SpringBootApplication
public class CyTicketServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CyTicketServerApplication.class, args);

		try {
			IdHelper.loadIds();
		} catch (URISyntaxException exception) {
			System.out.println(exception.toString());
		}
	}

}
