package com.tuenti.api;

import com.google.gson.annotations.SerializedName;

public class JobRequest {

    @SerializedName("repo_path")
    private final String repoPath;

    @SerializedName("branch")
    private final String branch;

    @SerializedName("changeset")
    private final String changeset;

    @SerializedName("issue")
    private final String issue;

    @SerializedName("workflow")
    private final String workflow;

    public JobRequest(String repoPath, String branch, String changeset, String issue, String workflow) {
        this.repoPath = repoPath;
        this.branch = branch;
        this.changeset = changeset;
        this.issue = issue;
        this.workflow = workflow;
    }
}
