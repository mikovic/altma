package com.mikovic.altma.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikovic.altma.modeles.User;
import com.mikovic.altma.payload.DataFacebookDelete;
import com.mikovic.altma.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/facebook")
public class FacebookDataDeletionCallbackController {


    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${facebook.clientSecret}")
    private  String facebookAppSecret;



}
