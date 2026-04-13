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

        stage('Stop Old Container') {
            steps {
                sh 'docker rm -f order-service-container || true'
            }
        }

        stage('Run Container') {
            steps {
                sh 'docker run -d -p 8081:8081 --name order-service-container order-service'
            }
        }
    }
}