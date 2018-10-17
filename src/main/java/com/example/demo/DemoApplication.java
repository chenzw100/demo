package com.example.demo;

import com.example.demo.mail.MailSendUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println(MailSendUtil.getFormName()+":"+MailSendUtil.getPassword());
	}
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
