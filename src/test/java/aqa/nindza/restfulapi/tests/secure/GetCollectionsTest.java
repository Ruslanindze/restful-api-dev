package aqa.nindza.restfulapi.tests.secure;

import aqa.nindza.restfulapi.BaseTest;
import aqa.nindza.restfulapi.infra.clients.RestfulApiSecretClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("GET /collections")
public class GetCollectionsTest extends BaseTest {
    @Autowired
    private RestfulApiSecretClient restfulApiSecretClient;

    @Nested
    class Positive {
        @Test
        void shouldReturnFullCollections() {
            // Проверяем, что внутри лямбда-выражения не падает ни один Exception
            assertThatNoException().isThrownBy(() -> {
                // Твой код вызова FeignClient или любая логика теста
                restfulApiSecretClient.getCollections(null, null);
            });
        }
    }
}
