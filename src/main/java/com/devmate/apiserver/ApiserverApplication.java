package com.devmate.apiserver;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiserverApplication {
	@Autowired MemberRepository memberRepository;
	public static void main(String[] args) {
		SpringApplication.run(ApiserverApplication.class, args);
	}

	@PostConstruct
	public void initData(){
		Member member = new Member("testId","testPassword","choiminsik",
				"minsik",true,"testUrl");
		memberRepository.save(member);
	}
}
