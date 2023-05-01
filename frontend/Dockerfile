# 노드 버전 지정
FROM node:18-alpine

LABEL email="lsh80165@gmail.com"

ENV APP_HOME=/usr/app

WORKDIR $APP_HOME

COPY . .

RUN yarn cache clean


RUN yarn add @mui/icons-material --network-timeout 500000

RUN yarn install

RUN npm run build
#어떤 포트에서 listen할지
EXPOSE 3000

ENTRYPOINT [ "npm","run","start" ]