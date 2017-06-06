package org.openmbee.testrail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties({"configs"})
public class TestRailCaseField {
    private String description;
    @JsonProperty("display_order")
    private Integer displayOrder;
    private Integer id;
    @JsonProperty("include_all")
    private Boolean includeAll;
    @JsonProperty("is_active")
    private Boolean isActive;
    private String label;
    private String name;
    @JsonProperty("system_name")
    private String systemName;
    @JsonProperty("template_ids")
    private List<Integer> templateIds;
    @JsonProperty("type_id")
    private Integer typeId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
