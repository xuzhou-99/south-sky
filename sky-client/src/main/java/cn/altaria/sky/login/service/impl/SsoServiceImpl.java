package cn.altaria.sky.login.service.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import cn.altaria.sky.login.service.ISsoService;

/**
 * SsoServiceImpl
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/19 16:52
 */
@Service
public class SsoServiceImpl implements ISsoService {

    /**
     * 注销
     * 向sso认证中心发送注销请求
     *
     * @param logoutUrl 登出url
     * @param token     sso发布的令牌
     */
    @Override
    public void logout(String logoutUrl, String token) {
        String getUrl = logoutUrl + "?token=" + token;
        doGet(getUrl);
    }

    /**
     * 校验令牌
     *
     * @param url        sso校验令牌请求
     * @param token      令牌
     * @param serviceUrl 请求系统路径
     * @return 校验结果
     */
    @Override
    public boolean verify(String url, String serviceUrl, String token, String jsessionid) {
        String getUrl = url + "?service=" + serviceUrl + "&token=" + token + "&jsessionid=" + jsessionid;
        String responseBody = doGet(getUrl);
        if (null != responseBody && !"".equals(responseBody)) {
            return Boolean.parseBoolean(responseBody);
        }
        return false;
    }

    /**
     * get 请求
     *
     * @param url 请求路径
     * @return 请求结果
     */
    private String doGet(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            // ... do something with response
            return httpClient.execute(httpGet, httpResponse -> {
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status < 200 || status >= 300) {
                    // ... handle unsuccessful request
                    return null;
                }
                HttpEntity entity = httpResponse.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            });
        } catch (IOException e) {
            // ... handle IO exception
        }
        return "";
    }
}
