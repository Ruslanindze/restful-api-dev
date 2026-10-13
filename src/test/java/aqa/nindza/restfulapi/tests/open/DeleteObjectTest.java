package aqa.nindza.restfulapi.tests.open;

import aqa.nindza.restfulapi.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static aqa.nindza.restfulapi.infra.config.TestTags.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DELETE /objects/{id}")
@Tag(REGRESS)
public class DeleteObjectTest extends BaseTest {

    @Nested
    @Tag(POSITIVE)
    class Positive {
        @ParameterizedTest
        @ValueSource(strings = "1001")
        @Tag(SMOKE)
        void shouldReturn200WhenObjectExists(String id) {
            var actualRs = restfulApiPublicClient.deleteObject(id);

            assertThat(actualRs).isNotNull();
            assertThat(actualRs.get("message")).isNotNull();
            assertThat(actualRs.get("message"))
                    .isEqualTo("Object with id = %s, has been deleted.".formatted(id));
        }
    }

    @Nested
    @Tag(NEGATIVE)
    class Negative {
        @ParameterizedTest
        @ValueSource(strings = "2001")
        void shouldReturn404WhenObjectDoesNotExist(String id) {
            var actualRs = restfulApiPublicClient.deleteObject(id);

            assertThat(actualRs).isNotNull();
            assertThat(actualRs.get("message")).isNotNull();
            assertThat(actualRs.get("message"))
                    .isEqualTo("Object deleted successfully");
        }
    }
}
