package com.mikovic.altma.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikovic.altma.modeles.User;
import com.mikovic.altma.payload.DataFacebookDelete;
import com.mikovic.altma.payload.ForgotRequest;
import com.mikovic.altma.payload.LoginRequest;
import com.mikovic.altma.payload.SignUpRequest;
import com.mikovic.altma.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Controller
public class LoginController {




    @GetMapping("/signin")
    public String showMyLoginPage(Model model) {
        model.addAttribute("forgotRequest", new ForgotRequest());
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @GetMapping("/signup")
    public String showMySignUpPage(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "registration-form";
    }


}



