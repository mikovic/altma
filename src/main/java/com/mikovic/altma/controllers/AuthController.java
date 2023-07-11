package com.mikovic.altma.controllers;
import com.mikovic.altma.enums.AuthProvider;
import com.mikovic.altma.exception.BadRequestException;
import com.mikovic.altma.modeles.ConfirmationToken;
import com.mikovic.altma.modeles.Role;
import com.mikovic.altma.modeles.User;
import com.mikovic.altma.payload.AuthResponse;
import com.mikovic.altma.payload.ForgotRequest;
import com.mikovic.altma.payload.LoginRequest;
import com.mikovic.altma.payload.SignUpRequest;
import com.mikovic.altma.security.CustomUserDetailsService;
import com.mikovic.altma.security.TokenProvider;
import com.mikovic.altma.security.UserPrincipal;
import com.mikovic.altma.services.ConfirmationTokenRepository;
import com.mikovic.altma.services.EmailSenderService;
import com.mikovic.altma.services.RoleService;
import com.mikovic.altma.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessageSource messages;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private RoleService roleService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    EmailSenderService emailSenderService;

    @Value("${server.context.path}")
    private  String pathContext;


    @PostMapping("/login")
    public String authenticateUser(@Valid LoginRequest loginRequest , BindingResult bindingResult, Model model) throws BadCredentialsException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("forgotRequest", new ForgotRequest());
            return "login";
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        ResponseEntity.ok(new AuthResponse(token));
        return "index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String registerUser(@Valid SignUpRequest signUpRequest, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes ra,  Locale locale, Model model) {

        if (bindingResult.hasErrors()) {
            return  "registration-form";
        }
        if(userService.existsByEmail(signUpRequest.getEmail())) {
            User user = userService.findByEmail(signUpRequest.getEmail());
            if(user.getEmailVerified()) {
                model.addAttribute("message",  messageSource.getMessage("local.account", null,locale));
                return "login";
//                throw new BadRequestException("Email address already in use.");
            }else {
                SignUpRequest sr = creatSignUpRequestFromUser(user);
                request.getSession().setAttribute("signUpRequest", sr );
                ra.addFlashAttribute("message", messageSource.getMessage("update.user.social", null,locale));
                return  "redirect:/auth/update";
            }
//            if(user.getProvider().equals(AuthProvider.local)){
//                    sendToken(user, locale);
//                    return "index";
//                }
//                    userService.delete(user);
            }



        // Creating user's account
        User user = new User();        ;
        Role role = roleService.findById(1L);
        user.addRole(role);
        StringBuilder requestURL = new StringBuilder(pathContext);
        String url = requestURL.append("/auth/confirm-account?token=").toString();

        sendToken(creatUserFromRequest(user, signUpRequest ), url,  locale);

        model.addAttribute("message",  messageSource.getMessage("complete.registration", null,locale));
