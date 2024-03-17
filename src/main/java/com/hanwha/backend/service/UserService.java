package com.hanwha.backend.service;

import com.hanwha.backend.data.dto.request.LoginRequestDto;
import com.hanwha.backend.data.dto.response.LoginResponseDto;
import com.hanwha.backend.data.entity.Member;
import com.hanwha.backend.exception.api.IdDuplicateException;
import com.hanwha.backend.exception.api.IdNotFoundException;
import com.hanwha.backend.exception.auth.PasswordNotMatchException;
import com.hanwha.backend.repository.MemberRepository;
import com.hanwha.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) throws IdNotFoundException, PasswordNotMatchException {
        Member member = memberRepository.findByUserId(loginRequestDto.getUserId()).orElseThrow(IdNotFoundException::new);
        if(!bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), member.getPassword()))
            throw new PasswordNotMatchException();
        return new LoginResponseDto(loginRequestDto.getUserId(), jwtUtil.generateAccessToken(member));
    }

    public Long signup(LoginRequestDto loginRequestDto) throws IdDuplicateException {
        if(memberRepository.findByUserId(loginRequestDto.getUserId()).isPresent())
            throw new IdDuplicateException();
        return memberRepository.save(loginDtoToEntity(loginRequestDto)).getId();
    }

    public boolean deleteUser(LoginRequestDto loginRequestDto) throws IdNotFoundException {
        memberRepository.deleteById(memberRepository.findByUserId(loginRequestDto.getUserId()).orElseThrow(IdNotFoundException::new).getId());
        return true;
    }

    public Member loginDtoToEntity(LoginRequestDto dto){
        return Member.builder()
                .userId(dto.getUserId())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build();
    }

}
