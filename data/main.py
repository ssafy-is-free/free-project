# 필요한 라이브러리 임포트
import requests
import json
from bs4 import BeautifulSoup
from fastapi import FastAPI

app = FastAPI()

# 루트 경로에 대한 정보를 반환하는 기본 GET 요청 엔드포인트
@app.get("/")
def read_backjun():
    return {"message": "hi"}

# 백준 아이디를 통해 정보와 언어를 크롤링하는 API
@app.get("/api/data/{name}")
def read_backjun(name):
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
    }
    information_url = 'https://www.acmicpc.net/user/' + name
    language_url = 'https://www.acmicpc.net/user/language/' + name
    response = requests.get(information_url, headers=headers)
    result = {
        "tier" : None,
        "pass_count" : None,
        "try_fail_count" : None,
        "submit_count" : None,
        "fail_count" : None
    }
    ## 응답이 OK 일때만 정보 가져오기
    if response.status_code == 200:
        html = response.text
        soup = BeautifulSoup(html, 'html.parser')
        # 티어 가져오기
        img_tag = soup.find("img", class_="solvedac-tier")        
        # 맞은 문제
        solved = soup.find("span", id = "u-solved")
        # 시도했지만 맞지 못한 문제
        try_failed = soup.find("span", id = "u-failed")
        # 제출 횟수
        submit = soup.find("a", href = "/status?user_id="+name)
        # 틀린 문제
        failed = soup.find("span", id = "u-result-6")

        result["tier"] = img_tag['src']
        result["pass_count"] = solved.text
        result["try_fail_count"] = try_failed.text
        result["submit_count"] = submit.text
        result["fail_count"] = failed.text

    else:
        print(response.status_code)
    return result
