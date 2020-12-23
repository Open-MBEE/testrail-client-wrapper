package org.openmbee.testrail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.util.EntityUtils;
import org.openmbee.RestEndpoint;
import org.openmbee.testrail.model.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;

@Getter
@Setter
public class TestRailAPI {
    public static final RestEndpoint GET_CASE_ENDPOINT = new RestEndpoint("/api/v2/get_case/:case_id", HttpGet.METHOD_NAME),
            GET_CASES_ENDPOINT = new RestEndpoint("/api/v2/get_cases/:project_id&suite_id=:suite_id&section_id=:section_id", HttpGet.METHOD_NAME),
            ADD_CASE_ENDPOINT = new RestEndpoint("/api/v2/add_case/:section_id", HttpPost.METHOD_NAME),
            UPDATE_CASE_ENDPOINT = new RestEndpoint("/api/v2/update_case/:case_id", HttpPost.METHOD_NAME),
            DELETE_CASE_ENDPOINT = new RestEndpoint("/api/v2/delete_case/:case_id", HttpPost.METHOD_NAME),

    GET_CASE_FIELDS_ENDPOINT = new RestEndpoint("/api/v2/get_case_fields", HttpGet.METHOD_NAME),

    GET_CASE_TYPES_ENDPOINT = new RestEndpoint("/api/v2/get_case_types", HttpGet.METHOD_NAME),

    GET_CONFIGS_ENDPOINT = new RestEndpoint("/api/v2/get_configs/:project_id", HttpGet.METHOD_NAME),
            ADD_CONFIG_GROUP_ENDPOINT = new RestEndpoint("/api/v2/add_config_group/:project_id", HttpPost.METHOD_NAME),
            ADD_CONFIG_ENDPOINT = new RestEndpoint("/api/v2/add_config/:config_group_id", HttpPost.METHOD_NAME),
            UPDATE_CONFIG_GROUP_ENDPOINT = new RestEndpoint("/api/v2/update_config_group/:config_group_id", HttpPost.METHOD_NAME),
            UPDATE_CONFIG_ENDPOINT = new RestEndpoint("/api/v2/update_config/:config_id", HttpPost.METHOD_NAME),
            DELETE_CONFIG_GROUP_ENDPOINT = new RestEndpoint("/api/v2/delete_config_group/:config_group_id", HttpPost.METHOD_NAME),
            DELETE_CONFIG_ENDPOINT = new RestEndpoint("/api/v2/delete_config/:config_id", HttpPost.METHOD_NAME),

    GET_MILESTONE_ENDPOINT = new RestEndpoint("/api/v2/get_milestone/:milestone_id", HttpGet.METHOD_NAME),
            GET_MILESTONES_ENDPOINT = new RestEndpoint("/api/v2/get_milestones/:project_id", HttpGet.METHOD_NAME),
            ADD_MILESTONE_ENDPOINT = new RestEndpoint("/api/v2/add_milestone/:project_id", HttpPost.METHOD_NAME),
            UPDATE_MILESTONE_ENDPOINT = new RestEndpoint("/api/v2/update_milestone/:milestone_id", HttpPost.METHOD_NAME),
            DELETE_MILESTONE_ENDPOINT = new RestEndpoint("/api/v2/delete_milestone/:milestone_id", HttpPost.METHOD_NAME),

    GET_PLAN_ENDPOINT = new RestEndpoint("/api/v2/get_plan/:plan_id", HttpGet.METHOD_NAME),
            GET_PLANS_ENDPOINT = new RestEndpoint("/api/v2/get_plans/:project_id", HttpGet.METHOD_NAME),
            ADD_PLAN_ENDPOINT = new RestEndpoint("/api/v2/add_plan/:project_id", HttpPost.METHOD_NAME),
            ADD_PLAN_ENTRY_ENDPOINT = new RestEndpoint("/api/v2/add_plan_entry/:plan_id", HttpPost.METHOD_NAME),
            UPDATE_PLAN_ENDPOINT = new RestEndpoint("/api/v2/update_plan/:plan_id", HttpPost.METHOD_NAME),
            UPDATE_PLAN_ENTRY_ENDPOINT = new RestEndpoint("/api/v2/update_plan_entry/:plan_id/:entry_id", HttpPost.METHOD_NAME),
            CLOSE_PLAN_ENDPOINT = new RestEndpoint("/api/v2/close_plan/:plan_id", HttpPost.METHOD_NAME),
            DELETE_PLAN_ENDPOINT = new RestEndpoint("/api/v2/delete_plan/:plan_id", HttpPost.METHOD_NAME),
            DELETE_PLAN_ENTRY_ENDPOINT = new RestEndpoint("/api/v2/delete_plan_entry/:plan_id/:entry_id", HttpPost.METHOD_NAME),

