package aqa.nindza.restfulapi.infra.dto.open.rs;

import lombok.Data;

import java.util.Map;

@Data
public class ObjectRs {
    public String id;
    public String name;
    public Map<String, Object> data;
}
