package com.devmate.apiserver.service.post.factory;

import com.devmate.apiserver.dto.post.request.RequestDto;
import com.devmate.apiserver.service.post.DtoFactory;

import java.util.NoSuchElementException;

public class FactoryProvider {
    public static <T extends RequestDto> DtoFactory<T> getFactory(String category) {
        switch (category.toLowerCase()) {
            case "qna":
                return (DtoFactory<T>) new QnaPostFactory();
            case "community":
                return (DtoFactory<T>) new CommunityPostFactory();
            case "job":
                return (DtoFactory<T>) new JobOpeningPostFactory();
            case "review":
                return (DtoFactory<T>) new ReviewPostFactory();
            case "study":
                return (DtoFactory<T>) new StudyPostFactory();
            case "mentoring":
                return (DtoFactory<T>) new MentoringPostFactory();
            default:
                throw new NoSuchElementException("Not Found Category: " + category);
        }
    }
}