    GET_PRIORITIES_ENDPOINT = new RestEndpoint("/api/v2/get_priorities", HttpGet.METHOD_NAME),

    GET_PROJECT_ENDPOINT = new RestEndpoint("/api/v2/get_project/:project_id", HttpGet.METHOD_NAME),
            GET_PROJECTS_ENDPOINT = new RestEndpoint("/api/v2/get_projects", HttpGet.METHOD_NAME),
            ADD_PROJECT_ENDPOINT = new RestEndpoint("/api/v2/add_project", HttpPost.METHOD_NAME),
            UPDATE_PROJECT_ENDPOINT = new RestEndpoint("/api/v2/update_project/:project_id", HttpPost.METHOD_NAME),
            DELETE_PROJECT_ENDPOINT = new RestEndpoint("/api/v2/delete_project/:project_id", HttpPost.METHOD_NAME),

    GET_RESULTS_ENDPOINT = new RestEndpoint("/api/v2/get_results/:test_id", HttpGet.METHOD_NAME),
            GET_RESULTS_FOR_CASE_ENDPOINT = new RestEndpoint("/api/v2/get_results_for_case/:run_id/:case_id", HttpGet.METHOD_NAME),
            GET_RESULTS_FOR_RUN_ENDPOINT = new RestEndpoint("/api/v2/get_results_for_run/:run_id", HttpGet.METHOD_NAME),
            ADD_RESULT_ENDPOINT = new RestEndpoint("/api/v2/add_result/:test_id", HttpPost.METHOD_NAME),
            ADD_RESULT_FOR_CASE_ENDPOINT = new RestEndpoint("/api/v2/add_result_for_case/:run_id/:case_id", HttpPost.METHOD_NAME),
            ADD_RESULTS_ENDPOINT = new RestEndpoint("/api/v2/add_results/:run_id", HttpPost.METHOD_NAME),
            ADD_RESULTS_FOR_CASES_ENDPOINT = new RestEndpoint("/add_results_for_cases/:run_id", HttpPost.METHOD_NAME),

    GET_RESULT_FIELDS_ENDPOINT = new RestEndpoint("/api/v2/get_result_fields", HttpGet.METHOD_NAME),

    GET_RUN_ENDPOINT = new RestEndpoint("/api/v2/get_run/:run_id", HttpGet.METHOD_NAME),
            GET_RUNS_ENDPOINT = new RestEndpoint("/api/v2/get_runs/:project_id", HttpGet.METHOD_NAME),
            ADD_RUN_ENDPOINT = new RestEndpoint("/api/v2/add_run/:project_id", HttpPost.METHOD_NAME),
            UPDATE_RUN_ENDPOINT = new RestEndpoint("/api/v2/update_run/:run_id", HttpPost.METHOD_NAME),
            CLOSE_RUN_ENDPOINT = new RestEndpoint("/api/v2/close_run/:run_id", HttpPost.METHOD_NAME),
            DELETE_RUN_ENDPOINT = new RestEndpoint("/api/v2/delete_run/:run_id", HttpPost.METHOD_NAME),

