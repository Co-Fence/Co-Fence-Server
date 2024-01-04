//package com.gdsc.cofence.controller;
//
//import com.gdsc.cofence.entity.user.User;
//import com.gdsc.cofence.service.login.UserLoginService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.security.Principal;
//
//@RestController
//@RequiredArgsConstructor
//public class TestController {
//
//    private final UserLoginService userloginService;
//
//    @GetMapping("/googlelogin/test")
//    public User googleLoginTest(Principal principal) {
//        return userloginService.test(principal);
//    }
//
//    @GetMapping("/kakaologin/test")
//    public User kakaoLoginTest(Principal principal) {
//        return userloginService.test(principal);
//    }
//}
