package com.example.demo;

import com.example.demo.dbTest.TestContainersConfig;
import org.springframework.boot.SpringApplication;

public class TestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(DemoApplication::main).with(TestContainersConfig.class).run(args);
	}

}
