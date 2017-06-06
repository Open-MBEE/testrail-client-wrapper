package org.openmbee.testrail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Getter
@Setter
@Accessors(chain = true)
public class TestRailProject {
    private String announcement;
    @JsonProperty("show_announcement")
    private Boolean showAnnouncement;
    private Integer id;
    private String name;
    @JsonProperty("is_completed")
    private Boolean isCompleted;
    @JsonProperty("completed_on")
    private Long completedOn;
    private String url;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
