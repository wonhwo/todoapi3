package com.study.todoapi.user.repository;

import com.study.todoapi.user.dto.request.UserSignUpRequestDTO;
import com.study.todoapi.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(false)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Test
    @DisplayName("회원가입 테스트")
    void saveTest() {
        //given
        User user=User.builder()
                .email("asdf@1234")
                .password("1234")
                .userName("말포이").build();
        //when
        User save = userRepository.save(user);

        //then
        assertNotNull(save);
    }
    @Test
    @DisplayName("이메일로 회원 조회하기")
    void findByEmailTest() {
        //given
        String  email = "asdf@1234";
        //when
        Optional<User> userOptional=userRepository.findByEmail(email);
        //then
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        assertEquals("말포이",user.getUserName());
        System.out.println("-----------------------");
        System.out.println("user = " + user);
        System.out.println("-----------------------");
    }
    @Test
    @DisplayName("이메일 중복 체크")
    void emailDuplicateTest() {
        //given
        String  email = "asdf@1234";
        //when
        boolean b = userRepository.existsByEmail(email);
        //then
        assertTrue(b);
    }
}