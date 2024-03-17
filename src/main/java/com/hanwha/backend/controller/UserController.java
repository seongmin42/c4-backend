package com.hanwha.backend.controller;

import com.hanwha.backend.data.dto.request.LoginRequestDto;
import com.hanwha.backend.exception.api.IdDuplicateException;
import com.hanwha.backend.exception.api.IdNotFoundException;
import com.hanwha.backend.exception.auth.PasswordNotMatchException;
import com.hanwha.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("User Controller")
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation(value="유저 로그인", notes = "아이디, 비밀번호를 필요로 합니다." +
            "실패할 경우 에러코드 401을 반환합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Access Token을 반환합니다."),
            @ApiResponse(code = 401, message = "Login failed.")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        try{
            return new ResponseEntity<>(userService.login(loginRequestDto), HttpStatus.OK);
        } catch(IdNotFoundException | PasswordNotMatchException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @ApiOperation(value="유저 회원가입", notes="회원 정보를 DB에 등록합니다. " +
            "아이디, 비밀번호를 필요로 합니다. " +
            "아이디는 최소 6자 최대 20자, 패스워드는 최소 8자 최대 30자까지 가능합니다.")
    @PostMapping("/signup")
    @ApiResponses({
            @ApiResponse(code = 201, message = "유저 엔티티의 id를 반환합니다."),
            @ApiResponse(code = 401, message = "Signup failed.")
    })
    public ResponseEntity<?> signUp(@RequestBody LoginRequestDto loginRequestDto){
        try{
            return new ResponseEntity<>(userService.signup(loginRequestDto), HttpStatus.CREATED);
        } catch(IdDuplicateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @ApiOperation(value="회원탈퇴", notes="회원 정보를 DB에서 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<?> leave(@RequestBody LoginRequestDto loginRequestDto){
        try {
            return new ResponseEntity<>(userService.deleteUser(loginRequestDto), HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
