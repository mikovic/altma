package com.mikovic.altma.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikovic.altma.modeles.User;
import com.mikovic.altma.payload.DataFacebookDelete;
import com.mikovic.altma.services.UserService;
import com.mikovic.altma.utils.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;


@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Value("${facebook.clientSecret}")
    private String facebookAppSecret;
    @Value("${server.https,context.path}")
    private String URL;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/")
    public String index() {

        LOGGER.info("Invoke {} >", Utilities.getCurrentMethodName());
        return "index";
    }
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @RequestMapping(value = "/remove_fb_data", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Object removeApp(@RequestParam(value = "signed_request") String signedRequest) throws Exception {

        Map<String, Object> data = parseSignedRequest(signedRequest);
        String providerId = (String) data.get("user_id");
        String url = URL + "/deletion?id=" + providerId; // URL to track the deletion
        String confirmation_code = providerId; // unique code for the deletion request

        User user = userService.findByProviderId(providerId);

        // Start data deletion
        if (user != null) {
            userService.delete(user);
        }
        DataFacebookDelete dataFacebookDelete = new DataFacebookDelete();
        dataFacebookDelete.setUrl(url);
        dataFacebookDelete.setConfirmation_code(confirmation_code);
        String jsonString = objectMapper.writeValueAsString(dataFacebookDelete);


        return jsonString;
    }

    private Map<String, Object> parseSignedRequest(String signedRequest) throws Exception {
        int split = signedRequest.indexOf('.');
        String encoded_sig = signedRequest.substring(0, split);
        String payload = signedRequest.substring(split + 1);

        // decode the data
        byte[] sig = Base64.getUrlDecoder().decode(encoded_sig);
        Map<String, Object> data = objectMapper.readValue(Base64.getUrlDecoder().decode(payload), new TypeReference<Map<String, Object>>() {
        });

        // confirm the signature
        byte[] expected_sig = hash_hmac(payload.getBytes(StandardCharsets.UTF_8));
        if (!Arrays.equals(sig, expected_sig)) {

            return Collections.<String, Object>emptyMap();
        }
        return data;
    }


    public byte[] hash_hmac(byte[] bytes) throws Exception {
        Key sk = new SecretKeySpec(facebookAppSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance(sk.getAlgorithm());
        mac.init(sk);
        return mac.doFinal(bytes);
    }

    @GetMapping("/deletion")
    public ResponseEntity<String> confirmDeletionFacebook(@RequestParam(value = "fbclid", required = false) String providerId, Locale local) {
        User user = userService.findByProviderId(providerId);
        if (user == null) {

        }
        return new ResponseEntity<>(messageSource.getMessage("user.delete", null, local), HttpStatus.OK);

    }

}
