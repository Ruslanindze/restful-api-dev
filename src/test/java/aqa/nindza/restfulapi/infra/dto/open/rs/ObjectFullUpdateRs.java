package aqa.nindza.restfulapi.infra.dto.open.rs;

import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
public class ObjectFullUpdateRs {
    public String id;
    public String name;
    public Map<String, Object> data;
    public Instant updatedAt;
}
