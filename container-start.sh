#!/bin/bash

# DOCKER_COMPOSE_FILE="your-docker-compose-file.yml"

BE_IMAGE_NAME="sh80165/product-free-springboot"
FE_IMAGE_NAME="sh80165/product-free-react"
DATA_IMAGE_NAME="sh80165/product-free-data"

# 컨테이너 실행 여부 확인
if [[ "$(sudo docker compose ps -q)" ]]; then
    echo "컨테이너가 이미 실행 중입니다. 컨테이너를 중지합니다."
    sudo docker compose down
fi

sleep 1

# 이미지 파일 삭제.
echo "기존 이미지 파일을 삭제 합니다."
sudo docker images ${BE_IMAGE_NAME} -q | xargs -r docker rmi -f
sudo docker images ${FE_IMAGE_NAME} -q | xargs -r docker rmi -f
sudo docker images ${DATA_IMAGE_NAME} -q | xargs -r docker rmi -f

sleep 1

# 이미지 pull
echo "이미지를 받아옵니다."
sudo docker pull ${BE_IMAGE_NAME}
sudo docker pull ${FE_IMAGE_NAME}
sudo docker pull ${DATA_IMAGE_NAME}



# 컨테이너 실행
echo "컨테이너를 실행합니다."
sudo docker compose up -d



