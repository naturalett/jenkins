pipeline {
    agent {
        docker {
            image 'python'
            args '--name workshop'
        }
    }
  stages {
    stage('Do job stage') {
      steps {
        git branch: 'main', url: 'https://github.com/naturalett/getting-started.git'
        sh 'ls -al'
      }
    }
  }
}