package pl.powtorka.Spring.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.powtorka.Spring.user.UserService;
import pl.powtorka.Spring.user.dto.UserRegistrationDto;

@Controller
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/rejestracja")
    public String registrationForm(Model model){
        UserRegistrationDto userRegistration = new UserRegistrationDto();
        model.addAttribute("user", userRegistration);
        return "registration-form";
    }

    @PostMapping("/rejestracja")
    public String register(UserRegistrationDto uSerRegistrationDto){
        userService.registerUserWithDefaultRole(uSerRegistrationDto);
        return "redirect:/";
    }

}
