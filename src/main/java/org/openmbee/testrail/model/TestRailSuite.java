package org.openmbee.testrail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Getter
@Setter
@Accessors(chain = true)
public class TestRailSuite {
    @JsonProperty("completed_on")
    private String completedOn;
    private String description;
    private Integer id;
    @JsonProperty("is_baseline")
    private Boolean isBaseline;
    @JsonProperty("is_completed")
    private Boolean isCompleted;
    @JsonProperty("is_master")
    private Boolean isMaster;
    private String name;
    @JsonProperty("project_id")
    private Integer projectId;
    private String url;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
