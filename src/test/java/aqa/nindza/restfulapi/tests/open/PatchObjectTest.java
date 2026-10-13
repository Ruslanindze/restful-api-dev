package aqa.nindza.restfulapi.tests.open;

import aqa.nindza.restfulapi.BaseTest;
import aqa.nindza.restfulapi.infra.dto.open.rq.ObjectPartUpdateRq;
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

@DisplayName("PATCH /objects/{id}")
@Tag(REGRESS)
public class PatchObjectTest extends BaseTest {

    @Nested
    @Tag(POSITIVE)
    class Positive {
        @ParameterizedTest
        @CsvSource({
                "700, data/in/patch_object.json"
        })
        void shouldUpdateOnlyTransmittedFieldsWhenPartialRequest(String id, String jsonRequest) {
            String requestInsTr = ResourceReader.read(jsonRequest);
            var request = mapper.readValue(requestInsTr, ObjectPartUpdateRq.class);

            var before = Instant.now();
            var actualRs = restfulApiPublicClient.patchObject(id, request);
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

        @Test
        void shouldReturn200WhenRequestBodyIsEmpty() {
            String id = "600";
            var request = ObjectPartUpdateRq.builder()
                    .withName(null)
                    .withData(null)
                    .build();

            var actualRs = restfulApiPublicClient.patchObject(id, request);

            assertThat(actualRs).isNotNull();
            assertThat(actualRs.getId()).isEqualTo(id);
        }
    }

    @Nested
    @Tag(NEGATIVE)
    class Negative {
        @Test
        void shouldReturn404WhenObjectDoesNotExist() {
            String id = "404";
            var request = ObjectPartUpdateRq.builder()
                    .withName("Part update name")
                    .build();

            assertThatThrownBy(() -> restfulApiPublicClient.patchObject(id, request))
                    .as("Ответ на [PATCH /objects/999] должен быть not found!")
                    .isInstanceOf(FeignException.NotFound.class);
        }

        @Test
        void shouldReturn400WhenFieldValidationFails() {
            String id = "500";
            var request = ObjectPartUpdateRq.builder()
                    .withData(Map.of("price", -50))
                    .build();

            assertThatThrownBy(() -> restfulApiPublicClient.patchObject(id, request))
                    .as("Ответ на [PATCH /objects/777] должен быть bad request при невалидном значении!")
                    .isInstanceOf(FeignException.BadRequest.class);
        }

        @Test
        void shouldReturn403WhenAttemptingToUpdateReadOnlyField() {
            String id = "300";
            var request = ObjectPartUpdateRq.builder()
                    .withName("Updated Name")
                    .withData(Map.of("id", "123"))
                    .build();

            assertThatThrownBy(() -> restfulApiPublicClient.patchObject(id, request))
                    .as("Ответ на [PATCH /objects/777] должен быть forbidden при попытке изменить readonly поле!")
                    .isInstanceOf(FeignException.Forbidden.class);
        }
    }
}
