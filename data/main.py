# 필요한 라이브러리 임포트
import httpx
from bs4 import BeautifulSoup
from fastapi import FastAPI, Response
from fastapi.responses import RedirectResponse
from fastapi.middleware.cors import CORSMiddleware
import requests
import re

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


def getUser(username: str, result: dict):
    print(f'============= {username} 크롤링 =============')
    print('유저 정보 크롤링')
    url = f'https://github.com/{username}'
    response = requests.get(url)
    html = response.text
    soup = BeautifulSoup(html, 'html.parser')

    # 닉네임
    nickname_tag = soup.select_one('span[class="p-nickname vcard-username d-block"]')
    nickname = ""
    if nickname_tag is not None:
        nickname = nickname_tag.text.strip()

    # 프로필 이미지
    img_tag = soup.select_one('img[alt="Avatar"]')
    img = ""
    if img_tag is not None:
        img = img_tag.attrs['src']

    # 팔로워
    followers = 0
    followers_tag = soup.select_one(f'a[href="https://github.com/{username}?tab=followers"]')
    if followers_tag is not None:
        followers = int(followers_tag.text.strip().split()[0].replace(',', ''))

    # 깃허브 링크
    link = url

    result['nickname'] = nickname
    result['profileLink'] = link
    result['avatarUrl'] = img
    result['followers'] = followers

    return result


def getRepo(username: str, result: dict):
    print('레포지토리 정보 크롤링')
    url = f'https://github.com/{username}?tab=repositories'
    response = requests.get(url)
    html = response.text
    soup = BeautifulSoup(html, 'html.parser')

    repo_list = soup.select('a[itemprop="name codeRepository"]')

    commit_total = 0
    star_total = 0
    repos = []
    for repo_tag in repo_list:
        repo = dict()

        # 레포 이름
        repo_name = repo_tag.text.strip()

        # 레포 링크
        repo_url = f'https://github.com/{username}/{repo_name}'

        response = requests.get(repo_url)
        html = response.text
        soup = BeautifulSoup(html, 'html.parser')

        # 포크된 레포는 버림
        fork = soup.select_one('span[class="text-small lh-condensed-ultra no-wrap mt-1"]')
        if fork is not None:
            continue

        # 커밋 수
        commit_tag = soup.select_one('ul[class="list-style-none d-flex"]')
        if commit_tag is not None:
            commit = int(commit_tag.text.strip().split()[0].replace(',', ''))
            commit_total += commit

        # 스타 수
        star_tag = soup.select_one(f'a[href="/{username}/{repo_name}/stargazers"]')
        if star_tag is not None:
            star = int(star_tag.text.strip().split()[0].replace(',', ''))
            star_total += star

        # 리드미
        # readme_html = soup.select_one('article[class="markdown-body entry-content container-lg"]')
        # if readme_html is not None:
        #     repo['readme'] = str(readme_html)
        # else:
        #     repo['readme'] = ""
        repo['readme'] = f"https://raw.githubusercontent.com/{username}/{repo_name}/main/README.md"

        repo['name'] = repo_name
        repo['link'] = repo_url

        repos.append(repo)

    result['commit'] = commit_total
    result['star'] = star_total
    result['repositories'] = repos

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


@app.get("/data/github/{name}")
def read_github(name):
    result = dict()
    result = getUser(name, result)
    result = getRepo(name, result)
    result = getLanguage(name, result)

    return result
