// imports
import hudson.plugins.git.*;
import jenkins.model.Jenkins
import hudson.model.ListView
import groovy.json.JsonSlurper

parent = Jenkins.instance
def sharedLibrariesUrl = "https://github.com/naturalett/jenkins-shared-library.git"

scm = new GitSCM(sharedLibrariesUrl)
scm.userRemoteConfigs = scm.createRepoList(sharedLibrariesUrl, null)
scm.branches = [new BranchSpec("*/main")];

def projectNames = [
  "Artifact",
  "Build-And-Test",
  "Clone",
  "Deployment",
  "Full-Pipeline",
  "Junit-Test",
  "Monitoring",
  "Parallelism",
  "VCS-Any-Agent",
  "VCS-Docker-Agent"
]

for (projectName in projectNames) {
  flowDefinition = new org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition(scm, "Jenkins/pipelines/${projectName}.groovy")
  myJob = hudson.model.Hudson.instance.getJob(projectName)
  println("${projectName} is being created")
  job = new org.jenkinsci.plugins.workflow.job.WorkflowJob(parent, projectName)
  job.definition = flowDefinition
  parent.reload()
}
