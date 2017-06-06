package org.openmbee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Getter
@RequiredArgsConstructor
public class RestEndpoint {
    private final String path;
    private final String method;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
