## Description:
Java wrapper for the TestRail REST API v2 (Source: http://docs.gurock.com/testrail-api2/start)

## Example:
```java
import org.openmbee.testrail.TestRailAPI;
import org.openmbee.testrail.model.TestRailProject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class TestRailExample {
    public static void main(String... args) throws URISyntaxException, IOException {
        TestRailAPI api = new TestRailAPI(new URI("http://yoursubdomain.testrail.net"), "username", "passwordOrApiKey");
        List<TestRailProject> projects = api.getProjects();
    }
}
```# testrail-client-wrapper
