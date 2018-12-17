package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.domain.dto.CaptchaResponseDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    private final static String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Autowired
    private UserService userService;

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("password2") String passwordConfirm,
                          @RequestParam("g-recaptcha-response") String captchaResponse,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model)
    {


        CaptchaResponseDto responseCaptchaDto = restTemplate
                .postForObject(String.format(RECAPTCHA_URL, secret, captchaResponse),
                        Collections.emptyList(), CaptchaResponseDto.class);


        if (!responseCaptchaDto.isSuccess()) {
            model.addAttribute("messageType", "danger");
            model.addAttribute("captchaError", "Fill captcha");
        }

        boolean isPasswordConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isPasswordConfirmEmpty) {
            model.addAttribute("password2", "Password confirmation cannot be null");
        }

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Passwords are different!");

        }
        if (isPasswordConfirmEmpty || bindingResult.hasErrors() || !responseCaptchaDto.isSuccess()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated!");
        }
        else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code for user not found!");
        }
        return "login";
    }
}