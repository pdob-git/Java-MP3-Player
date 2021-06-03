pipeline {
    agent any
    tools {
        maven 'maven3'
        jdk 'java-oracle-jdk-8'
    }
    stages {
        stage('Build') {
            steps {
                echo "brach -> " + branchName()
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        success {
            echo 'Godlike Java MP3 Player App build successfully'
            mailNotification()

        }
        failure {
            echo 'Godlike Java MP3 Player App build failure'
            mailNotification()
        }

    }
}

def branchName() {
    return scm.branches[0].name
}

def mailNotification() {
    emailext subject: "App pipeline : ${env.JOB_NAME}, build status : ${currentBuild.currentResult}",
            recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
            body: "Pipeline for app : ${env.JOB_NAME}\n" +
                    "Pipeline build number : ${env.BUILD_NUMBER}\n" +
                    "Pipeline build status : ${currentBuild.currentResult} \n" +
                    "Pipeline build details at: ${env.BUILD_URL}"

}
