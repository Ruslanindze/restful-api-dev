package aqa.nindza.restfulapi.tests.open;

import aqa.nindza.restfulapi.BaseTest;
import aqa.nindza.restfulapi.utils.ResourceReader;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static aqa.nindza.restfulapi.infra.config.TestTags.*;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DisplayName("GET /object/{id}")
@Tag(REGRESS)
public class GetObjectTest extends BaseTest {

    @Nested
    @Tag(POSITIVE)
    class Positive {
        @ParameterizedTest
        @CsvSource(
                {
                        "1, data/out/get_object_1.json",
                        "13, data/out/get_object_13.json"
                }
        )
        @Tag(SMOKE)
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
    @Tag(NEGATIVE)
    class Negative {
        @ParameterizedTest(name = "Объект не найден | {0}")
        @ValueSource(strings = "404")
        void shouldReturnObjectByIdNotFound(String id) {

            assertThatThrownBy(() -> restfulApiPublicClient.getObject(id))
                    .as("Ответ на [GET /objects] должен быть 404!")
                    .isInstanceOf(FeignException.NotFound.class);
        }

        @ParameterizedTest(name = "Плохой id при поиске объекта | {0}")
        @ValueSource(strings = "sidorov")
        void shouldReturnObjectByIdBadRequest(String id) {

            assertThatThrownBy(() -> restfulApiPublicClient.getObject(id))
                    .as("Ответ на [GET /objects] должен быть bad request!")
                    .isInstanceOf(FeignException.BadRequest.class);
        }
    }
}
