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
public class TestRailCase {
    @JsonProperty("created_by")
    private Integer createdBy;
    @JsonProperty("created_on")
    private Long createdOn;
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
    @JsonProperty("section_id")
    private Integer sectionId;
    @JsonProperty("suite_id")
    private Integer suiteId;
    @JsonProperty("template_id")
    private Integer templateId;
    private String title;
    @JsonProperty("type_id")
    private Integer typeId;
    @JsonProperty("updated_by")
    private Integer updatedBy;
    @JsonProperty("updated_on")
    private Long updatedOn;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