    GET_SECTION_ENDPOINT = new RestEndpoint("/api/v2/get_section/:section_id", HttpGet.METHOD_NAME),
            GET_SECTIONS_ENDPOINT = new RestEndpoint("/api/v2/get_sections/:project_id&suite_id=:suite_id", HttpGet.METHOD_NAME),
            ADD_SECTION_ENDPOINT = new RestEndpoint("/api/v2/add_section/:project_id", HttpPost.METHOD_NAME),
            UPDATE_SECTION_ENDPOINT = new RestEndpoint("/api/v2/update_section/:section_id", HttpPost.METHOD_NAME),
            DELETE_SECTION_ENDPOINT = new RestEndpoint("/api/v2/delete_section/:section_id", HttpPost.METHOD_NAME),

    GET_STATUSES_ENDPOINT = new RestEndpoint("/api/v2/get_statuses", HttpGet.METHOD_NAME),

    GET_SUITE_ENDPOINT = new RestEndpoint("/api/v2/get_suite/:suite_id", HttpGet.METHOD_NAME),
            GET_SUITES_ENDPOINT = new RestEndpoint("/api/v2/get_suites/:project_id", HttpGet.METHOD_NAME),
            ADD_SUITE_ENDPOINT = new RestEndpoint("/api/v2/add_suite/:project_id", HttpPost.METHOD_NAME),
            UPDATE_SUITE_ENDPOINT = new RestEndpoint("/api/v2/update_suite/:suite_id", HttpPost.METHOD_NAME),
            DELETE_SUITE_ENDPOINT = new RestEndpoint("/api/v2/delete_suite/:suite_id", HttpPost.METHOD_NAME),

    GET_TEMPLATES_ENDPOINT = new RestEndpoint("/api/v2/get_templates/:project_id", HttpGet.METHOD_NAME),

    GET_TEST_ENDPOINT = new RestEndpoint("/api/v2/get_test/:test_id", HttpGet.METHOD_NAME),
            GET_TESTS_ENDPOINT = new RestEndpoint("/api/v2/get_tests/:run_id", HttpGet.METHOD_NAME),

    GET_USER_ENDPOINT = new RestEndpoint("/api/v2/get_user/:user_id", HttpGet.METHOD_NAME),
            GET_CURRENT_USER_ENDPOINT = new RestEndpoint("/api/v2/get_current_user", HttpGet.METHOD_NAME),
            GET_USER_BY_EMAIL_ENDPOINT = new RestEndpoint("/api/v2/get_user_by_email&email=:email", HttpGet.METHOD_NAME),
            GET_USERS_ENDPOINT = new RestEndpoint("/api/v2/get_users/:project_id", HttpGet.METHOD_NAME);

    private final URI uri;
    private final String username;
    @Getter(AccessLevel.NONE)
    private final CredentialsProvider credentialsProvider;
    @Getter(AccessLevel.NONE)
    private HttpHost host;
    @Getter(AccessLevel.NONE)
    private final HttpClientContext context;

