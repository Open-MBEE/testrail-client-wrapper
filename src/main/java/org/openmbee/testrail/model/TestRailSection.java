package org.openmbee.testrail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Getter
@Setter
@Accessors(chain = true)
public class TestRailSection {
    private Integer depth;
    private String description;
    @JsonProperty("display_order")
    private Integer displayOrder;
    private Integer id;
    private String name;
    @JsonProperty("parent_id")
    private String parentId;
    @JsonProperty("suite_id")
    private Integer suiteId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
