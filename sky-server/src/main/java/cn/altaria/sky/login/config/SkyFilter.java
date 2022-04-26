package cn.altaria.sky.login.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.altaria.sky.login.CookieUtil;
import cn.altaria.sky.login.service.ILoginService;
import cn.altaria.sky.login.util.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;


/**
 * SkyFilter
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/19 13:37
 */
@Slf4j
public class SkyFilter implements Filter {

    private ILoginService loginService;

    /**
     * Called by the web container to indicate to a filter that it is being placed into
     * service. The servlet container calls the init method exactly once after instantiating the
     * filter. The init method must complete successfully before the filter is asked to do any
     * filtering work. <br><br>
     * <p>
     * The web container cannot place the filter into service if the init method either<br>
     * 1.Throws a ServletException <br>
     * 2.Does not return within a time period defined by the web container
     *
     * @param filterConfig 过滤器配置
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * The <code>doFilter</code> method of the Filter is called by the container
     * each time a request/response pair is passed through the chain due
     * to a client request for a resource at the end of the chain. The FilterChain passed in to this
     * method allows the Filter to pass on the request and response to the next entity in the
     * chain.<p>
     * A typical implementation of this method would follow the following pattern:- <br>
     * 1. Examine the request<br>
     * 2. Optionally wrap the request object with a custom implementation to
     * filter content or headers for input filtering <br>
     * 3. Optionally wrap the response object with a custom implementation to
     * filter content or headers for output filtering <br>
     * 4. a) <strong>Either</strong> invoke the next entity in the chain using the FilterChain object (<code>chain.doFilter()</code>), <br>
     * * 4. b) <strong>or</strong> not pass on the request/response pair to the next entity in the filter chain to block the request processing<br>
     * * 5. Directly set headers on the response after invocation of the next entity in the filter chain.
     *
     * @param servletRequest 请求
     * @param servletResponse 响应
     * @param chain 过滤器链
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        // 支持跨越
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", " Origin, X-Requested-With, Content-Type, Accept");

        StringBuffer requestUrl = request.getRequestURL();
        log.info("【SSO登录】requestUrl:{}", requestUrl);

        if (loginService == null) {
            this.loginService = SpringBeanUtils.getBean(ILoginService.class);
        }

        if (requestUrl.indexOf("/verify") > -1) {
            chain.doFilter(request, response);
            return;
        }

        if (requestUrl.indexOf("/logout") > -1) {
            chain.doFilter(request, response);
            return;
        }

        if (requestUrl.indexOf("/sso-logout") > -1) {
            session.invalidate();
            return;
        }

        Object isLogin = session.getAttribute("isLogin");
        if (null != isLogin && (Boolean) isLogin) {
            log.info("【SSO登录】登录成功");
            chain.doFilter(request, response);
            return;
        } else {
            String token = request.getParameter("token");
            if (token == null) {
                token = CookieUtil.getCookie("token", request);
            }
            if (token != null) {
                String serviceUrl = request.getParameter("service");

                if (serviceUrl == null) {
                    serviceUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                            request.getContextPath() + "/";
                }
                // sso-server-verify-url
                boolean verifyResult = Boolean.parseBoolean(this.loginService.verify(request, serviceUrl, token, session.getId()));
                if (verifyResult) {
                    log.info("【SSO登录】登录令牌校验通过，登录成功");
                    session.setAttribute("isLogin", true);
                    session.setAttribute("token", token);

                    response.sendRedirect(serviceUrl);
                    return;
                }
            }
        }

        if (requestUrl.indexOf("/login") > -1) {
            chain.doFilter(request, response);
            return;
        }

        // 跳转至登录页面
        response.sendRedirect("/login");
    }

    /**
     * Called by the web container to indicate to a filter that it is being taken out of service. This
     * method is only called once all threads within the filter's doFilter method have exited or after
     * a timeout period has passed. After the web container calls this method, it will not call the
     * doFilter method again on this instance of the filter. <br><br>
     * <p>
     * This method gives the filter an opportunity to clean up any resources that are being held (for
     * example, memory, file handles, threads) and make sure that any persistent state is synchronized
     * with the filter's current state in memory.
     */
    @Override
    public void destroy() {

    }
}
