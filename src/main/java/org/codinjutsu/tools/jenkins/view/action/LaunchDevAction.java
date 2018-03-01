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
import com.tuenti.api.FlowApi;
import com.tuenti.api.JobRequest;
import org.codinjutsu.tools.jenkins.JenkinsSettings;
import org.codinjutsu.tools.jenkins.util.GuiUtil;

import javax.swing.*;

public class LaunchDevAction extends AnAction implements DumbAware {


    private static final Icon SETTINGS_ICON = GuiUtil.loadIcon("settings.png");

    public LaunchDevAction() {
        super("Launch Dev", "Edit the Jenkins settings for the current project", SETTINGS_ICON);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {

        JobRequest jobRequest = new JobRequest(
                "apps/github-migration",
                "apps/github-migration",
                "ANDROID-6133",
                "dev");

        Project project = event.getProject();
        int returnCode = Messages.showOkCancelDialog(project, jobRequest.toString(),"Launch Dev?", SETTINGS_ICON);
        if (returnCode == Messages.OK) {
            FlowApi api = new FlowApi();
            JenkinsSettings jenkinsSettings = JenkinsSettings.getSafeInstance(project);
            api.launchJob(jenkinsSettings.getUsername(), jenkinsSettings.getPassword(), jobRequest)
                    .thenAccept(response -> Messages.showMessageDialog(project, "Launch Succeeded!\n" + response.getJobName(), "Result", SETTINGS_ICON))
                    .exceptionally(exception -> {
                        Messages.showMessageDialog(project, "Launch Failed!", "Result", SETTINGS_ICON);
                        return null;
                    });
        }
    }
}
