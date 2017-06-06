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
@JsonIgnoreProperties({"entries", "runs"})
public class TestRailPlan {
    @JsonProperty("assignedto_id")
    private String assignedToId;
    @JsonProperty("completed_on")
    private Long completedOn;
    @JsonProperty("blocked_count")
    private Integer blockedCount;
    @JsonProperty("created_by")
    private Integer createdBy;
    @JsonProperty("created_on")
    private Long createdOn;
    @JsonProperty("custom_status1_count")
    private Integer customStatus1Count;
    @JsonProperty("custom_status2_count")
    private Integer customStatus2Count;
    @JsonProperty("custom_status3_count")
    private Integer customStatus3Count;
    @JsonProperty("custom_status4_count")
    private Integer customStatus4Count;
    @JsonProperty("custom_status5_count")
    private Integer customStatus5Count;
    @JsonProperty("custom_status6_count")
    private Integer customStatus6Count;
    @JsonProperty("custom_status7_count")
    private Integer customStatus7Count;
    private String description;
    @JsonProperty("failed_count")
    private Integer failedCount;
    private Integer id;
    @JsonProperty("is_completed")
    private Boolean isCompleted;
    private String name;
    @JsonProperty("milestone_id")
    private Integer milestoneId;
    @JsonProperty("passed_count")
    private Integer passedCount;
    @JsonProperty("retest_count")
    private Integer retestCount;
    @JsonProperty("untested_count")
    private Integer untestedCount;
    @JsonProperty("project_id")
    private Integer projectId;
    private String url;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
