pipeline {
    agent any

    tools {
        maven 'Maven-3.9.9'   // Global Tool Configuration me jo name diya tha
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ApproachableAnkur/spring-boot-cicd.git'
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
               script {
                    def imageName = "approachableguptadocker/spring-cicd"
                    def buildTag = "${env.BUILD_NUMBER}"   // Jenkins build number tag
                    bat "docker build -t ${imageName}:latest -t ${imageName}:${buildTag} ."
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat "docker login -u %DOCKER_USER% -p %DOCKER_PASS%"
                    bat 'docker push approachableguptadocker/spring-cicd:latest'
                    bat "docker push approachableguptadocker/spring-cicd:${env.BUILD_NUMBER}"
                }
            }
        }

        stage('Deploy Container with k8s') {
            steps {
                script {
                  //  kubernetesDeploy (configs: 'deploymentservice.yaml',kubeconfigId: 'k8sconfigpwd')
                    kubernetesDeploy (configs: 'deploymentservice.yaml', kubeconfigId: 'k8sconfigpwd')
                    // def imageName = "approachableguptadocker/spring-cicd"
                    // // Purana container hata de (agar hai to)
                    // bat 'docker rm -f spring-cicd-app || echo "No old container"'
                    // // Naya container chalaye
                    // bat "docker run -d -p 7070:9090 --name spring-cicd-app ${imageName}:latest"
                }
            }
        }


    }
}
