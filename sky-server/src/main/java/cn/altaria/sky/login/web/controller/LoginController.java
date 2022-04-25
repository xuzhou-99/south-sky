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
        String serviceUrl = request.getParameter("service");
        if(serviceUrl == null) {
            serviceUrl = request.getRequestURI();
        }
        log.info("【SSO单点登录】开始登录，原系统路径：{}", serviceUrl);

        String token = CookieUtil.getCookie("token", request);

        if (SystemRegister.getINSTANCE().containsKey(token)) {
            return "redirect:" + serviceUrl + "?token=" + token;
        }
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPost(@RequestParam String email, @RequestParam String password,
                                       HttpServletRequest request, HttpServletResponse response) throws LoginException {

        String serviceUrl = request.getParameter("service");
        HttpSession session = request.getSession();

        String path = request.getContextPath();
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

        if (serviceUrl == null) {
            serviceUrl = url;
        }

        boolean loginSuccess = loginService.login(email, password);

        if (loginSuccess) {
            request.getSession().setAttribute("isLogin", true);

            String token = UUID.randomUUID().toString();
            SystemRegister.getINSTANCE().put(token, serviceUrl);

            log.info("【登录】登录成功，系统路径：{}", serviceUrl);
            log.info("【session】 {} ，sessionId = {} ", serviceUrl, session.getId());

            return ResponseEntity.ok(serviceUrl + "?token=" + token);
        }

        log.info("【登录】登录失败，系统路径：{}", url);
        return ResponseEntity.ok("login");
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, String token) {

        HttpSession session = request.getSession();

        if (token == null) {
            token = CookieUtil.getCookie("token", request);
        }
        log.info("【登录】登出");
        log.info("【session】sessionId = {} ", session.getId());

        if (session != null) {
            // 触发 LogoutListener
            session.invalidate();
        }

        loginService.logout(token);
        return "redirect:/";
    }

    @RequestMapping("/verify")
    @ResponseBody
    public String verify(HttpServletRequest request, String token) {
        return loginService.verify(request, token);
    }
}
