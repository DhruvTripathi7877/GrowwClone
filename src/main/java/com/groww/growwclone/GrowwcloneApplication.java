package com.groww.growwclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.groww.growwclone")
public class GrowwcloneApplication {
	public static void main(String[] args) {
		SpringApplication.run(GrowwcloneApplication.class, args);
	}
}