package cn.altaria.sky.login.config;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

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

        StringBuffer requestUrl = request.getRequestURL();
        log.info("requestUrl:{}", requestUrl);

        Object isLogin = session.getAttribute("isLogin");
        if (Objects.nonNull(isLogin) && (Boolean) isLogin) {
            filterChain.doFilter(request, response);
            return;
        }

        // 去sso认证中心校验token
        String token = request.getParameter("token");

        String ssoUrl = "http://172.23.22.58:8081";

        // "sso-server-verify-url"
        String verifyUrl = "http://172.23.22.58:8081/verify";

        String logout = request.getParameter("logout");
        if (logout != null) {
            session.invalidate();
            token = TokenRegister.getINSTANCE().get(session.getId());
            this.ssoService.logout(ssoUrl + "/logout", token);
            // 跳转至sso认证中心 sso-server-url-with-system-url
            response.sendRedirect(ssoUrl + "/login?service=" + requestUrl + "&uuid=" + UUID.randomUUID());
            return;
        }

        if (token != null) {
            this.ssoService = SpringBeanUtils.getBean(ISsoService.class);
            boolean verifyResult = this.ssoService.verify(verifyUrl, token);
            if (!verifyResult) {
                // 校验未通过，重新登录
                response.sendRedirect(ssoUrl + "/login?service=" + requestUrl + "&uuid=" + UUID.randomUUID());
                return;
            }

            session.setAttribute("isLogin", true);
            TokenRegister.getINSTANCE().put(session.getId(), token);

            filterChain.doFilter(request, response);
            return;
        }

        // 跳转至sso认证中心 sso-server-url-with-system-url
        response.sendRedirect(ssoUrl + "/login?service=" + requestUrl + "&uuid=" + UUID.randomUUID());
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
