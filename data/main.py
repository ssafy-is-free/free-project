# 필요한 라이브러리 임포트
import requests
from bs4 import BeautifulSoup
from fastapi import FastAPI

app = FastAPI()

# 크롬 헤더 안 넣으면 인증 실패
headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
}

# 루트 경로에 대한 정보를 반환하는 기본 GET 요청 엔드포인트
@app.get("/")
def read():
    return {"message": "hi"}

# 백준 아이디를 통해 정보와 언어를 크롤링하는 API
@app.get("/api/data/{name}")
def read_backjun(name):
    
    # 백준 사용자의 정보, 언어 url
    information_url = 'https://www.acmicpc.net/user/' + name
    language_url = 'https://www.acmicpc.net/user/language/' + name
    information_response = requests.get(information_url, headers=headers)
    language_response = requests.get(language_url, headers=headers)

    # 결과 값을 반환할 객체
    result = {
        "tier" : None,
        "pass_count" : None,
        "try_fail_count" : None,
        "submit_count" : None,
        "fail_count" : None,
        "languages_result" : []
    }
    
    ## 정보 응답이 OK 일때만 정보 가져오기
    if information_response.status_code == 200:
        # response 객체로부터 웹 페이지의 HTML 소스를 가져옵니다.
        html = information_response.text
        # BeautifulSoup 객체를 생성하여 HTML 소스를 파싱합니다.
        # 파싱에는 'html.parser' 파서를 사용합니다.
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
        # json 변환
        # json_result = json.dumps(information_result)
        # print(json_result)
    # 언어 정보 응답이 200일 때만 가져오기
    if language_response.status_code == 200:
        # response 객체로부터 웹 페이지의 HTML 소스를 가져옵니다.
        html = language_response.text
        # BeautifulSoup 객체를 생성하여 HTML 소스를 파싱합니다.
        # 파싱에는 'html.parser' 파서를 사용합니다.
        soup = BeautifulSoup(html, 'html.parser')
        table = soup.find("table", class_ = "table table-bordered table-striped").find("tbody").findAll("tr")
        
        for row in table:
            # 각 tr 아래에 있는 th 태그와 td 태그들을 찾습니다.
            header = row.find("th")
            columns = row.findAll("td")
            # 만약 문자열이 비어있다면 0으로 치환합니다
            pass_count = columns[0].text.strip(
            ) if columns[0].text.strip() != "" else "0"
            # 결과 값에 저장할 배열을 생성하고 언어, 정답 비율, 맞춘 수를 저장합니다.
            language_result = {
                "language" : header.text,
                "pass_percentage" : columns[11].text,
                "pass_count": pass_count,
            }
            # 결과 배열에 language_result을 추가합니다.
            result["languages_result"].append(language_result)

    
    
    return result
