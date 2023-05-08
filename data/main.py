# 필요한 라이브러리 임포트
import re
import time

import httpx
import requests
from bs4 import BeautifulSoup
from fastapi import FastAPI, Response
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import RedirectResponse
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver import ChromeOptions
from datetime import datetime
import json

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 허용되는 도메인 목록. 와일드카드(*)를 사용하여 모든 도메인을 허용할 수 있습니다.
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# 크롬 헤더 안 넣으면 인증 실패
headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) "
                  "Chrome/58.0.3029.110 Safari/537.3"
}

# 대용량 업데이트 시 필요한 정보
personal_token = ""
with open("env.json", "r", encoding='utf-8') as file:
    data = json.load(file)
    personal_token = data['token']


async def get_response(url):
    async with httpx.AsyncClient() as client:
        response = await client.get(url, headers=headers)
        return response


# 루트 경로에 대한 정보를 반환하는 기본 GET 요청 엔드포인트
@app.get("/")
def read(response: Response):
    response = RedirectResponse(url="/docs")
    return response


# 비동기 테스트 코드
# @app.get("/test/{name}")
# async def test_async(name: str):
#     start_time = time.time()

#     task_1 = asyncio.create_task(read_backjun(name, delay=1))
#     task_2 = asyncio.create_task(read_backjun(name, delay=2))

#     responses = await asyncio.gather(task_1, task_2)
#     response_1, response_2 = responses

#     elapsed_time = time.time() - start_time
#     print(f"Elapsed time: {elapsed_time:.2f} seconds")

#     return {"response_1": response_1, "response_2": response_2}

# 백준 아이디를 통해 정보와 언어를 크롤링하는 API
@app.get("/data/baekjoon/{name}")
async def read_baekjoon(name):
    # 백준 사용자의 정보, 언어 url
    information_url = 'https://www.acmicpc.net/user/' + name
    language_url = 'https://www.acmicpc.net/user/language/' + name
    information_response = await get_response(information_url)
    language_response = await get_response(language_url)

    # 결과 값을 반환할 객체
    result = {
        "tier": None,
        "pass_count": None,
        "try_fail_count": None,
        "submit_count": None,
        "fail_count": None,
        "languages_result": []
    }

    # 정보 응답이 OK 일때만 정보 가져오기
    if information_response.status_code == 200:
        # response 객체로부터 웹 페이지의 HTML 소스를 가져옵니다.
        html = information_response.text
        # BeautifulSoup 객체를 생성하여 HTML 소스를 파싱합니다.
        # 파싱에는 'html.parser' 파서를 사용합니다.
        soup = BeautifulSoup(html, 'html.parser')
        # 티어 가져오기
        img_tag = soup.find("img", class_="solvedac-tier")
        # 맞은 문제
        solved = soup.find("span", id="u-solved")
        # 시도했지만 맞지 못한 문제
        try_failed = soup.find("span", id="u-failed")
        # 제출 횟수
        submit = soup.find("a", href="/status?user_id=" + name)
        # 틀린 문제
        failed = soup.find("span", id="u-result-6")

        # solved_tier가 없으면 0
        if img_tag == None:
            result["tier"] = "https://d2gd6pc034wcta.cloudfront.net/tier/0.svg"
        else:
            result["tier"] = img_tag['src']
        result["pass_count"] = solved.text
        result["try_fail_count"] = try_failed.text
        result["submit_count"] = submit.text
        # failed가 없으면 0
        if failed == None:
            result["fail_count"] = 0
        else:
            result["fail_count"] = failed.text
    # 사용자가 없다면 return
    else:
        result = {"message": "사용자가 없습니다"}
        return result
    if language_response.status_code == 200:
        html = language_response.text
        soup = BeautifulSoup(html, 'html.parser')
        table = soup.find("table", class_="table table-bordered table-striped").find("tbody").findAll("tr")

        for row in table:
            # 각 tr 아래에 있는 th 태그와 td 태그들을 찾습니다.
            header = row.find("th")
            columns = row.findAll("td")
            # 만약 문자열이 비어있다면 0으로 치환합니다
            pass_count = columns[0].text.strip(
            ) if columns[0].text.strip() != "" else "0"
            # 결과 값에 저장할 배열을 생성하고 언어, 정답 비율, 맞춘 수를 저장합니다.

            # columns[11].text가 "0.000%"인 경우 건너뜁니다.
            if columns[11].text == "0.000%":
                continue
            language_result = {
                "language": header.text,
                "pass_percentage": re.sub(r'%', '', columns[11].text),
                "pass_count": pass_count,
            }
            # 결과 배열에 language_result을 추가합니다.
            result["languages_result"].append(language_result)

    return result