    @Getter(AccessLevel.PROTECTED)
    private ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public TestRailAPI(URI uri, String username, String apiKey) {
        this.uri = uri;
        String h = uri.getHost();
        String scheme = uri.getScheme();
        int port = uri.getPort();
        if (port < 0) {
            switch (h) {
                case "http":
                    port = 80;
                    break;
                case "https":
                    port = 443;
                    break;
            }
        }
        this.host = new HttpHost(h, port, scheme);
        this.username = username;
        this.credentialsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, apiKey);
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);
        AuthCache authCache = new BasicAuthCache();
        authCache.put(this.host, new BasicScheme());
        context = HttpClientContext.create();
        context.setCredentialsProvider(credentialsProvider);
        context.setAuthCache(authCache);
    }

    public TestRailCase getCase(int caseId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":case_id", Integer.toString(caseId));
        return request(GET_CASE_ENDPOINT, parameters, TestRailCase.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailCase> getCases(int projectId) throws URISyntaxException, IOException {
        return getCases(projectId, -1, -1);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailCase> getCases(int projectId, int suiteId) throws URISyntaxException, IOException {
        return getCases(projectId, suiteId, -1);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailCase> getCases(int projectId, int suiteId, int sectionId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(projectId));
        parameters.put((suiteId > 0 ? "" : "&suite_id=") + ":suite_id", suiteId > 0 ? Integer.toString(suiteId) : null);
        parameters.put((sectionId > 0 ? "" : "&section_id=") + ":section_id", sectionId > 0 ? Integer.toString(sectionId) : null);
        return request(GET_CASES_ENDPOINT, parameters, List.class, TestRailCase.class);
    }

    public TestRailCase addCase(TestRailCase caze) throws URISyntaxException, IOException {
        assertThat(caze.getTitle(), not(emptyOrNullString()));
        assertThat(caze.getSectionId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":section_id", Integer.toString(caze.getSectionId()));
        return request(ADD_CASE_ENDPOINT, parameters, mapper.writeValueAsBytes(caze), TestRailCase.class);
    }

    public TestRailCase updateCase(TestRailCase caze) throws URISyntaxException, IOException {
        assertThat(caze.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":case_id", Integer.toString(caze.getId()));
        return request(UPDATE_CASE_ENDPOINT, parameters, mapper.writeValueAsBytes(caze), TestRailCase.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailCaseField> getCaseFields() throws URISyntaxException, IOException {
        return request(GET_CASE_FIELDS_ENDPOINT, List.class, TestRailCaseField.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailCaseType> getCaseTypes() throws URISyntaxException, IOException {
        return request(GET_CASE_TYPES_ENDPOINT, List.class, TestRailCaseType.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailConfigurationGroup> getConfigurationGroups(int projectId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(projectId));
        return request(GET_CONFIGS_ENDPOINT, parameters, List.class, TestRailConfigurationGroup.class);
    }

    public TestRailConfigurationGroup addConfigurationGroup(TestRailConfigurationGroup configurationGroup) throws URISyntaxException, IOException {
        assertThat(configurationGroup.getProjectId(), not(0));
        assertThat(configurationGroup.getName(), not(emptyOrNullString()));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(configurationGroup.getProjectId()));
        return request(ADD_CONFIG_GROUP_ENDPOINT, parameters, mapper.writeValueAsBytes(configurationGroup), TestRailConfigurationGroup.class);
    }

    public TestRailConfiguration addConfiguration(TestRailConfiguration configuration) throws URISyntaxException, IOException {
        assertThat(configuration.getGroupId(), not(0));
        assertThat(configuration.getName(), not(emptyOrNullString()));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":config_group_id", Integer.toString(configuration.getGroupId()));
        return request(ADD_CONFIG_ENDPOINT, parameters, mapper.writeValueAsBytes(configuration), TestRailConfiguration.class);
    }

    public TestRailConfigurationGroup updateConfigurationGroup(TestRailConfigurationGroup configurationGroup) throws URISyntaxException, IOException {
        assertThat(configurationGroup.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":config_group_id", Integer.toString(configurationGroup.getId()));
        return request(UPDATE_CONFIG_GROUP_ENDPOINT, parameters, mapper.writeValueAsBytes(configurationGroup), TestRailConfigurationGroup.class);
    }

    public TestRailConfiguration updateConfiguration(TestRailConfiguration configuration) throws URISyntaxException, IOException {
        assertThat(configuration.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":config_id", Integer.toString(configuration.getId()));
        return request(UPDATE_CONFIG_ENDPOINT, parameters, mapper.writeValueAsBytes(configuration), TestRailConfiguration.class);
    }

    public void deleteConfigurationGroup(TestRailConfigurationGroup configurationGroup) throws URISyntaxException, IOException {
        assertThat(configurationGroup.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":config_group_id", Integer.toString(configurationGroup.getId()));
        request(DELETE_CONFIG_GROUP_ENDPOINT, parameters, Void.class);
    }

    public void deleteConfiguration(TestRailConfiguration configuration) throws URISyntaxException, IOException {
        assertThat(configuration.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":config_id", Integer.toString(configuration.getId()));
        request(DELETE_CONFIG_ENDPOINT, parameters, Void.class);
    }

    public TestRailMilestone getMilestone(int milestoneId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":milestone_id", Integer.toString(milestoneId));
        return request(GET_MILESTONE_ENDPOINT, parameters, TestRailMilestone.class);
    }

    public TestRailMilestone addMilestone(TestRailMilestone milestone) throws URISyntaxException, IOException {
        assertThat(milestone.getProjectId(), not(0));
        assertThat(milestone.getName(), not(emptyOrNullString()));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(milestone.getProjectId()));
        return request(ADD_MILESTONE_ENDPOINT, parameters, mapper.writeValueAsBytes(milestone), TestRailMilestone.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailMilestone> getMilestones(int projectId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(projectId));
        return request(GET_MILESTONES_ENDPOINT, parameters, List.class, TestRailMilestone.class);
    }

    public TestRailMilestone updateMilestone(TestRailMilestone milestone) throws URISyntaxException, IOException {
        assertThat(milestone.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":milestone_id", Integer.toString(milestone.getId()));
        return request(UPDATE_MILESTONE_ENDPOINT, parameters, mapper.writeValueAsBytes(milestone), TestRailMilestone.class);
    }

    public void deleteMilestone(TestRailMilestone milestone) throws URISyntaxException, IOException {
        assertThat(milestone.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":milestone_id", Integer.toString(milestone.getId()));
        request(DELETE_MILESTONE_ENDPOINT, parameters, Void.class);
    }

    public TestRailPlan getPlan(int planId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":plan_id", Integer.toString(planId));
        return request(GET_PLAN_ENDPOINT, parameters, TestRailPlan.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailPlan> getPlans(int projectId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(projectId));
        return request(GET_PLANS_ENDPOINT, parameters, List.class, TestRailPlan.class);
    }

    public TestRailPlan addPlan(TestRailPlan plan) throws URISyntaxException, IOException {
        assertThat(plan.getProjectId(), not(0));
        assertThat(plan.getName(), not(emptyOrNullString()));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(plan.getProjectId()));
        return request(ADD_PLAN_ENDPOINT, parameters, mapper.writeValueAsBytes(plan), TestRailPlan.class);
    }

    public TestRailPlanEntry addPlanEntry(TestRailPlanEntry planEntry, int planId) throws URISyntaxException, IOException {
        assertThat(planEntry.getSuiteId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":plan_id", Integer.toString(planId));
        return request(ADD_PLAN_ENTRY_ENDPOINT, parameters, mapper.writeValueAsBytes(planEntry), TestRailPlanEntry.class);
    }

    public TestRailPlan updatePlan(TestRailPlan plan) throws URISyntaxException, IOException {
        assertThat(plan.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":plan_id", Integer.toString(plan.getId()));
        return request(UPDATE_PLAN_ENDPOINT, parameters, mapper.writeValueAsBytes(plan), TestRailPlan.class);
    }

    public TestRailPlanEntry updatePlanEntry(TestRailPlanEntry planEntry, int planId) throws URISyntaxException, IOException {
        assertThat(planEntry.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":plan_id", Integer.toString(planId));
        parameters.put(":entry_id", planEntry.getId());
        return request(UPDATE_PLAN_ENTRY_ENDPOINT, parameters, mapper.writeValueAsBytes(planEntry), TestRailPlanEntry.class);
    }

    public TestRailPlan closePlan(TestRailPlan plan) throws URISyntaxException, IOException {
        assertThat(plan.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":plan_id", Integer.toString(plan.getId()));
        return request(CLOSE_PLAN_ENDPOINT, parameters, TestRailPlan.class);
    }

    public void deletePlan(TestRailPlan plan) throws URISyntaxException, IOException {
        assertThat(plan.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":plan_id", Integer.toString(plan.getId()));
        request(DELETE_PLAN_ENDPOINT, parameters, Void.class);
    }

    public void deletePlanEntry(TestRailPlanEntry planEntry, int planId) throws URISyntaxException, IOException {
        assertThat(planEntry.getId(), not(emptyOrNullString()));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":plan_id", Integer.toString(planId));
        parameters.put(":entry_id", planEntry.getId());
        request(DELETE_PLAN_ENTRY_ENDPOINT, parameters, Void.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailPriority> getPriorities() throws URISyntaxException, IOException {
        return request(GET_PRIORITIES_ENDPOINT, List.class, TestRailPriority.class);
    }

    public TestRailProject getProject(int projectId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(projectId));
        return request(GET_PROJECT_ENDPOINT, parameters, TestRailProject.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailProject> getProjects() throws URISyntaxException, IOException {
        return request(GET_PROJECTS_ENDPOINT, List.class, TestRailProject.class);
    }

    public TestRailProject addProject(TestRailProject project) throws URISyntaxException, IOException {
        return request(ADD_PROJECT_ENDPOINT, mapper.writeValueAsBytes(project), TestRailProject.class);
    }

    public TestRailProject updateProject(TestRailProject project) throws URISyntaxException, IOException {
        assertThat(project.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(project.getId()));
        return request(UPDATE_PROJECT_ENDPOINT, parameters, mapper.writeValueAsBytes(project), TestRailProject.class);
    }

    public void deleteProject(TestRailProject project) throws URISyntaxException, IOException {
        assertThat(project.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(project.getId()));
        request(DELETE_PROJECT_ENDPOINT, parameters, Void.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailResult> getResults(int testId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":test_id", Integer.toString(testId));
        return request(GET_RESULTS_ENDPOINT, parameters, List.class, TestRailResult.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailResult> getResultsForCase(int runId, int caseId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":run_id", Integer.toString(runId));
        parameters.put(":case_id", Integer.toString(caseId));
        return request(GET_RESULTS_FOR_CASE_ENDPOINT, parameters, List.class, TestRailResult.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailResult> getResultsForRun(int runId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":run_id", Integer.toString(runId));
        return request(GET_RESULTS_FOR_RUN_ENDPOINT, parameters, List.class, TestRailResult.class);
    }

    public TestRailResult addResult(TestRailResult result) throws URISyntaxException, IOException {
        assertThat(result.getTestId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":test_id", Integer.toString(result.getTestId()));
        return request(ADD_RESULT_ENDPOINT, parameters, mapper.writeValueAsBytes(result), TestRailResult.class);
    }

    public TestRailResult addResultForCase(TestRailResult result, int runId, int caseId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":run_id", Integer.toString(runId));
        parameters.put(":case_id", Integer.toString(caseId));
        return request(ADD_RESULT_FOR_CASE_ENDPOINT, parameters, mapper.writeValueAsBytes(result), TestRailResult.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailResult> addResults(Collection<TestRailResult> results, int runId) throws URISyntaxException, IOException {
        results.forEach(result -> assertThat(result.getTestId(), not(0)));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":run_id", Integer.toString(runId));
        ObjectNode resultsWrapper = mapper.createObjectNode();
        resultsWrapper.putPOJO("results", results);
        return request(ADD_RESULTS_ENDPOINT, parameters, mapper.writeValueAsBytes(resultsWrapper), List.class, TestRailResult.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailResult> addResultsForCases(Map<TestRailResult, Integer> resultCaseIdMap, int runId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":run_id", Integer.toString(runId));
        ObjectNode resultsWrapper = mapper.createObjectNode();
        resultsWrapper.putArray("results").addAll(resultCaseIdMap.entrySet().stream().map(entry -> {
            ObjectNode resultObjectNode = mapper.valueToTree(entry.getKey());
            resultObjectNode.put("case_id", entry.getValue());
            return resultObjectNode;
        }).collect(Collectors.toList()));
        return request(ADD_RESULTS_FOR_CASES_ENDPOINT, parameters, mapper.writeValueAsBytes(resultsWrapper), List.class, TestRailResult.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailResultField> getResultFields() throws URISyntaxException, IOException {
        return request(GET_RESULT_FIELDS_ENDPOINT, List.class, TestRailResultField.class);
    }

    public TestRailRun getRun(int runId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":run_id", Integer.toString(runId));
        return request(GET_RUN_ENDPOINT, parameters, TestRailRun.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailRun> getRuns(int projectId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(projectId));
        return request(GET_RUNS_ENDPOINT, parameters, List.class, TestRailRun.class);
    }

    public TestRailRun addRun(TestRailRun run) throws URISyntaxException, IOException {
        assertThat(run.getProjectId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(run.getProjectId()));
        return request(ADD_RUN_ENDPOINT, parameters, mapper.writeValueAsBytes(run), TestRailRun.class);
    }

    public TestRailRun updateRun(TestRailRun run) throws URISyntaxException, IOException {
        assertThat(run.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":run_id", Integer.toString(run.getId()));
        return request(UPDATE_RUN_ENDPOINT, parameters, mapper.writeValueAsBytes(run), TestRailRun.class);
    }

    public TestRailRun closeRun(TestRailRun run) throws URISyntaxException, IOException {
        assertThat(run.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":run_id", Integer.toString(run.getId()));
        return request(CLOSE_RUN_ENDPOINT, parameters, TestRailRun.class);
    }

    public void deleteRun(TestRailRun run) throws URISyntaxException, IOException {
        assertThat(run.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":run_id", Integer.toString(run.getId()));
        request(DELETE_RUN_ENDPOINT, parameters, Void.class);
    }

    public TestRailSection getSection(int sectionId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":section_id", Integer.toString(sectionId));
        return request(GET_SECTION_ENDPOINT, parameters, TestRailSection.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailSection> getSections(int projectId, int suiteId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(projectId));
        parameters.put(":suite_id", Integer.toString(suiteId));
        return request(GET_SECTIONS_ENDPOINT, parameters, List.class, TestRailSection.class);
    }

    public TestRailSection addSection(TestRailSection section, int projectId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(projectId));
        return request(ADD_SECTION_ENDPOINT, parameters, mapper.writeValueAsBytes(section), TestRailSection.class);
    }

    public TestRailSection updateSection(TestRailSection section) throws URISyntaxException, IOException {
        assertThat(section.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":section_id", Integer.toString(section.getId()));
        return request(UPDATE_SECTION_ENDPOINT, parameters, mapper.writeValueAsBytes(section), TestRailSection.class);
    }

    public void deleteSection(TestRailSection section) throws URISyntaxException, IOException {
        assertThat(section.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":section_id", Integer.toString(section.getId()));
        request(DELETE_SECTION_ENDPOINT, parameters, Void.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailStatus> getStatuses() throws URISyntaxException, IOException {
        return request(GET_STATUSES_ENDPOINT, List.class, TestRailStatus.class);
    }

    public TestRailSuite getSuite(int suiteId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":suite_id", Integer.toString(suiteId));
        return request(GET_SUITE_ENDPOINT, parameters, TestRailSuite.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailSuite> getSuites(int projectId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(projectId));
        return request(GET_SUITES_ENDPOINT, parameters, List.class, TestRailSuite.class);
    }

    public TestRailSuite addSuite(TestRailSuite suite) throws URISyntaxException, IOException {
        assertThat(suite.getProjectId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(suite.getProjectId()));
        return request(ADD_SUITE_ENDPOINT, parameters, mapper.writeValueAsBytes(suite), TestRailSuite.class);
    }

    public TestRailSuite updateSuite(TestRailSuite suite) throws URISyntaxException, IOException {
        assertThat(suite.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":suite_id", Integer.toString(suite.getId()));
        return request(UPDATE_SUITE_ENDPOINT, parameters, mapper.writeValueAsBytes(suite), TestRailSuite.class);
    }

    public void deleteSuite(TestRailSuite suite) throws URISyntaxException, IOException {
        assertThat(suite.getId(), not(0));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":suite_id", Integer.toString(suite.getId()));
        request(DELETE_SUITE_ENDPOINT, parameters, Void.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailTemplate> getTemplates(int projectId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(projectId));
        return request(GET_TEMPLATES_ENDPOINT, parameters, List.class, TestRailTemplate.class);
    }

    public TestRailTest getTest(int testId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":test_id", Integer.toString(testId));
        return request(GET_TEST_ENDPOINT, parameters, TestRailTest.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailTest> getTests(int runId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":run_id", Integer.toString(runId));
        return request(GET_TESTS_ENDPOINT, parameters, List.class, TestRailTest.class);
    }

    public TestRailUser getUser(int userId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":user_id", Integer.toString(userId));
        return request(GET_USER_ENDPOINT, parameters, TestRailUser.class);
    }

    public TestRailUser getCurrentUser() throws URISyntaxException, IOException {
        return request(GET_CURRENT_USER_ENDPOINT, TestRailUser.class);
    }

    public TestRailUser getUserByEmail(String email) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":email", email);
        return request(GET_USER_BY_EMAIL_ENDPOINT, parameters, TestRailUser.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailUser> getUsers() throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", "");
        return request(GET_USERS_ENDPOINT, parameters, List.class, TestRailUser.class);
    }

    @SuppressWarnings("unchecked")
    public List<TestRailUser> getUsers(int projectId) throws URISyntaxException, IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(":project_id", Integer.toString(projectId));
        return request(GET_USERS_ENDPOINT, parameters, List.class, TestRailUser.class);
    }

    private <T> T request(RestEndpoint endpoint, Class<T> parametrized, Class... parametrizedClasses) throws URISyntaxException, IOException {
        return request(endpoint, null, null, parametrized, parametrizedClasses);
    }

    private <T> T request(RestEndpoint endpoint, byte[] body, Class<T> parametrized, Class... parametrizedClasses) throws URISyntaxException, IOException {
        return request(endpoint, null, body, parametrized, parametrizedClasses);
    }

    private <T> T request(RestEndpoint endpoint, Map<String, String> parameters, Class<T> parametrized, Class... parametrizedClasses) throws URISyntaxException, IOException {
        return request(endpoint, parameters, null, parametrized, parametrizedClasses);
    }

    private <T> T request(RestEndpoint endpoint, Map<String, String> parameters, byte[] body, Class<T> parametrized, Class... parametrizedClasses) throws URISyntaxException, IOException {
        String path = parameters != null ? replaceParameters(endpoint.getPath(), parameters) : endpoint.getPath();
        URI uri = buildUri(path);
        return executeRequest(body != null ? createRequest(endpoint.getMethod(), uri, body) : createRequest(endpoint.getMethod(), uri), parametrized, parametrizedClasses);
    }

    private HttpRequest createRequest(String method, URI uri) throws UnsupportedEncodingException {
        // server encodes (bad practice), so if we do it double encodes :(
        // have to undo default behavior to mitigate
        String url = URLDecoder.decode(uri.toString(), CharEncoding.UTF_8);
        return new BasicHttpRequest(method, url);
    }

    private HttpRequest createRequest(String method, URI uri, byte[] body) throws UnsupportedEncodingException {
        String url = URLDecoder.decode(uri.toString(), CharEncoding.UTF_8);
        HttpEntity entity = EntityBuilder.create().setBinary(body).setContentEncoding(CharEncoding.UTF_8).setContentType(ContentType.APPLICATION_JSON).build();
        HttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest(method, url);
        request.setEntity(entity);
        return request;
    }

    private URI buildUri(String path) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(uri);
        uriBuilder.addParameter(path, null);
        return uriBuilder.build();
    }

    private String replaceParameters(String target, Map<String, String> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            target = target.replaceAll((String) entry.getKey(), entry.getValue() != null ? (String) entry.getValue() : "");
        }
        return target;
    }

    protected CloseableHttpClient createHttpClient() {
        return HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
    }

    private <T> T executeRequest(HttpRequest request, Class<T> parametrized, Class... parametrizedClasses) throws IOException {
        request.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        try (CloseableHttpClient httpClient = createHttpClient();
             CloseableHttpResponse response = httpClient.execute(host, request, context)) {
            if (response.getStatusLine().getStatusCode() / 100 != 2) {
                throw new HttpResponseException(response.getStatusLine().getStatusCode(), "Expected a 2xx HTTP response, but got a " + response.getStatusLine().getStatusCode() + " response. Body: " + EntityUtils.toString(response.getEntity()));
            }
            String s = EntityUtils.toString(response.getEntity());
            if (parametrized != null && !Void.class.equals(parametrized)) {
                JavaType javaType = mapper.getTypeFactory().constructParametricType(parametrized, parametrizedClasses);
                return mapper.readValue(s, javaType);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
