import groovy.transform.Field
@Field String customImage, applicationDir = "Application", dockerHubOwner = "naturalett"

pipeline {
    agent {
        docker {
            image 'docker:19.03.12'
            args '-v /var/run/docker.sock:/var/run/docker.sock -v /var/workshop-creds:/home'
        }
    }
    stages {
        stage('Clone') {
            steps {
                deleteDir()

                git branch: 'main', url: 'https://github.com/naturalett/jenkins.git'
            }
        }
        stage('Build') {
            steps {
              script {
                dir(applicationDir) {
                  customImage = docker.build("${dockerHubOwner}/hello-world:${env.BUILD_ID}")
                }
              }
            }
        }
        stage('Artifact') {
            steps {
              script {
                sh '/bin/sh /var/workshop-creds/docker_login.sh'
                customImage.push()
                customImage.push('latest')
              }
            }
        }
    }
    post {
        success {
            script {
                currentBuild.description = "Passed successfully!"
            }
        }
    }
}