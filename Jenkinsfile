pipeline {
    agent any

    stages {

        stage("Set Variable") {
            steps {
                script {
                    IMAGE_NAME_FE = "sh80165/product-free-react"
                    IMAGE_NAME_BE = "sh80165/product-free-springboot"
                    IMAGE_NAME_DATA = "sh80165/product-free-data"
                    IMAGE_STORAGE = "https://registry.hub.docker.com"
                    IMAGE_STORAGE_CREDENTIAL = "docker-hub"
                    SSH_CONNECTION = "${env.SSH_CONNECTION}"
                    SSH_CONNECTION_CREDENTIAL = "product-server-ssh-credential"
                    APPLICATION_YML_PATH = "/var/jenkins_home/workspace"
                    CONTAINER_NAME_FE = "product_FE"
                    CONTAINER_NAME_BE = "product_BE"
                    CONTAINER_NAME_DATA = "product_data"
                    PROJECT_DIR_FE = "frontend/"
                    PROJECT_DIR_BE = "backend/"
                    PROJECT_DIR_DATA = "data/"
                    PRODUCT_DOMAIN = "ubuntu@k8b1021.p.ssafy.io"
                }
            }
        }
        //scp를 이용해서 docker compose, script 파일 전송.
        stage("Send file") {
            parallel{
                stage("docker compose yml"){
                    steps {
                        script {
                                sh "scp docker-compose.yml container-start.sh ${PRODUCT_DOMAIN}:~"
                            }
                        }
                    }
                }
                stage("script"){
                    steps {
                        script {
                                sh "pwd"
                            }
                        }
                    }
                }
                
            }
            
        }
    
        //ssh를 이용해서 이미지 pull
        // stage("Pull Container Image") {
        //     steps {
        //         script {
        //             docker.withRegistry("${IMAGE_STORAGE}", "docker-hub") {
        //                 image.push("${env.BUILD_NUMBER}")
        //                 image.push("latest")
        //                 image
        //             }
        //         }
        //     }
        // }

        // //ssh를 이용해서 docker compose up -d 
        // stage("Server send") {
        //     steps {
        //         sshagent([SSH_CONNECTION_CREDENTIAL]) {
        //             // 최신 컨테이너 삭제
        //             sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker rm -f ${CONTAINER_NAME}'"
        //             // 최신 이미지 삭제
        //             sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker rmi -f ${IMAGE_NAME}:latest'"
        //             // 최신 이미지 PULL
        //             sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker pull ${IMAGE_NAME}:latest'"
        //             // 이미지 확인
        //             sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker images'"
        //             // 최신 이미지 RUN
        //             sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker run -d --name ${CONTAINER_NAME} -p 8080:8080 ${IMAGE_NAME}:latest'"
        //             // 컨테이너 확인
        //             sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker ps -a'"
        //         }   
        //     }
        // }

        // stage("Server Run") {

        // }
    }
}