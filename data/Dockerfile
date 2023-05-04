# 기본 이미지 선택 (Python 3.9 버전 사용)
FROM python:3.9

# 작업 디렉터리 설정
WORKDIR /usr/bin

# 의존성 설치
RUN apt-get -y update
RUN apt install wget
RUN apt install unzip
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt -y install ./google-chrome-stable_current_amd64.deb
RUN wget -O /tmp/chromedriver.zip http://chromedriver.storage.googleapis.com/` curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE`/chromedriver_linux64.zip
RUN mkdir chrome
RUN unzip /tmp/chromedriver.zip chromedriver -d /usr/bin/chrome

WORKDIR /app
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# 소스 코드 복사
COPY . .
RUN cat main.py

# 애플리케이션 실행
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]
