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
@JsonIgnoreProperties({"custom_steps_separated"})
public class TestRailTest {
    @JsonProperty("run_id")
    private Integer runId;
    @JsonProperty("case_id")
    private Integer caseId;
    private String title;
    @JsonProperty("assignedto_id")
    private String assignedToId;
    @JsonProperty("custom_expected")
    private String customExpected;
    @JsonProperty("custom_preconds")
    private String customPreconds;
    @JsonProperty("custom_steps")
    private String customSteps;
    private String estimate;
    @JsonProperty("estimate_forecast")
    private String estimateForecast;
    private Integer id;
    @JsonProperty("milestone_id")
    private Integer milestoneId;
    @JsonProperty("priority_id")
    private Integer priorityId;
    private String refs;
    @JsonProperty("status_id")
    private Integer statusId;
    @JsonProperty("template_id")
    private Integer templateId;
    @JsonProperty("type_id")
    private Integer typeId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
