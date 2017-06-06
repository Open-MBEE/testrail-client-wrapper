package org.openmbee.testrail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties({"configs"})
public class TestRailResultField {
    private String description;
    @JsonProperty("display_order")
    private Integer displayOrder;
    private Integer id;
    private String label;
    private String name;
    @JsonProperty("system_name")
    private String systemName;
    @JsonProperty("type_id")
    private Integer typeId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