def getLanguage(username: str, result: dict):
    url = f'https://github-readme-stats.vercel.app/api/top-langs/?username={username}'
    response = requests.get(url)
    html = response.text
    soup = BeautifulSoup(html, 'html.parser')

    language_list = soup.select('g[class="stagger"]')

    tmp = []
    for language in language_list:
        data = dict()
        lang, percentage, = language.text.strip().split('\n')
        data['name'] = lang
        data['percentage'] = re.sub(r'%', '', percentage)
        tmp.append(data)

    result['languages'] = tmp
    return result

@app.get("/data/github/{token}")
def read_github(token):
    github_headers = {'Content-Type': 'application/json', 'Authorization': f'Bearer {token}'}
    user_res = requests.get("https://api.github.com/user", headers=github_headers)
    user_res = user_res.json()

    # 닉네임
    nickname = user_res['login']

    # 깃허브 링크
    profileLink = user_res['html_url']

    # 프로필 이미지
    avatarUrl = user_res['avatar_url']

    # 팔로워 수
    followers = user_res['followers']

    # 커밋 수
    commits_res = requests.get(f'https://api.github.com/search/commits?q=author%3A{nickname}%20is%3Apublic',
                               headers=github_headers)
    commit = commits_res.json()['total_count']

    # 레포지토리 정보
    repos_res = requests.get(f'https://api.github.com/search/repositories?q=user%3A{nickname}', headers=github_headers)
    repos_res = repos_res.json()

    repo_list = []
    star = 0
    for repo in repos_res['items']:
        # 레포 정보
        name = repo['name']
        link = repo['html_url']

        dto = dict()
        dto['name'] = name
        dto['link'] = link
        dto['readme'] = f'https://raw.githubusercontent.com/{nickname}/{name}/main/README.md'
        repo_list.append(dto)

        # 스타 수 합치기
        star += repo['stargazers_count']

    result = dict()
    result['nickname'] = nickname
    result['profileLink'] = profileLink
    result['avatarUrl'] = avatarUrl
    result['followers'] = followers
    result['commit'] = commit
    result['repositories'] = repo_list
    result['star'] = star
    result = getLanguage(nickname, result)
    return result


@app.get("/data/github/update/{nickname}")
async def update_github(nickname):
    github_headers = {'Content-Type': 'application/json', 'Authorization': f'Bearer {personal_token}'}
    user_res = await userAPI(github_headers, nickname)
    user_res = user_res.json()['items'][0]

    # 닉네임
    nickname = user_res['login']

    # 깃허브 링크
    profileLink = user_res['html_url']

    # 프로필 이미지
    avatarUrl = user_res['avatar_url']

    # 커밋 수
    commits_res = await commitAPI(github_headers, nickname)
    commit = commits_res.json()['total_count']

    # 팔로워 수
    followers_res = await followerAPI(github_headers, nickname)
    followers_res = followers_res.json()
    followers = len(followers_res)

    # 레포
    repos_res = await repoAPI(github_headers, nickname)
    repos_res = repos_res.json()

    repo_list = []
    star = 0
    for repo in repos_res['items']:
        # 레포 정보
        name = repo['name']
        link = repo['url']

        dto = dict()
        dto['name'] = name
        dto['link'] = link
        dto['readme'] = f'https://raw.githubusercontent.com/{nickname}/{name}/main/README.md'
        repo_list.append(dto)

        # 스타 수 합치기
        star += repo['stargazers_count']

    result = dict()
    result['nickname'] = nickname
    result['profileLink'] = profileLink
    result['avatarUrl'] = avatarUrl
    result['followers'] = followers
    result['commit'] = commit
    result['repositories'] = repo_list
    result['star'] = star
    result = getLanguage(nickname, result)
    return result


