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
@JsonIgnoreProperties({"custom_step_results"})
public class TestRailResult {
    private String comment;
    @JsonProperty("created_on")
    private long createdOn;
    private String defects;
    private String elapsed;
    @JsonProperty("status_id")
    private Integer statusId;
    @JsonProperty("test_id")
    private Integer testId;
    private String version;
    @JsonProperty("assignedto_id")
    private Integer assignedToId;
    @JsonProperty("created_by")
    private Integer createdBy;
    private Integer id;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
