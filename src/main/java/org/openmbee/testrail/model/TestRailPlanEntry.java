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
public class TestRailPlanEntry {
    private String id;
    private String name;
    private List<TestRailRun> runs;
    @JsonProperty("suite_id")
    private Integer suiteId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
