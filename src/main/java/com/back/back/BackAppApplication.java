package com.back.back;

import com.back.back.controller.Cardcontroller;
import com.back.back.entities.Card;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages="com.back.back")
@ComponentScan(basePackageClasses=Cardcontroller.class)
@ComponentScan(basePackageClasses= Card.class)

public class BackAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackAppApplication.class, args);
	}

}
