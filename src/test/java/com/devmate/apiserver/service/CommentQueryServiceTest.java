package com.devmate.apiserver.service;

import com.devmate.apiserver.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentQueryServiceTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentQueryService commentQueryService;

    @Test
    @DisplayName("댓글 조회시 예외")
    void getCommentExceptionTest(){
        given(commentRepository.findCommentAndMemberById(1L)).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> commentQueryService.getCommentDto(1L));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class).hasMessageContaining("Not Found Comment");
    }
}