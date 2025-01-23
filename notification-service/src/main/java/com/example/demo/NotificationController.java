package com.example.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.UserEntity;

@Service
public class NotificationController {

	@KafkaListener(topics = "user-registration", groupId = "1")
	public String userCreatedSuccessfully(UserEntity user) {
		System.out.println(user.toString());
		return "User Created Successfully!" + user.toString();
	}
	
	
}
