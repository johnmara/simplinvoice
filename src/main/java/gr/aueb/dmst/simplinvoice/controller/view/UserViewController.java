package gr.aueb.dmst.simplinvoice.controller.view;

import gr.aueb.dmst.simplinvoice.dto.UserDto;
import gr.aueb.dmst.simplinvoice.model.User;
import gr.aueb.dmst.simplinvoice.service.UserService;
import gr.aueb.dmst.simplinvoice.validator.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserViewController {

    @Autowired
    UserService userService;

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

    @GetMapping("/register")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDto userDto, Errors errors, HttpServletRequest request) {

        if(errors.hasErrors())
            return new ModelAndView("register");

        try {
            final User registered = userService.registerNewUserAccount(userDto);
        } catch (final EmailExistsException uaeEx) {
            ModelAndView mav = new ModelAndView("register", "user", userDto);
            String errMessage = messages.getMessage("messages.email.exists", null, request.getLocale());
            mav.addObject("message", errMessage);
            return mav;
        } catch (final RuntimeException ex) {
            return new ModelAndView("emailError", "user", userDto);
        }
        return new ModelAndView("redirect:/login");
    }

}
