package cn.altaria.sky.login.web.controller;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.altaria.sky.login.CookieUtil;
import cn.altaria.sky.login.cache.SystemRegister;
import cn.altaria.sky.login.exception.LoginException;
import cn.altaria.sky.login.service.ILoginService;
import lombok.extern.slf4j.Slf4j;

/**
 * LoginController
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/19 13:49
 */
@Slf4j
@Controller
@RequestMapping()
public class LoginController {

    @Resource
    private ILoginService loginService;

    @GetMapping("/login")
    public String login(HttpServletRequest request) {

        String token = (String) request.getSession().getAttribute("token");

        if (token != null) {
            if (SystemRegister.getINSTANCE().containsKey(token)) {
                String serviceUrl = request.getParameter("service");
                if (serviceUrl == null) {
                    serviceUrl =  request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                            request.getContextPath() + "/";
                }
                log.info("【SSO单点登录】开始登录，原系统路径：{}, 存在token：{}", serviceUrl, token);

                return "redirect:" + serviceUrl + "?token=" + token;
            }
        }

        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPost(@RequestParam String email, @RequestParam String password,
                                       HttpServletRequest request, HttpServletResponse response) throws LoginException {
        return loginService.loginSso(request, response, email, password);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, String token) {
        loginService.logout(request, token);
        return "redirect:/";
    }

    @RequestMapping("/verify")
    @ResponseBody
    public String verify(HttpServletRequest request, String token, String jsessionid) {
        return loginService.verify(request, token, jsessionid);
    }
}
