package com.modak;

import com.modak.service.NotificationServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ModakApplication {
	@Autowired
	NotificationServiceImpl notificationServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(ModakApplication.class, args);


	}


	@PostConstruct
	public void init() {
		notificationServiceImpl.send("news", "user", "news 1");
		notificationServiceImpl.send("news", "user", "news 2");
		notificationServiceImpl.send("news", "user", "news 3");
		notificationServiceImpl.send("news", "another user", "news 1");
		notificationServiceImpl.send("update", "user", "update 1");
		notificationServiceImpl.send("noType", "user", "update 1");

	}
}



