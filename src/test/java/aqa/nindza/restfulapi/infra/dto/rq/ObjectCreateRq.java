package aqa.nindza.restfulapi.infra.dto.rq;

import lombok.Data;

import java.util.Map;

@Data
public class ObjectCreateRq {
    public String name;
    public Map<String, Object> data;
}