async def repoAPI(github_headers, nickname):
    return requests.get(f'https://api.github.com/search/repositories?q=user%3A{nickname}', headers=github_headers)


async def followerAPI(github_headers, nickname):
    return requests.get(f'https://api.github.com/users/{nickname}/followers', headers=github_headers)


async def commitAPI(github_headers, nickname):
    return requests.get(f'https://api.github.com/search/commits?q=author%3A{nickname}%20is%3Apublic',
                        headers=github_headers)


async def userAPI(github_headers, nickname):
    return requests.get(f"https://api.github.com/search/users?q=user%3A{nickname}", headers=github_headers)


@app.get("/data/postings")
def get_postings():
    posting_list = []

    chromedriver = "/usr/bin/chrome/chromedriver"

    options = ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('--disable-dev-shm-usage')
    options.add_argument('--window-size=1920x1080')
    options.add_argument('--disable-gpu')
    options.add_argument("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6)")

    driver = webdriver.Chrome(executable_path=chromedriver, chrome_options=options)

    base_url = 'https://www.catch.co.kr/NCS/RecruitCalendar/Week'

    driver.get(base_url)
    time.sleep(0.5)

    # 직무 클릭
    driver.find_element(By.XPATH, '//*[@id="Contents"]/div[2]/div/div[1]/button').click()
    time.sleep(0.5)

    # IT/인터넷 클릭
    driver.find_element(By.XPATH, '//*[@id="Contents"]/div[2]/div/div[1]/ul/li[7]/button').click()
    time.sleep(0.5)

    # IT/인터넷 전체 클릭
    driver.find_element(By.XPATH, '//*[@id="Contents"]/div[2]/div/div[1]/ul/li[7]/div/span[1]/label').click()
    time.sleep(0.5)

    end_flag = False
    while True:
        html = driver.page_source
        soup = BeautifulSoup(html, 'html.parser')

        tr_tags = soup.select('tr')
        for tr in tr_tags:
            posting = dict()
            for idx, td in enumerate(tr.contents):
                if td.name != 'td':
                    continue

                # 마감처리된 공고는 불러오지 않는다.
                if idx == 0 and td.attrs['class'][0] == 'end':
                    # 마감 공고 읽음 처리
                    end_flag = True
                    break

                # 시작인 공고는 불러오지만 시작이란 단어는 데이터로 저장하지 않는다.
                if idx == 0 and td.attrs['class'][0] == 'start':
                    continue

                if idx == 2:  # 회사명
                    posting['companyName'] = td.text
                elif idx == 4:  # 공고명
                    posting['postingName'] = td.contents[0].contents[0].text
                elif idx == 6:  # 경력, 학력무관
                    continue
                elif idx == 8:  # 기간
                    day_info = td.text.strip().split('~')
                    now = datetime.now()
                    posting['start'] = f'{now.year}.{day_info[0]}'

                    if (day_info[1].split('.')[0]).isdigit():
                        posting['end'] = f'{now.year}.{day_info[1]}'
                    else:
                        posting['end'] = '1996.11.22'
            else:
                posting_list.append(posting)
                continue
            break

        if end_flag:
            driver.close()
            return posting_list

        driver.find_element(By.XPATH, '//*[@id="Contents"]/div[4]/p/a[12]').send_keys(Keys.ENTER)
        time.sleep(0.5)