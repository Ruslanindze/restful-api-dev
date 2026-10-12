package aqa.nindza.restfulapi.infra.clients;

import aqa.nindza.restfulapi.infra.config.SecureFeignConfig;
import aqa.nindza.restfulapi.infra.dto.secure.rs.CollectionRs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "restful-api-secret",
        url = "${external.api.url}",
        configuration = SecureFeignConfig.class
)
public interface RestfulApiSecretClient {

    @GetMapping("/collections")
    List<CollectionRs> getCollections(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "auth-type", required = false) String authType
    );
}
