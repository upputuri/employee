package com.supcode.demo.app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ComponentScan("com.supcode.demo")
@RestController
@Slf4j
public class EmployeeApplication{

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

	@GetMapping(value="/health")
	public String getAppHealth(){
		return "good";
	}
}
