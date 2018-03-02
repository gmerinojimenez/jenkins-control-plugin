/*
 * Copyright (c) 2013 David Boissier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codinjutsu.tools.jenkins.view.action;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.tuenti.api.FlowApi;
import com.tuenti.api.GitApi;
import com.tuenti.api.JobRequest;
import org.codinjutsu.tools.jenkins.JenkinsSettings;
import org.codinjutsu.tools.jenkins.logic.Jira;
import org.codinjutsu.tools.jenkins.util.GuiUtil;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LaunchJobAction extends AnAction implements DumbAware {

    // static singleton of thread-safe Pattern object
    private static final Pattern FIRST_LINE_PATTERN = Pattern.compile("(ref: )?(.+)[\\r\\n]*");
    private static final Icon SETTINGS_ICON = GuiUtil.loadIcon("settings.png");
    private final GitApi gitApi = new GitApi();

    private final String jobType;

    public LaunchJobAction(String text, String description, Icon icon, String jobType) {
        super(text, description, icon);
        this.jobType = jobType;
    }

    @Override
    public void actionPerformed(AnActionEvent event) {

        Project project = event.getProject();
        JenkinsSettings jenkinsSettings = JenkinsSettings.getSafeInstance(project);

        Jira jira = new Jira();
        jira.doStuff(jenkinsSettings.getUsername(), jenkinsSettings.getPassword());

        JobRequest jobRequest = new JobRequest(
                gitApi.tryToGetBranch(project),
                gitApi.tryToGetChangeset(project),
                "ANDROID-6133",
                jobType);

        int returnCode = Messages.showOkCancelDialog(project, jobRequest.toString(),"Launch?", SETTINGS_ICON);
        if (returnCode == Messages.OK) {
            FlowApi api = new FlowApi();
            api.launchJob(jenkinsSettings.getUsername(), jenkinsSettings.getPassword(), jobRequest)
                    .thenAccept(response -> Messages.showMessageDialog(project, "Launch Succeeded!\n" + response.getJobName(), "Result", SETTINGS_ICON))
                    .exceptionally(exception -> {
                        Messages.showMessageDialog(project, "Launch Failed!", "Result", SETTINGS_ICON);
                        return null;
                    });
        }
    }
}