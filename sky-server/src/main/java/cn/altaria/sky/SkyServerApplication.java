package cn.altaria.sky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Hello world!
 *
 * @author xuzhou
 */
@EnableCaching
@SpringBootApplication
public class SkyServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkyServerApplication.class, args);
    }
}
