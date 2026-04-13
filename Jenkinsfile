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

        stage('Docker Build') {
            steps {
                sh 'docker build -t order-service .'
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