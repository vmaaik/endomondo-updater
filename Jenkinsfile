
pipeline {
    agent any
    stages {
        stage('Start') {
            steps {
                script {
                    echo 'Starting'
                    echo 'Nothing to do!'
                }
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

    }
}