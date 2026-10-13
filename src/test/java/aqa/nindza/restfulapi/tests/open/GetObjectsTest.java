package aqa.nindza.restfulapi.tests.open;

import aqa.nindza.restfulapi.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GET /objects")
public class GetObjectsTest extends BaseTest {

    @Nested
    class Positive {
        @DisplayName("Получение всех объектов без фильтрации")
        @Test
        void getObjectsWithoutIdFilterReturnsAllObjects() {
            var actualRs = restfulApiPublicClient.getObjects(null);

            assertThat(actualRs)
                    .as("Ответ на [GET /objects] не должен быть пустой!")
                    .isNotNull();
            assertThat(actualRs)
                    .allSatisfy(o -> {
                        assertThat(o.getId()).isNotNull();
                        assertThat(o.getName()).isNotNull();
                    });
        }

        @ParameterizedTest(name = "Получение объкетов с фильтром | {0}")
        @MethodSource
        void getObjectsWithIdFilterReturnsOneObject(List<String> ids) {
            var actualRs = restfulApiPublicClient.getObjects(ids);

            assertThat(actualRs)
                    .as("Ответ на [GET /objects?id...] не должен быть пустой!")
                    .isNotNull();
            assertThat(actualRs)
                    .allSatisfy(o -> {
                        assertThat(o.getId()).isNotNull();
                        assertThat(o.getName()).isNotNull();
                    });

            // Проверяем, что вернулись именно те объекты по порядку
            assertThat(actualRs.get(0).getId()).isEqualTo(ids.get(0));
            assertThat(actualRs.get(1).getId()).isEqualTo(ids.get(1));
            assertThat(actualRs.get(2).getId()).isEqualTo(ids.get(2));
        }

        static Stream<List<String>> getObjectsWithIdFilterReturnsOneObject() {
            return Stream.of(List.of("2", "6", "12"));
        }
    }

}
