package com.tuenti.api;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitApi {

    public String tryToGetChangeset(Project project) {
        VirtualFile gitDir = project.getBaseDir().findChild(".git");
        if (gitDir != null) {
            VirtualFile gitHeadSymRefFile = gitDir.findChild("ORIG_HEAD");

            if (gitHeadSymRefFile != null) {
                try {
                    return new String(gitHeadSymRefFile.contentsToByteArray()).trim();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public String tryToGetBranch(Project project) {
        VirtualFile gitDir = project.getBaseDir().findChild(".git");
        if (gitDir != null) {
            VirtualFile gitHeadSymRefFile = gitDir.findChild("HEAD");

            if (gitHeadSymRefFile != null) {
                return determineBranchName(gitHeadSymRefFile);
            }

        }
        return "";
    }

    private static final Pattern FIRST_LINE_PATTERN = Pattern.compile("(ref: )?(.+)[\\r\\n]*");

    private String determineBranchName(VirtualFile headFile) {
        if (headFile != null) {
            try {
                String headLinkAsString = new String(headFile.contentsToByteArray());
                Matcher matcher = FIRST_LINE_PATTERN.matcher(headLinkAsString);
                if (matcher.find() && matcher.groupCount() >= 2) {
                    String branchName = matcher.group(2).trim();
                    branchName = removePrefix("refs/", branchName);
                    branchName = removePrefix("heads/", branchName);
                    return branchName;
                }
            } catch (Exception e) {
                // ignore
            }
        }
        return "";
    }

    /**
     * Utility function to tidy-up common repetition of sym-link path to current branch.
     */
    private String removePrefix(String prefix, String branchName) {
        if (branchName.startsWith(prefix) && branchName.length() > prefix.length()) {
            return branchName.substring(prefix.length());
        }
        return branchName;
    }
}
