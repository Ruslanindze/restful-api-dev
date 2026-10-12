package aqa.nindza.restfulapi.infra.dto.rs;

import lombok.Data;

import java.util.Map;

@Data
public class ObjectRs {
    public Long id;
    public String name;
    public Map<String, Object> data;
}
