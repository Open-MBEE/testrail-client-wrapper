package org.openmbee.testrail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Getter
@Setter
@Accessors(chain = true)
public class TestRailCaseType {
    private Integer id;
    private String name;
    @JsonProperty("is_default")
    private Boolean isDefault;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
