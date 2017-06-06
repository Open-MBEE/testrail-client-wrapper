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
@JsonIgnoreProperties({"milestones"})
public class TestRailMilestone {
    @JsonProperty("completed_on")
    private Long completedOn;
    private String description;
    @JsonProperty("due_on")
    private Long dueOn;
    private Integer id;
    @JsonProperty("is_started")
    private Boolean isStarted;
    @JsonProperty("is_completed")
    private Boolean isCompleted;
    private String name;
    @JsonProperty("parent_id")
    private Integer parentId;
    @JsonProperty("project_id")
    private Integer projectId;
    @JsonProperty("start_on")
    private Long startOn;
    @JsonProperty("started_on")
    private Long startedOn;
    private String url;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
