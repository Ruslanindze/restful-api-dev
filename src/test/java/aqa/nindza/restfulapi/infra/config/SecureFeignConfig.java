package aqa.nindza.restfulapi.infra.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecureFeignConfig extends BaseFeignConfig {

    // Выносим сам токен в application.yml, чтобы не хардкодить секреты в коде
    @Value("${external.api.secure-key:default_dummy_key}")
    private String secureApiKey;

    @Bean
    public RequestInterceptor apiKeyInterceptor() {
        return requestTemplate -> {
            // Перехватчик автоматически добавляет заголовок к каждому запросу клиента
            requestTemplate.header("x-api-key", secureApiKey);
        };
    }
}
