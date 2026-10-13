package aqa.nindza.restfulapi.infra.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public abstract class BaseFeignConfig {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Logger feignLogger() {
        return new AllureFeignLogger();
    }
}
