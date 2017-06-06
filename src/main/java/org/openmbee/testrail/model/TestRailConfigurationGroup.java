package org.openmbee.testrail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class TestRailConfigurationGroup {
    private List<TestRailConfiguration> configs;
    private Integer id;
    private String name;
    @JsonProperty("project_id")
    private Integer projectId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
