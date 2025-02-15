package com.board.member.controller;

import com.board.common.exception.member.NameValidException;
import com.board.common.jwt.JWTUtil;
import com.board.config.ApiResponse;
import com.board.constant.StatusEnum;
import com.board.entity.Refresh;
import com.board.member.repository.RefreshRepository;
import com.board.member.request.JoinRequest;
import com.board.member.service.MemberService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private MemberService memberService;
    private JWTUtil jwtUtil;
    private RefreshRepository refreshRepository;

    @Autowired
    public AuthController(MemberService memberService, JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    /**
     * username 중복 체크
     * @param username
     * @return
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getChkUsername(@PathVariable("username") @NotBlank String username){
        if (!username.matches("^[가-힣A-Za-z0-9_-]{4,10}$")) {
            throw new NameValidException();
        }

        return ResponseEntity.ok(ApiResponse.builder()
                .status(StatusEnum.OK)
                .msg("사용가능 합니다.")
                .data(memberService.getChkUsername(username))
                .build());
    }

    /**
     * 회원가입
     * @param joinRequest
     * @return
     */
    @PostMapping("")
    public ResponseEntity<?> createMember(@Valid @RequestBody JoinRequest joinRequest){
        memberService.createMember(joinRequest);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(StatusEnum.OK)
                .msg("회원가입을 성공 했습니다.")
                .data("")
                .build());
    }

    // 토큰 재발급?
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response){
        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {

            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefreshToken(refresh);
        if (!isExist) {

            //response body
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefreshToken(refresh);
        addRefreshEntity(username, newRefresh, 86400000L);

        //response
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, long expiredMs) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.add(Calendar.SECOND, Integer.parseInt(String.valueOf(expiredMs / 1000)));

        Timestamp newDate = new Timestamp(calendar.getTimeInMillis());

        Refresh refreshEntity = Refresh.builder()
                .username(username)
                .refreshToken(refresh)
                .expiration(newDate)
                .build();

        refreshRepository.save(refreshEntity);
    }
}
