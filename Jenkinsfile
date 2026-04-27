pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build + Test') {
            steps {
                bat 'mvn -B clean test'
            }
        }

        stage('Package') {
            steps {
                bat 'mvn -B package'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t pipeline-demo .'
            }
        }

        stage('Docker Run - Normal Logging Demo') {
            steps {
                bat 'docker run --rm pipeline-demo'
            }
        }
    }
}