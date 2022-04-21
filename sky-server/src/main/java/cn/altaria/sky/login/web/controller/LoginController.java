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
        String serviceUrl = request.getParameter("service");
        log.info("【SSO单点登录】开始登录，原系统路径：{}", serviceUrl);
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPost(@RequestParam String email, @RequestParam String password,
                                       HttpServletRequest request, HttpServletResponse response) throws LoginException {

        boolean loginSuccess = loginService.login(email, password);
        if (loginSuccess) {
            request.getSession().setAttribute("isLogin", true);

            String token = UUID.randomUUID().toString();
            SystemRegister.getINSTANCE().put(token, token);

            String serviceUrl = request.getParameter("service");
            log.info("【SSO单点登录】登录成功，原系统路径：{}", serviceUrl);
            return ResponseEntity.ok(serviceUrl + "?token=" + token);
        }

        return ResponseEntity.ok("login");
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            // 触发 LogoutListener
            session.invalidate();
        }
        return "redirect:/";
    }

    @RequestMapping("/verify")
    @ResponseBody
    public String verify(HttpServletRequest request, HttpServletResponse response, String token) {
        if (SystemRegister.getINSTANCE().containsKey(token)) {
            StringBuffer systemUrl = request.getRequestURL();
            SystemRegister.getINSTANCE().put(token, systemUrl.toString());
            return "true";
        } else {
            return "fail";
        }
    }
}
