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

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(logoutUrl + "?token=" + token);
            String responseBody = httpClient.execute(httpGet, httpResponse -> {
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status < 200 || status >= 300) {
                    // ... handle unsuccessful request
                    return null;
                }
                HttpEntity entity = httpResponse.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            });
            // ... do something with response

        } catch (IOException e) {
            // ... handle IO exception
        }

    }

    /**
     * 校验令牌
     *
     * @param url   sso校验令牌请求
     * @param token 令牌
     * @return 校验结果
     */
    @Override
    public boolean verify(String url, String token) {
        // TODO http请求校验token
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url + "?token=" + token);
            String responseBody = httpClient.execute(httpGet, httpResponse -> {
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status < 200 || status >= 300) {
                    // ... handle unsuccessful request
                    return null;
                }
                HttpEntity entity = httpResponse.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            });
            // ... do something with response
            return doVerify(responseBody);
        } catch (IOException e) {
            // ... handle IO exception
        }

        return false;
    }

    private Boolean doVerify(String responseBody) {
        if (null != responseBody && !"".equals(responseBody)) {
            return Boolean.parseBoolean(responseBody);
        }
        return false;
    }
}
