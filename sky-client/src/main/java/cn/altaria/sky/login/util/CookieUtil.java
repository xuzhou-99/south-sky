package cn.altaria.sky.login.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CookieUtil
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/22 10:32
 */
public class CookieUtil {

    public static String getCookie(String cookieName, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void setCookie(String cookieName, String value, int seconds, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, value);
        // TODO: domain
        cookie.setPath("/");
        cookie.setMaxAge(seconds);
        response.addCookie(cookie);
    }
}
