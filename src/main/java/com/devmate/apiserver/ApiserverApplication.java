package com.devmate.apiserver;

import com.devmate.apiserver.domain.Interest;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.repository.InterestRepository;
import com.devmate.apiserver.repository.MemberRepository;
import com.devmate.apiserver.service.post.PostService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ApiserverApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiserverApplication.class, args);
	}
}
