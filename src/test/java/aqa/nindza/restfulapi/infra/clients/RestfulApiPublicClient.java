package aqa.nindza.restfulapi.infra.clients;

import aqa.nindza.restfulapi.infra.dto.rs.ObjectRs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "restful-api-service", url = "${external.api.url}")
public interface RestfulApiPublicClient {

    @GetMapping("/objects")
    List<Object> getObjects(@RequestParam("id") List<String> ids);

    @GetMapping("/objects/{id}")
    ObjectRs getObject(@PathVariable("id") String id);

    @PostMapping(value = "/objects", consumes = "application/json")
    Object postObject(@RequestBody Object request);

    @PutMapping(value = "/objects/{id}", consumes = "application/json")
    Object putObject(@PathVariable("id") String id, @RequestBody Object request);

    @PutMapping(value = "/objects/{id}", consumes = "application/json")
    Object patchObject(@PathVariable("id") String id, @RequestBody Object request);

    @DeleteMapping("/objects/{id}")
    Object deleteObject(@PathVariable("id") String id);
}
