package aqa.nindza.restfulapi.infra.dto.open.rq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class ObjectCreateRq {
    public String name;
    public Map<String, Object> data;
}
