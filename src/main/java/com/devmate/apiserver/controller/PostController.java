package com.devmate.apiserver.controller;

import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import com.devmate.apiserver.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final ControllerUtil controllerUtil;
    private final PostService postService;
    @PostMapping("/{category}")
    public ResponseEntity<String> postRegister(@PathVariable("category") String category,
                                               Authentication authentication,
                                               @RequestBody @Validated PostRegisterDto postRegisterDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long l = postService.postSave(loginId,category, postRegisterDto);
        return ResponseEntity.ok("test");
    }
}
