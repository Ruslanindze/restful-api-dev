package aqa.nindza.restfulapi.infra.dto.open.rq;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObjectPartUpdateRq {
    public String name;
    public Map<String, Object> data;
}
