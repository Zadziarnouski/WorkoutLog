package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;

class LoginAndRegistrationControllerTest {

    UserService userService;
    PasswordEncoder passwordEncoder;
    Model model;



    @Test
    void getLoginPage() {
        LoginAndRegistrationController loginAndRegistrationController = new LoginAndRegistrationController(userService,passwordEncoder);
        String loginPage = loginAndRegistrationController.getLoginPage();
        assertEquals(loginPage,"login");
    }

    @Test
    void getRegistrationPage() {
    }

    @Test
    void registerNewUser() {
    }
}