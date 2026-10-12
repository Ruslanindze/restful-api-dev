package aqa.nindza.restfulapi;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableFeignClients("aqa.nindza.restfulapi.infra.clients")
public class TestConfig {
}
