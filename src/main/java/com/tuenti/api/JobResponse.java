package com.tuenti.api;

import com.google.gson.annotations.SerializedName;

public class JobResponse {

    @SerializedName("branch")
    private String branch;

    @SerializedName("changeset")
    private String changeset;

    @SerializedName("workflow")
    private String workflow;

    @SerializedName("issue")
    private String issue;

    @SerializedName("jenkins_url")
    private String jenkinsUrl;

    @SerializedName("user")
    private String user;

    @SerializedName("name")
    private String jobName;

    public String getBranch() {
        return branch;
    }

    public String getChangeset() {
        return changeset;
    }

    public String getWorkflow() {
        return workflow;
    }

    public String getIssue() {
        return issue;
    }

    public String getJenkinsUrl() {
        return jenkinsUrl;
    }

    public String getUser() {
        return user;
    }

    public String getJobName() {
        return jobName;
    }
}
