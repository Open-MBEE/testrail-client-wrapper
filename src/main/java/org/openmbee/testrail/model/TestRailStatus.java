package org.openmbee.testrail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Getter
@Setter
@Accessors(chain = true)
public class TestRailStatus {
    @JsonProperty("color_bright")
    private Long colorBright;
    @JsonProperty("color_dark")
    private Long colorDark;
    @JsonProperty("color_medium")
    private Long colorMedium;
    @JsonProperty("is_final")
    private Boolean isFinal;
    @JsonProperty("is_system")
    private Boolean isSystem;
    @JsonProperty("is_untested")
    private Boolean isUntested;
    private Integer id;
    private String name;
    private String label;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
