package aqa.nindza.restfulapi.tests.open;

import aqa.nindza.restfulapi.BaseTest;
import aqa.nindza.restfulapi.infra.dto.open.rq.ObjectCreateRq;
import aqa.nindza.restfulapi.utils.ResourceReader;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("POST /objects")
public class PostObjectTest extends BaseTest {

    @Nested
    class Positive {
        @ParameterizedTest
        @ValueSource(strings = "data/in/create_object.json")
        void shouldCreateObjectWhenRqIsValid(String jsonRequest) {
            // Подготовка запроса
            String requestInsTr = ResourceReader.read(jsonRequest);
            var request = mapper.readValue(requestInsTr, ObjectCreateRq.class);

            // Сам запрос, также запомним время для проверки
            var before = Instant.now();
            var actualRs = restfulApiPublicClient.postObject(request);
            var after = Instant.now();

            // Проверка, что ответ на пост-запрос не пустой и есть id
            assertThat(actualRs)
                    .as("Ответ на [POST /objects] не должен быть пустой!")
                    .isNotNull();
            assertThat(actualRs.getId()).isNotNull();

            // Проверка ответа с запросом.
            assertThat(actualRs.getName()).isEqualTo(request.getName());
            assertThat(actualRs.getData()).isEqualTo(request.getData());

            // Проверка времени создания, что по времени тоже сходимся
            assertThat(actualRs.getCreatedAt()).isNotNull();
            assertThat(actualRs.getCreatedAt())
                    .isAfter(before)
                    .isBefore(after);
        }
    }

    @Nested
    class Negative {
        @Test
        void shouldBadRqWhenNameNull() {
            var request = ObjectCreateRq.builder()
                    .withName(null)
                    .withData(Map.of("year", 2030, "price", 1000000))
                    .build();

            assertThatThrownBy(() -> restfulApiPublicClient.postObject(request))
                    .as("Ответ на [POST /objects] должен быть bad request!")
                    .isInstanceOf(FeignException.BadRequest.class);
        }

        @Test
        void shouldConflictWhenObjectAlreadyExists() {
            var request = ObjectCreateRq.builder()
                    .withName("Apple MacBook Pro 666")
                    .withData(Map.of("year", 2026, "price", 300000.00))
                    .build();

            assertThatThrownBy(() -> restfulApiPublicClient.postObject(request))
                    .as("Ответ на [POST /objects] должен быть conflict!")
                    .isInstanceOf(FeignException.Conflict.class);
        }
    }
}
