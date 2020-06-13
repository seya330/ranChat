/**
 * develop
 * */
package com.seya330.ranchat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RanchatApplication {
	public static void main(String[] args) {
		SpringApplication.run(RanchatApplication.class, args);
	}
} 




/**
 * operation
 * */
/*
package com.seya330.ranchat;
  
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
  
@SpringBootApplication
public class RanchatApplication extends SpringBootServletInitializer{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(RanchatApplication.class);
	}

	public static void main(String[] args) {
	SpringApplication.run(RanchatApplication.class, args); }
}
 */