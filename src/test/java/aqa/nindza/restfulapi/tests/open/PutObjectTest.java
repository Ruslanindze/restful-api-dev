package aqa.nindza.restfulapi.tests.open;

import aqa.nindza.restfulapi.BaseTest;
import aqa.nindza.restfulapi.infra.dto.open.rq.ObjectFullUpdateRq;
import aqa.nindza.restfulapi.utils.ResourceReader;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Instant;
import java.util.Map;

import static aqa.nindza.restfulapi.infra.config.TestTags.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("PUT /objects/{id}")
@Tag(REGRESS)
public class PutObjectTest extends BaseTest {

    @Nested
    @Tag(POSITIVE)
    class Positive {
        @ParameterizedTest
        @CsvSource({
                "888, data/in/update_object.json"
        })
        void shouldUpdateAllFieldsWhenValidFullRequest(String id, String jsonRequest) {
            String requestInsTr = ResourceReader.read(jsonRequest);
            var request = mapper.readValue(requestInsTr, ObjectFullUpdateRq.class);
            request.setId(id);

            var before = Instant.now();
            var actualRs = restfulApiPublicClient.putObject(id, request);
            var after = Instant.now();

            assertThat(actualRs).isNotNull();
            assertThat(actualRs.getId()).isEqualTo(id);
            assertThat(actualRs.getName()).isEqualTo(request.getName());
            assertThat(actualRs.getData()).isEqualTo(request.getData());
            assertThat(actualRs.getUpdatedAt()).isNotNull();
            assertThat(actualRs.getUpdatedAt())
                    .isAfter(before)
                    .isBefore(after);
        }
    }

    @Nested
    @Tag(NEGATIVE)
    class Negative {
        @Test
        void shouldReturn404WhenObjectDoesNotExist() {
            String id = "999";
            var request = ObjectFullUpdateRq.builder()
                    .withName("Updated Name")
                    .withData(Map.of("year", 2027))
                    .build();

            assertThatThrownBy(() -> restfulApiPublicClient.putObject(id, request))
                    .as("Ответ на [PUT /objects/999] должен быть not found!")
                    .isInstanceOf(FeignException.NotFound.class);
        }

        @Test
        void shouldReturn400WhenIdInUrlAndBodyMismatch() {
            String id = "1";
            var request = ObjectFullUpdateRq.builder()
                    .withId("2")
                    .withName("Updated Name")
                    .withData(Map.of("year", 2027))
                    .build();

            assertThatThrownBy(() -> restfulApiPublicClient.putObject(id, request))
                    .as("Ответ на [PUT /objects/1] должен быть bad request при несовпадении ID!")
                    .isInstanceOf(FeignException.BadRequest.class);
        }

        @Test
        void shouldReturn400WhenRequiredFieldsMissing() {
            String id = "1";
            var request = ObjectFullUpdateRq.builder()
                    .withName(null)
                    .withData(Map.of("year", 2027))
                    .build();

            assertThatThrownBy(() -> restfulApiPublicClient.putObject(id, request))
                    .as("Ответ на [PUT /objects/1] должен быть bad request при отсутствии обязательных полей!")
                    .isInstanceOf(FeignException.BadRequest.class);
        }

    }
}
