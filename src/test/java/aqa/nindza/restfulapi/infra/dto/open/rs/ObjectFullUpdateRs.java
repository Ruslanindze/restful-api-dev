package aqa.nindza.restfulapi.infra.dto.open.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class ObjectFullUpdateRs {
    public String id;
    public String name;
    public Map<String, Object> data;
    public Instant updatedAt;
}
