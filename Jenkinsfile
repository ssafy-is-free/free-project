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
            steps {
                sshagent([SSH_CONNECTION_CREDENTIAL]) {
                        
                        sh "scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null docker-compose.yml container-start.sh ${PRODUCT_DOMAIN}:~"
                }
            }  
        }
                
            
            // stage("Send file") {
        //         stage("docker compose yml"){
        //             steps {
        //                 sshagent([SSH_CONNECTION_CREDENTIAL]) {
                                
        //                         sh "scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null docker-compose.yml container-start.sh ${PRODUCT_DOMAIN}:~"
        //                     }
        //                 }
        //         }
                
        //         stage("script"){
        //             steps {
        //                 script {
        //                         sh "pwd"
        //                     }
        //             }
        //         }
        //     }
                
        // }
        stage("image build&push"){
            parallel{
                stage("BE"){
                    //설정파일 카피
                    steps{
                        sh "cp -r -f resources ${PROJECT_DIR_BE}"
                    }
                    //도커 이미지 빌드
                    steps{
                        dir("${PROJECT_DIR_BE}"){
                            script {
                                sh "docker build -t ${IMAGE_NAME_BE} ." 
                            }
                        }
                    }
                    //도커 허브에 푸시
                    steps{
                        script {
                            sh "docker push ${IMAGE_NAME_BE}"
                        }
                    }
                }
                stage("FE"){
                    //설정 파일 카피
                    steps{
                        sh "cp -f .env frontend/.env"
                    }
                    //도커 이미지 빌드
                    steps{
                        dir("${PROJECT_DIR_FE}"){
                            script {
                                sh "docker build -t ${IMAGE_NAME_FE} ." 
                            }
                        }
                    }
                    //도커 허브에 푸시
                    steps{
                        script {
                            sh "docker push ${IMAGE_NAME_FE}"
                        }
                    }

                }
                stage("DATA"){
                    //이미지 빌드
                    steps{
                        dir("${PROJECT_DIR_DATA}"){
                            script {
                                sh "docker build -t ${IMAGE_NAME_DATA} ." 
                            }
                        }
                    }
                    //도커 허브에 푸시
                    steps{
                        script {
                            sh "docker push ${IMAGE_NAME_DATA}"
                        }
                    }
                }
            }
        }
        
    
        // //ssh를 이용해서 실행 스크립트 실행하기
        stage("Server send") {
            steps {
                sshagent([SSH_CONNECTION_CREDENTIAL]) {
                    // 실행파일로 만들기
                    sh "ssh -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null 'chmod + x container-start.sh'"
                    //실행
                    sh "ssh -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null './container-start.sh'"
                }   
            }
        }
    }
}