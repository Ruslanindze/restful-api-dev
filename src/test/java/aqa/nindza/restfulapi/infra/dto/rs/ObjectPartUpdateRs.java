package aqa.nindza.restfulapi.infra.dto.rs;

import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
public class ObjectPartUpdateRs {
    public String id;
    public String name;
    public Map<String, Object> data;
    public Instant updatedAt;
}
