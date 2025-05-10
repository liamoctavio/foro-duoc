package demo1.demo1.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.*;

public class CorsConfigTest {

    @Test
    void testCorsConfigurerBeanSeCreaCorrectamente() {
        CorsConfig config = new CorsConfig();
        WebMvcConfigurer bean = config.corsConfigurer();

        assertNotNull(bean);
    }

}