//        Если нужно вернуть ResponseEntity
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/user/me")
//                .buildAndExpand(result.getId()).toUri();
//
//        return ResponseEntity.created(location)
//                .body(new ApiResponse(true, "User registered successfully@"));
//        String referrer = request.getHeader("referer");
//        return "redirect:" + referrer;

        return "redirect:/";

    }
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String showUpdateRegistrationForm(HttpServletRequest request, Model model, Locale locale) {

        model.addAttribute("signUpRequest", request.getSession().getAttribute("signUpRequest"));
        return "update-registration-form";
}

    private User creatUserFromRequest(User user, SignUpRequest signUpRequest){
        user.setFirstName(signUpRequest.getFirstName().trim());
        user.setLastName(signUpRequest.getLastName().trim());
        user.setNickName(signUpRequest.getNickName().trim());
        user.setEmail(signUpRequest.getEmail().trim());
        user.setPassword(signUpRequest.getPassword().trim());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEmailVerified(false);
        return userService.save(user);

    }
    private SignUpRequest creatSignUpRequestFromUser(User user){
        SignUpRequest sr = new SignUpRequest();
        sr.setId(user.getId());
        sr.setFirstName(user.getFirstName());
        sr.setLastName(user.getLastName());
        sr.setNickName(user.getNickName());
        sr.setEmail(user.getEmail());
        return sr;

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(@Valid SignUpRequest signUpRequest, BindingResult bindingResult,HttpServletRequest request, Locale locale, Model model ) {

        if (bindingResult.hasErrors()) {
            return "update-registration-form";
        }


//            if(user.getProvider().equals(AuthProvider.local)){
//                    sendToken(user, locale);
//                    return "index";
//                }
//                    userService.delete(user);

            User user = userService.findOneById(signUpRequest.getId());
        if(signUpRequest.getEmail().equals(user.getEmail())){

            User updateUser = creatUserFromRequest(user, signUpRequest);
            UserDetails userInfo = customUserDetailsService.loadUserByUsername(updateUser.getEmail());
            Authentication auth = new
                    UsernamePasswordAuthenticationToken(userInfo, userInfo.getPassword(), userInfo.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            confirmationTokenRepository.deleteConfirmationTokenFromUser(user.getId());

            return "redirect:/";
        }
        StringBuilder requestURL = new StringBuilder(pathContext);
        String url = requestURL.append( "/auth/confirm-account?token=").toString();


        sendToken(creatUserFromRequest(user, signUpRequest ), url, locale);

        model.addAttribute("message",  messageSource.getMessage("complete.registration", null,locale));
        return "index";

    }



    @ExceptionHandler(value = BadCredentialsException.class)
    public String exception(BadCredentialsException ex) {
        return  "redirect:/signin?error=invalid";
    }
    @ExceptionHandler(value = MailException.class)
    public String mailException(MailException  ex, Model model, Locale locale){

        String message = messages.getMessage("invalid.send.email", null, locale);

        model.addAttribute("message", message);
        model.addAttribute("loginRequest", new LoginRequest());
        model.addAttribute("forgotRequest", new ForgotRequest());
        return "login";
    }


    @PostMapping("/forgot/pwd")
    public String forgotPage(@Valid ForgotRequest forgotRequest, BindingResult bindingResult,
                             HttpServletRequest request,  Model model, RedirectAttributes rs,  Locale locale) throws InterruptedException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("loginRequest", new LoginRequest());
            return "login";
        }
        String message = null;
        if(userService.existsByEmail(forgotRequest.getEmail())) {
            User user = userService.findByEmail(forgotRequest.getEmail());

            StringBuilder requestURL = new StringBuilder(pathContext);

            String url = requestURL.append("/auth/confirm-pwd?token=").toString();

            sendToken(user, url, locale);
            message = messages.getMessage("complete.registration", null, locale);

        }else{
            message = messages.getMessage("wrong.user", null, locale);

        }

        rs.addFlashAttribute("message", message);
        return "redirect:/signin?lang=" + locale.getLanguage();

    }
    @RequestMapping(value="/confirm-pwd", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmPwd (@RequestParam("token")String confirmationToken, HttpServletRequest request, RedirectAttributes ra )
            throws MailException{
        Locale locale = request.getLocale();
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token == null)
        {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            ra.addFlashAttribute("message", message);
            return "redirect:/signin?lang=" + locale.getLanguage();

        }

        String email = token.getUser().getEmail();
        User user = userService.findByEmailIgnoreCase(email);
        Calendar cal = Calendar.getInstance();
        if ((token.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            StringBuilder requestURL = new StringBuilder(pathContext);
            String url = requestURL.append("/auth/confirm-account?token=").toString();

            sendToken(user, url,  locale);
            String message = messages.getMessage("auth.message.newtoken", null, locale);
            ra.addFlashAttribute("message", message);
            return "redirect:/signin?lang=" + locale.getLanguage();
        }
        String message = messages.getMessage("confirmed.user.email", null, locale);
        ra.addFlashAttribute("message", message);
        SignUpRequest sr = creatSignUpRequestFromUser(user);

        request.getSession().setAttribute("signUpRequest", sr );
        return "redirect:/auth/update";
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token")String confirmationToken, HttpServletRequest request, RedirectAttributes ra )


    {
        Locale locale = request.getLocale();
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token == null)
        {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            ra.addFlashAttribute("message", message);
            return "redirect:/signin?lang=" + locale.getLanguage();

        }

        String email = token.getUser().getEmail();
        User user = userService.findByEmailIgnoreCase(email);
        Calendar cal = Calendar.getInstance();
        if ((token.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            StringBuilder requestURL = new StringBuilder(pathContext);
            String url = requestURL.append("/auth/confirm-account?token=").toString();

            sendToken(user, url,  locale);
            String message = messages.getMessage("complete.registration.email", null, locale);
            ra.addFlashAttribute("message", message);
            return "redirect:/signin?lang=" + locale.getLanguage();
        }

        user.setEmailVerified(true);
        userService.save(user);
        UserDetails userInfo = customUserDetailsService.loadUserByUsername(email);
        Authentication auth = new
                UsernamePasswordAuthenticationToken(userInfo, userInfo.getPassword(), userInfo.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        confirmationTokenRepository.deleteConfirmationTokenFromUser(user.getId());

        return "redirect:/";

    }
        private void sendToken (User user, String url, Locale locale) throws MailException{
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);
            String subject = messageSource.getMessage("complete.registration.email", null, locale);

            StringBuilder  requestURL = new StringBuilder(url);
            requestURL.append(confirmationToken.getConfirmationToken());

//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setTo(user.getEmail());
//            mailMessage.setSubject(subject);
//            mailMessage.setFrom("spring.mail.username");

//            mailMessage.setText(sb.toString());
//            emailSenderService.sendEmail(mailMessage);

            emailSenderService.prepareAndSend(user.getEmail(), subject, requestURL.toString(), user.getFirstName());



        }
}
