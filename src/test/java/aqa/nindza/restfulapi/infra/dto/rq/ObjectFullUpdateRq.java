package aqa.nindza.restfulapi.infra.dto.rq;

import lombok.Data;

import java.util.Map;

@Data
public class ObjectFullUpdateRq {
    public String name;
    public Map<String, Object> data;
}
