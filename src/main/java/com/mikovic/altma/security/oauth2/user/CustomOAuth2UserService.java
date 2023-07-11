package com.mikovic.altma.security.oauth2.user;

import com.mikovic.altma.enums.AuthProvider;
import com.mikovic.altma.exception.BadRequestException;
import com.mikovic.altma.exception.OAuth2AuthenticationProcessingException;
import com.mikovic.altma.modeles.Role;
import com.mikovic.altma.modeles.User;
import com.mikovic.altma.repositories.UserRepository;
import com.mikovic.altma.security.UserPrincipal;
import com.mikovic.altma.security.oauth2.OAuth2UserInfoFactory;
import com.mikovic.altma.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.*;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = null;
        if (oAuth2UserRequest.getClientRegistration().getRegistrationId().equalsIgnoreCase("vk")) {
            oAuth2User = loadVkUser(oAuth2UserRequest);
        } else {

            oAuth2User = super.loadUser(oAuth2UserRequest);
        }

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }



    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        Optional<User> userOptional;
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            userOptional = userRepository.findByProviderId(oAuth2UserInfo.getId());
        } else {
            userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        }
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException( user.getProvider() +
                        ".account");
//                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
//                        user.getProvider() + " account. Please use your " + user.getProvider() +
//                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }


    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setProviderId(oAuth2UserInfo.getId());
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        if (user.getProvider().toString().equalsIgnoreCase("github")) {
            user.setFirstName(oAuth2UserInfo.attributes.get("login").toString());

            user.setNickName(oAuth2UserInfo.attributes.get("login").toString());
        } else if (user.getProvider().toString().equalsIgnoreCase("facebook")) {

            user.setFirstName(oAuth2UserInfo.getName());
            user.setLastName(oAuth2UserInfo.getLastName());
            user.setNickName(oAuth2UserInfo.getName());
        } else if (user.getProvider().toString().equalsIgnoreCase("vk")) {
            user.setFirstName(oAuth2UserInfo.getName());
            user.setNickName(oAuth2UserInfo.getName());
            user.setLastName(oAuth2UserInfo.getLastName());




        }
        user.setPassword(passwordEncoder.encode(oAuth2UserInfo.getId()));
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        Role role = roleService.findById(1L);
        user.addRole(role);
        user = userRepository.save(user);
        return user;
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        //чуть изменил во второй части else if
        if (existingUser.getProvider().toString().equalsIgnoreCase("github")) {
            existingUser.setFirstName(oAuth2UserInfo.attributes.get("login").toString());

            existingUser.setNickName(oAuth2UserInfo.attributes.get("login").toString());
        } else if (existingUser.getProvider().toString().equalsIgnoreCase("facebook")) {
            existingUser.setFirstName(oAuth2UserInfo.getName());
            existingUser.setNickName(oAuth2UserInfo.getName());
        } else if (existingUser.getProvider().toString().equalsIgnoreCase("vk")) {
            existingUser.setFirstName(oAuth2UserInfo.getName());
            existingUser.setNickName(oAuth2UserInfo.getName());
        }
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

    private OAuth2User loadVkUser(OAuth2UserRequest oAuth2UserRequest) {
        RestTemplate template = new RestTemplate();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", oAuth2UserRequest.getAccessToken().getTokenType().getValue() + " " + oAuth2UserRequest.getAccessToken().getTokenValue());
        HttpEntity<?> httpRequest = new HttpEntity(headers);
        String uri = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
        String userNameAttributeName = "user_id";
        uri = uri.replace("{user_id}", userNameAttributeName + "=" + oAuth2UserRequest.getAdditionalParameters().get(userNameAttributeName));

        try {
            ResponseEntity<Object> entity = template.exchange(uri, HttpMethod.GET, httpRequest, Object.class);
            Map<String, Object> response = (Map) entity.getBody();
            ArrayList valueList = (ArrayList) response.get("response");
            Map<String, Object> userAttributes = (Map<String, Object>) valueList.get(0);
            userAttributes.put(userNameAttributeName, oAuth2UserRequest.getAdditionalParameters().get(userNameAttributeName));
            userAttributes.put("email", oAuth2UserRequest.getAdditionalParameters().get("email"));

            Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));
            return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);

        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            throw new OAuth2AuthenticationProcessingException (ex.getMessage(), ex);
        }
    }

}