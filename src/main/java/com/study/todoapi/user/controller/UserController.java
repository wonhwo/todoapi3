package com.study.todoapi.user.controller;

import com.study.todoapi.exception.DuplicatedEmailException;
import com.study.todoapi.exception.NoRegisteredArgumentsException;
import com.study.todoapi.user.dto.request.UserSignUpRequestDTO;
import com.study.todoapi.user.dto.response.UserSignUpResponse;
import com.study.todoapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.DigestException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> signUp(@Validated @RequestBody UserSignUpRequestDTO dto, BindingResult bindingResult){
        log.info("/api/auth");
        if(bindingResult.hasErrors()){
            return ResponseEntity
                    .badRequest()
                    .body(bindingResult.getFieldError());
        }
        try {
            UserSignUpResponse userSignUpResponse = userService.create(dto);
            return ResponseEntity.ok().body(userSignUpResponse);
        }catch (NoRegisteredArgumentsException e){
            log.warn("필수 가입 정보를 전달 받지 못했습니다.!");
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (DuplicatedEmailException a){
            log.warn("이메일 중복.!");
            return ResponseEntity.badRequest().body(a.getMessage());
        }
    }

    @GetMapping("/check")
    public ResponseEntity<?> check(String email){
        boolean duplicateEmail = userService.isDuplicateEmail(email);
        return ResponseEntity.ok().body(duplicateEmail);
    }
}
