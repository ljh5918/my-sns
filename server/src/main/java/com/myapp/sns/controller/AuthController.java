// package com.myapp.sns.controller;

// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.http.ResponseEntity;
// import org.springframework.http.HttpStatus;

// import lombok.RequiredArgsConstructor;

// import com.myapp.sns.entity.User; 
// import com.myapp.sns.dto.LoginRequest;
// import com.myapp.sns.dto.SignupRequest;
// import com.myapp.sns.repository.UserRepository;

// import org.springframework.security.crypto.password.PasswordEncoder;




// @RestController
// @RequestMapping("/api/auth")
// @CrossOrigin(origins = "http://localhost:3000")
// @RequiredArgsConstructor
// public class AuthController {

//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;

//     @PostMapping("/signup")
//     public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
//         if (userRepository.findByEmail(request.getEmail()).isPresent()) {
//             return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다.");
//         }

//         User user = User.builder()
//                 .email(request.getEmail())
//                 .username(request.getUsername())
//                 .password(passwordEncoder.encode(request.getPassword()))
//                 .build();

//         userRepository.save(user);
//         return ResponseEntity.ok("회원가입 성공");
//     }

//     @PostMapping("/login")
//     public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//         User user = userRepository.findByEmail(request.getEmail())
//                 .orElse(null);

//         if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 틀렸습니다.");
//         }

//         // JWT 발급 추가 예정
//         return ResponseEntity.ok("로그인 성공");
//     }
// }










package com.myapp.sns.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

import com.myapp.sns.entity.User;
import com.myapp.sns.jwt.JwtUtil;
import com.myapp.sns.dto.LoginRequest;
import com.myapp.sns.dto.SignupRequest;
import com.myapp.sns.repository.UserRepository;

import io.jsonwebtoken.lang.Collections;
import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;




@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
     private final JwtUtil jwtUtil; 

   @PostMapping("/signup")
public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request,
                                BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        // 에러 메시지 반환
        return ResponseEntity.badRequest().body(
                bindingResult.getAllErrors().get(0).getDefaultMessage()
        );
    }

    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
        return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다.");
    }

    User user = User.builder()
            .email(request.getEmail())
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();

    userRepository.save(user);
    return ResponseEntity.ok("회원가입 성공");
}

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
            .orElse(null);

    if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 틀렸습니다.");
    }

    //jwt 토큰 
    String token = jwtUtil.generateToken(user.getEmail());
    return ResponseEntity.ok(token);
}


}
