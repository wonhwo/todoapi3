package com.study.todoapi.user.service;

import com.study.todoapi.exception.DuplicatedEmailException;
import com.study.todoapi.exception.NoRegisteredArgumentsException;
import com.study.todoapi.user.dto.request.UserSignUpRequestDTO;
import com.study.todoapi.user.dto.response.UserSignUpResponse;
import com.study.todoapi.user.dto.response.UserSignUpResponse;
import com.study.todoapi.user.entity.User;
import com.study.todoapi.user.repository.UserRepository;
import com.study.todoapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 처리
    public UserSignUpResponse create(UserSignUpRequestDTO dto) {

        if (dto == null) {
            throw new NoRegisteredArgumentsException("회원가입 입력정보가 없습니다!");
        }
        String email = dto.getEmail();

        if (userRepository.existsByEmail(email)) {
            log.warn("이메일이 중복되었습니다!! - {}", email);
            throw new DuplicatedEmailException("중복된 이메일입니다!!");
        }

        User saved = userRepository.save(dto.toEntity(passwordEncoder));

        log.info("회원가입 성공!! saved user - {}", saved);

        return new UserSignUpResponse(saved); // 회원가입 정보를 클라이언트에게 리턴

    }
    // 이메일 중복확인
    public boolean isDuplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
