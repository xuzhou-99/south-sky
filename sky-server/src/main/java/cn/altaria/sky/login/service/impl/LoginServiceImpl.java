package cn.altaria.sky.login.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cn.altaria.sky.login.CookieUtil;
import cn.altaria.sky.login.cache.ClientSessionVo;
import cn.altaria.sky.login.cache.SystemRegister;
import cn.altaria.sky.login.cache.SystemSessionRegister;
import cn.altaria.sky.login.exception.LoginException;
import cn.altaria.sky.login.pojo.UserPojo;
import cn.altaria.sky.login.service.ILoginService;
import cn.altaria.sky.login.service.IUserService;
import lombok.extern.slf4j.Slf4j;

/**
 * LoginServiceImpl
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/19 13:57
 */
@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {

    @Resource
    private IUserService userService;

    @Override
    public boolean login(String email, String password) throws LoginException {

        if (email == null) {
            throw new LoginException("用户密码不能为空！");
        }
        UserPojo userPojo = userService.load(null, email, null, null);
        if (userPojo == null) {
            throw new LoginException("用户不存在！");
        }
        if (!Objects.equals(userPojo.getPassword(), password)) {
            throw new LoginException("用户名或密码错误！");
        }

        return true;
    }

    @Override
    public ResponseEntity<?> loginSso(HttpServletRequest request, HttpServletResponse response, String email, String password) throws LoginException {

        String serviceUrl = request.getParameter("service");
        HttpSession session = request.getSession();

        String path = request.getContextPath();
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

        if (serviceUrl == null) {
            serviceUrl = url;
        }

        boolean loginSuccess = this.login(email, password);

        if (loginSuccess) {

            String token = UUID.randomUUID().toString();
            SystemRegister.getINSTANCE().put(token, session.getId());

            session.setAttribute("token", token);
            CookieUtil.setCookie("token", token, 60 * 30, response);

            log.info("【登录】登录成功，系统路径：{}", serviceUrl);
            log.info("【session】 {} ，sessionId = {} ", serviceUrl, session.getId());

            return ResponseEntity.ok(serviceUrl + "?token=" + token);
        }

        log.info("【登录】登录失败，系统路径：{}", url);
        return ResponseEntity.ok("login");
    }

    @Override
    public void logout(HttpServletRequest request, String token) {
        HttpSession session = request.getSession();

        if (token == null) {
            token = (String) session.getAttribute("token");
        }
        log.info("【登录】通知其他系统登出");
        log.info("【session】sessionId = {} ，token = {}", session.getId(), token);
        if (null != token) {
            SystemRegister.getINSTANCE().remove(token);
            log.info("【SSO单点登录】移除token");
        }

        // 触发 LogoutListener
        session.invalidate();
    }

    @Override
    public String verify(HttpServletRequest request, String token, String jsessionid) {
        if (token != null) {
            if (SystemRegister.getINSTANCE().containsKey(token)) {
                log.info("【SSO单点登录】{} 用户", token);

                String systemUrl = request.getParameter("service");
                if (systemUrl == null) {
                    systemUrl = request.getRequestURL().toString();
                }
                // 注册系统 systemUrl
                List<ClientSessionVo> clientSessionVos = SystemSessionRegister.getINSTANCE().get(token);
                if (clientSessionVos == null) {
                    clientSessionVos = new ArrayList<>();
                }
                ClientSessionVo client = ClientSessionVo.builder()
                        .clientUrl(systemUrl)
                        .jSessionId(jsessionid)
                        .build();
                clientSessionVos.add(client);

                SystemSessionRegister.getINSTANCE().put(token, clientSessionVos);

                log.info("【SSO单点登录】{} 用户，系统上线：{}，sessionId：{}", token, systemUrl, jsessionid);

                return "true";
            }
        }

        return "fail";
    }

}
