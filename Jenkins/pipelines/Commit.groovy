pipeline {
  agent {
      docker {
          image 'python'
      }
  }
  parameters {
    string defaultValue: 'main', description: 'Feature Branch', name: 'branch'
  }
  stages {
    stage('Do job stage') {
      steps {
        git branch: params.branch, url: 'https://github.com/naturalett/jenkins.git'
      }
    }
  }
}