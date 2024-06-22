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
	@Autowired MemberRepository memberRepository;
	@Autowired
	InterestRepository interestRepository;
	@Autowired
	PostService postService;
	public static void main(String[] args) {
		SpringApplication.run(ApiserverApplication.class, args);
	}

	@PostConstruct
	public void initData(){
		Member member1 = new Member("testId","testPassword","choiminsik",
				"minsik",true,"testUrl");
		Member member2 = new Member("testId2","testPassword","minsik",
				"minsiiiki",true,"testUrl");
		memberRepository.save(member1);
		memberRepository.save(member2);

		Interest interest1 = new Interest("AI");
		Interest interest2 = new Interest("FrontEnd");
		Interest interest3 = new Interest("BackEnd");
		List<Interest> list = new ArrayList<>();
		list.add(interest1);
		list.add(interest2);
		list.add(interest3);
		interestRepository.saveAll(list);
	}
}
