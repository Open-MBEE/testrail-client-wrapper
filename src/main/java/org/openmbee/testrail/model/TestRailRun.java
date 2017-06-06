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
public class TestRailRun {
    @JsonProperty("assignedto_id")
    private Integer assignedToId;
    @JsonProperty("blocked_count")
    private Integer blockedCount;
    @JsonProperty("case_ids")
    private List<Integer> caseIds;
    @JsonProperty("completed_on")
    private Long completedOn;
    private String config;
    @JsonProperty("config_ids")
    private List<Integer> configIds;
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
    @JsonProperty("entry_id")
    private String entryId;
    @JsonProperty("entry_index")
    private Integer entryIndex;
    @JsonProperty("failed_count")
    private Integer failedCount;
    private Integer id;
    @JsonProperty("include_all")
    private Boolean includeAll;
    @JsonProperty("is_completed")
    private Boolean isCompleted;
    @JsonProperty("milestone_id")
    private Integer milestoneId;
    private String name;
    @JsonProperty("passed_count")
    private Integer passedCount;
    @JsonProperty("plan_id")
    private Integer planId;
    @JsonProperty("project_id")
    private Integer projectId;
    @JsonProperty("retest_count")
    private Integer retestCount;
    @JsonProperty("suite_id")
    private Integer suiteId;
    @JsonProperty("untested_count")
    private Integer untestedCount;
    private String url;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
