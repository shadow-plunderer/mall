pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "mall"
        IMAGE_VERSION = "v1.0"
        DOCKER_REPO = "crpi-p4k1w5usimcreyfz-vpc.cn-beijing.personal.cr.aliyuncs.com"
        DOCKER_USERNAME = "hunt-angel"
        DOCKER_PASSWORD = credentials('aliyunimage@')
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/hunt-angel/mall.git'
            }
        }

        stage('Build Services') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    def services = ['product-service', 'order-service', 'inventory_service', 'user-service', 'gateway-service']

                    for (service in services) {
                        sh "docker build -t ${DOCKER_REGISTRY}/${service}:${IMAGE_VERSION} ${service}"
                    }
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    def services = ['product-service', 'order-service', 'inventory_service', 'user-service', 'gateway-service']

                    sh "echo ${DOCKER_PASSWORD} | docker login --username=${DOCKER_USERNAME} --password-stdin ${DOCKER_REPO}"

                    for (service in services) {
                        sh "docker tag ${DOCKER_REGISTRY}/${service}:${IMAGE_VERSION} ${DOCKER_REPO}/${DOCKER_REGISTRY}/${service}:${IMAGE_VERSION}"
                        sh "docker push ${DOCKER_REPO}/${DOCKER_REGISTRY}/${service}:${IMAGE_VERSION}"
                    }
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }
    }
}