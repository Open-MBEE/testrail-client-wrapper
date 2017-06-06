package org.openmbee.testrail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Getter
@Setter
@Accessors(chain = true)
public class TestRailConfiguration {
    @JsonProperty("group_id")
    private Integer groupId;
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
