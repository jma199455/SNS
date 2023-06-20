package com.fastcampus.sns.service;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.fixture.UserEntityFixture;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.WeakHashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;


    @Test
    void 회원가입이_정상적으로_동작하는_경우() {

        String userName = "userName";
        String password = "password";

        // mocking
        //when(userEntityRepository.findByUserName(userName)).the
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password ");
        when(userEntityRepository.save(any())).thenReturn((UserEntityFixture.get(userName,password))); // 저장된 Entity 반환

        Assertions.assertDoesNotThrow(() -> userService.join(userName, password)); // exception이 발생하지 않도록

    }


    @Test
    void 회원가입시_userName으로_회원가입한_유저가_이미_있는경우() {

        String userName = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(userName,password);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.encode(password)).thenReturn("encrypt_password ");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture)); // 저장된 Entity 반환  , mocking된 user enttiy를 가지고 비교하도록 구현해도됨

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(userName, password)); // exception이 발생하지 않도록
        Assertions.assertEquals(ErrorCode.DUPLICATED_USER_NAME,e.getErrorCode());

    }


    @Test
    void 로그인이_정상적으로_동작하는_경우() {

        String userName = "userName";
        String password = "password";

        // 가짜 엔티티 생성
        UserEntity fixture = UserEntityFixture.get(userName,password);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.matches(password,fixture.getPassword())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> userService.login(userName, password)); // exception이 발생하지 않도록
    }


    @Test
    void 로그인시_userName으로_회원가입한_유저가_없는_경우() {

        String userName = "userName";
        String password = "password";

        // 가짜 엔티티 생성
        UserEntity fixture = UserEntityFixture.get(userName,password);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName,password)); // exception이 발생하도록 (확인)
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND,e.getErrorCode());

    }

    @Test
    void 로그인시_패스워드가_틀린_경우() {

        String userName = "userName";
        String password = "password";
        String wrongPassword = "wrongPassword";

        // 가짜 엔티티 생성
        UserEntity fixture = UserEntityFixture.get(userName,password);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName,wrongPassword)); // exception이 발생하도록 (확인)
        Assertions.assertEquals(ErrorCode.INVALID_PASSWORD,e.getErrorCode());
    }





}
