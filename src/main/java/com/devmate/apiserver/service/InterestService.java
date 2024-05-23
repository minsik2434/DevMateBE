package com.devmate.apiserver.service;

import com.devmate.apiserver.domain.Interest;
import com.devmate.apiserver.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterestService {
    private final InterestRepository interestRepository;

    public List<Interest> findInterests(){
        return interestRepository.findAll();
    }

}
