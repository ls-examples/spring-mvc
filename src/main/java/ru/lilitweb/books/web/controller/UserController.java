package ru.lilitweb.books.web.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.lilitweb.books.dto.UserDto;
import ru.lilitweb.books.service.EmailExistsException;
import ru.lilitweb.books.service.UserService;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class UserController {

    private MessageSource messageSource;
    private UserService userService;

    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserDto());
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String register(@ModelAttribute("user") @Valid UserDto userDto,
                           BindingResult result,
                           RedirectAttributes attr, Locale locale) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            attr.addFlashAttribute("user", userDto);
            return "redirect:/signup";
        }
        try {
            userService.registerNewUserAccount(userDto);
        } catch (EmailExistsException e) {
            result.addError(new ObjectError("email", messageSource.getMessage("email.error.alredy.used", null, locale)));
            attr.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            attr.addFlashAttribute("user", userDto);
            return "redirect:/signup";
        }
        return "redirect:/login";
    }

    @RequestMapping("/signin")
    public String login() {
        return "login";
    }

    @RequestMapping("/signin-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
