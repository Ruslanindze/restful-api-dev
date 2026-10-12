package aqa.nindza.restfulapi.tests.open;

import aqa.nindza.restfulapi.BaseTest;
import aqa.nindza.restfulapi.infra.clients.RestfulApiPublicClient;
import aqa.nindza.restfulapi.utils.ResourceReader;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DisplayName("GET /objects")
public class GetObjectTest extends BaseTest {
    @Autowired
    private RestfulApiPublicClient restfulApiPublicClient;

    @Nested
    class Positive {
        @ParameterizedTest
        @CsvSource(
                {
                        "1, data/out/get_object_1.json",
                        "13, data/out/get_object_13.json"
                }
        )
        void shouldReturnObjectByIdExist(String id, String jsonExpected) {
            var actualRs = restfulApiPublicClient.getObject(id);
            String expectedRs = ResourceReader.read(jsonExpected);

            assertThat(actualRs)
                    .as("Ответ на [GET /objects] не должен быть пустой!")
                    .isNotNull();
            assertThatJson(actualRs)
                    .as("Ответ на [GET /objects] не соответствует ожидаемому!")
                    .isEqualTo(expectedRs);
        }
    }

    @Nested
    class Negative {
        @ParameterizedTest(name = "Объект не найден | {0}")
        @ValueSource(strings = "4_0_4")
        void shouldReturnObjectByIdNotFound(String id) {

            assertThatThrownBy(() -> restfulApiPublicClient.getObject(id))
                    .as("Ответ на [GET /objects] должен быть 404!")
                    .isInstanceOf(FeignException.NotFound.class);
        }

        @ParameterizedTest(name = "Плохой id при поиске объекта | {0}")
        @ValueSource(strings = "$$$")
        void shouldReturnObjectByIdBadRequest(String id) {

            assertThatThrownBy(() -> restfulApiPublicClient.getObject(id))
                    .as("Ответ на [GET /objects] должен быть 404!")
                    .isInstanceOf(FeignException.NotFound.class);
        }
    }
}
