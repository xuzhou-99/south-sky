package cn.altaria.sky.login.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * HttpUtil
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/4/26 20:16
 */
@Slf4j
public class HttpUtil {

    private HttpUtil() {

    }


    /**
     * http get请求
     * header添加Cookie
     *
     * @param url        请求路径
     * @param jsessionid sessionID
     */
    public static void doGet(String url, String jsessionid) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            log.info("【HTTP】Get请求 url = {}", url);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Cookie", "JSESSIONID=" + jsessionid);
            // ... do something with response
            httpClient.execute(httpGet, httpResponse -> {
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
    }
}
