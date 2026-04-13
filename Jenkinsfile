pipeline {
    agent any

    tools {
        maven 'Maven 3'
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean install -Dspring.profiles.active=h2'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test -Dspring.profiles.active=h2'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }
}