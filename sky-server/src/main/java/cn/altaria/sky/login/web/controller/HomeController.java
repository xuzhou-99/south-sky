package cn.altaria.sky.login.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/24 14:50
 */
@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home(HttpServletRequest request) {
        return "home";
    }
}
