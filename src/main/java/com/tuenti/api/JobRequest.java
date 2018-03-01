package com.tuenti.api;

import com.google.gson.annotations.SerializedName;

public class JobRequest {

    @SerializedName("repo_path")
    private final String repoPath = "/android-messenger";

    @SerializedName("branch")
    private final String branch;

    @SerializedName("changeset")
    private final String changeset;

    @SerializedName("issue")
    private final String issue;

    @SerializedName("workflow")
    private final String workflow;

    public JobRequest(String branch, String changeset, String issue, String workflow) {
        this.branch = branch;
        this.changeset = changeset;
        this.issue = issue;
        this.workflow = workflow;
    }

    @Override
    public String toString() {
        return "branch --> " + branch + "\n" +
                "changeset --> " + changeset + "\n" +
                "issue --> " + issue;
    }
}
