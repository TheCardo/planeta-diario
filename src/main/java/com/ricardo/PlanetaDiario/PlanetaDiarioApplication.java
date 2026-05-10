package com.ricardo.PlanetaDiario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
        ManagementWebSecurityAutoConfiguration.class
})
public class PlanetaDiarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanetaDiarioApplication.class, args);
	}

}
