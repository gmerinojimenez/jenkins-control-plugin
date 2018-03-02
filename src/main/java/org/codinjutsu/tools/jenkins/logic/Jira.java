package org.codinjutsu.tools.jenkins.logic;

import com.atlassian.jira.rest.client.AuthenticationHandler;
import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

import java.net.URI;
import java.net.URISyntaxException;

public class Jira {

    //TODO https://ecosystem.atlassian.net/wiki/spaces/JRJC/pages/27164680/Tutorial
    public void doStuff(String user, String password) {
        try {
            AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            URI jiraServerUri;


            jiraServerUri = new URI("https://jira.tuenti.io/jira");

            //TODO user and password
            AuthenticationHandler myAuthenticationHandler = new BasicHttpAuthenticationHandler(user, password);

            JiraRestClient restClient = factory.create(jiraServerUri, myAuthenticationHandler);

//            final NullProgressMonitor pm = new NullProgressMonitor();
            final Promise<Issue> issue = restClient.getIssueClient().getIssue("ANDROID-6134");

            issue.done(i -> i.getAssignee());

//            System.out.println(issue);

//            // now let's vote for it
//            restClient.getIssueClient().vote(issue.getVotesUri(), pm);
//
//            // now let's watch it
//            restClient.getIssueClient().watch(issue.getWatchers().getSelf(), pm);
//
//            // now let's start progress on this issue
//            final Iterable<Transition> transitions = restClient.getIssueClient().getTransitions(issue.getTransitionsUri(), pm);
//            final Transition startProgressTransition = getTransitionByName(transitions, "Start Progress");
//            restClient.getIssueClient().transition(issue.getTransitionsUri(), new TransitionInput(startProgressTransition.getId()), pm);
//
//            // and now let's resolve it as Incomplete
//            final Transition resolveIssueTransition = getTransitionByName(transitions, "Resolve Issue");
//            Collection<FieldInput> fieldInputs = Arrays.asList(new FieldInput("resolution", "Incomplete"));
//            final TransitionInput transitionInput = new TransitionInput(resolveIssueTransition.getId(), fieldInputs, Comment.valueOf("My comment"));
//            restClient.getIssueClient().transition(issue.getTransitionsUri(), transitionInput, pm);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


}
