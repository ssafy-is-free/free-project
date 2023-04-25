pipeline {
    agent any
    stages {
        stage("Set Variable") {
            steps {
                script {
                    IMAGE_NAME = "sh80165/free-springboot"
                    IMAGE_STORAGE = "https://registry.hub.docker.com"
                    IMAGE_STORAGE_CREDENTIAL = "docker-hub"
                    // SSH_CONNECTION = "ubuntu@j8b206.p.ssafy.io"
                    // SSH_CONNECTION_CREDENTIAL = "deploy-ec2-ssh"
                    SPRING_BUILD_PATH = "./backend/"
                    APPLICATION_YML_PATH = "/var/jenkins_home/workspace"
                    CONTAINER_NAME = "free-api"
                    PROJECT_DIR = "spring-boot/backend/"
                    DOCKER_FILE_PATH = "backend/"
                }
            }
        }
        stage("Clean Build Test") {
            steps {
                dir("${SPRING_BUILD_PATH}"){
                    sh "pwd"
//                     sh "chmod +x gradlew"
//                     sh "./gradlew clean build -x test"
//                     sh "ls -al ./build"
                }
            }
        }
//         stage("Copy Application.yml"){
//             steps{
//                 dir("${APPLICATION_YML_PATH}"){
//                     sh "pwd"
//                     sh "cp -r -f resources ${PROJECT_DIR}"
//                 }
//             }
//         }
//
//         stage("Build Container Image") {
//             steps {
//                 dir("${DOCKER_FILE_PATH}"){
//                     script {
//                         image = docker.build("${IMAGE_NAME}")
//                     }
//
//                 }
//
//             }
//         }
//
//         stage("Push Container Image") {
//             steps {
//                 script {
//
//                     docker.withRegistry("", "${IMAGE_STORAGE_CREDENTIAL}") {
//                         image.push("latest")
//                     }
//                 }
//             }
//         }
//
//         stage("Server Run") {
//             steps {
//                 script {
//
//                     // //컨테이너 확인 후 정지
//                     sh "docker ps -f name=${CONTAINER_NAME} -q | xargs --no-run-if-empty docker container stop"
//
//                     // //컨테이너 삭제
//                     sh "docker container ls -a -f name=${CONTAINER_NAME} -q | xargs -r docker container rm"
//
//                     //기존 이미지 삭제
//                     sh "docker images sh80165/manamana-springboot -q | xargs -r docker rmi -f"
//
//                     //컨테이너 확인
//                     sh "docker ps -a"
//
//                     // 이미지 확인
//                     sh "docker images"
//
//                     // 최신 이미지 RUN
//                     sh "docker run -d --name ${CONTAINER_NAME} --link recommend-api -p 8080:8080 ${IMAGE_NAME}:latest"
//
//                     // 컨테이너 확인 - 로그 확인용
//                     sh "docker ps -a"
//                 }
//             }
//         }
    }
}