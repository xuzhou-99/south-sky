package cn.altaria.sky.login.config;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.altaria.sky.login.cache.TokenRegister;
import cn.altaria.sky.login.service.ISsoService;
import cn.altaria.sky.login.spring.SpringBeanUtils;
import cn.altaria.sky.login.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * LoginFilter
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/19 14:00
 */
@Slf4j
public class LoginFilter implements Filter {

    private FilterConfig filterConfig;

    private SSOLoginConfig ssoLoginConfig;

    private ISsoService ssoService;

    /**
     * 初始化
     *
     * @param filterConfig 过滤器配置
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        this.ssoService = SpringBeanUtils.getBean(ISsoService.class);
        this.ssoLoginConfig = SpringBeanUtils.getBean(SSOLoginConfig.class);
    }

    /**
     * 过滤内容
     *
     * @param servletRequest  请求
     * @param servletResponse 响应
     * @param filterChain     过滤链
     * @throws IOException      异常
     * @throws ServletException 服务异常
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        log.info("-----------sso拦截器-----------");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String path = request.getContextPath();
        String url = servletRequest.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        StringBuffer requestUrl = request.getRequestURL();
        log.info("【SSO登录】requestUrl:{}", requestUrl);

        // 去sso认证中心校验token
        String token = "";
        if (this.ssoLoginConfig == null) {
            this.ssoLoginConfig = SpringBeanUtils.getBean(SSOLoginConfig.class);
        }
        if (this.ssoService == null) {
            this.ssoService = SpringBeanUtils.getBean(ISsoService.class);
        }

        // 登出
        int logoutIndex = requestUrl.indexOf("logout");
        if (logoutIndex > -1) {

            token = TokenRegister.getINSTANCE().get(session.getId());

            session.invalidate();

            log.info("【SSO登录】登出操作，SSOUrl: {}，token: {}", ssoLoginConfig.getUrl(), token);

            this.ssoService.logout(ssoLoginConfig.getUrl() + "/logout", token);

            // 跳转至sso认证中心 sso-server-url-with-system-url
            response.sendRedirect(ssoLoginConfig.getUrl() + "/login?service=" + url);
            return;
        }

        // 登录成功
        Object isLogin = session.getAttribute("isLogin");
        if (Objects.nonNull(isLogin) && (Boolean) isLogin) {
            log.info("【SSO登录】登录成功");
            filterChain.doFilter(request, response);
            return;
        }

        // 尚未登录，跳转sso，并进行验证
        token = request.getParameter("token");
        if (token == null) {
            token = CookieUtil.getCookie("token", request);
        }
        if (token != null) {
            // sso-server-verify-url
            boolean verifyResult = this.ssoService.verify(ssoLoginConfig.getUrl() + "/verify", url, token);
            if (!verifyResult) {
                log.info("【SSO登录】登录令牌校验未通过，重定向到登录页面");
                // 校验未通过，重新登录
                response.sendRedirect(ssoLoginConfig.getUrl() + "/login?service=" + url);
                return;
            }

            log.info("【SSO登录】登录令牌校验通过，登录成功");
            session.setAttribute("isLogin", true);
            TokenRegister.getINSTANCE().put(session.getId(), token);

            CookieUtil.setCookie("token", token, 60 * 30, response);
            response.sendRedirect(url);

            filterChain.doFilter(request, response);
            return;
        }

        // 跳转至sso认证中心 sso-server-url-with-system-url
        response.sendRedirect(ssoLoginConfig.getUrl() + "/login?service=" + url);
    }


    /**
     * 停止服务
     */
    @Override
    public void destroy() {

    }

    public FilterConfig getFilterConfig() {
        return this.filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}
