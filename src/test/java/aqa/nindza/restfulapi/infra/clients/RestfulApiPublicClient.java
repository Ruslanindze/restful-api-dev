package aqa.nindza.restfulapi.infra.clients;

import aqa.nindza.restfulapi.infra.config.FeignConfig;
import aqa.nindza.restfulapi.infra.dto.rq.ObjectCreateRq;
import aqa.nindza.restfulapi.infra.dto.rq.ObjectFullUpdateRq;
import aqa.nindza.restfulapi.infra.dto.rq.ObjectPartUpdateRq;
import aqa.nindza.restfulapi.infra.dto.rs.ObjectCreateRs;
import aqa.nindza.restfulapi.infra.dto.rs.ObjectFullUpdateRs;
import aqa.nindza.restfulapi.infra.dto.rs.ObjectPartUpdateRs;
import aqa.nindza.restfulapi.infra.dto.rs.ObjectRs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@FeignClient(
        name = "restful-api-service",
        url = "${external.api.url}",
        configuration = FeignConfig.class
)
public interface RestfulApiPublicClient {

    @GetMapping("/objects")
    List<ObjectRs> getObjects(@RequestParam("id") List<String> ids);

    @GetMapping("/objects/{id}")
    ObjectRs getObject(@PathVariable("id") String id);

    @PostMapping(value = "/objects", consumes = "application/json")
    ObjectCreateRs postObject(@RequestBody ObjectCreateRq request);

    @PutMapping(value = "/objects/{id}", consumes = "application/json")
    ObjectFullUpdateRs putObject(@PathVariable("id") String id, @RequestBody ObjectFullUpdateRq request);

    @PutMapping(value = "/objects/{id}", consumes = "application/json")
    ObjectPartUpdateRs patchObject(@PathVariable("id") String id, @RequestBody ObjectPartUpdateRq request);

    @DeleteMapping("/objects/{id}")
    Map<String, String> deleteObject(@PathVariable("id") String id);
}
