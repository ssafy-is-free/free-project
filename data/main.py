# 필요한 라이브러리 임포트
import requests
from bs4 import BeautifulSoup
from fastapi import FastAPI

app = FastAPI()

url = 'https://www.acmicpc.net/user/sodamito2'


# 루트 경로에 대한 정보를 반환하는 기본 GET 요청 엔드포인트
@app.get("/")
def read_root():
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
    }
    response = requests.get(url, headers=headers)
    if response.status_code == 200:
        html = response.text
        soup = BeautifulSoup(html, 'html.parser')
        img_tag = soup.find("img", class_="solvedac-tier")
        print("{}".format(img_tag['src']))

    else:
        print(response.status_code)
    return {"message": "hi"}

# /items/{item_id} 경로에 대한 정보를 반환하는 GET 요청 엔드포인트


@app.get("/items/{item_id}")
def read_item(item_id: int, q: str = None):
    return {"item_id": item_id, "q": q}
