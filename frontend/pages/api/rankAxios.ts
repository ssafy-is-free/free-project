import { authApi, basicApi } from './customAxio';

/**
 *랭킹 필터 언어 목록 조회 api
 * @param languageType 깃허브인지 백준인지 타입
 * @returns
 */
export const getFilter = async (languageType: String) => {
  const params = {
    type: languageType,
  };

  const { data } = await basicApi({
    method: 'get',
    url: '/filter/language',
    params: params,
  });

  return data.data;
};

/**
 * 깃허브 랭킹 조회 api
 * @param sizeParam 개수
 * @param rankParam 시작랭킹
 * @returns
 */
export const getGithubRanking = async (sizeParam: number, rankParam: number) => {
  const params = {
    size: sizeParam,
    rank: rankParam,
  };

  const { data } = await basicApi({
    method: 'get',
    url: '/github/ranks',
    params: params,
  });

  return data.data.ranks;
};

/**
 * 백준 랭킹 조회 api
 * @returns
 */
export const getBojRanking = async () => {
  const { data } = await basicApi({
    method: 'get',
    url: '/boj/ranks',
  });

  return data.data.ranks;
};

/**
 * 내 백준 랭킹 조회 api
 */
export const getMyBojRanking = async () => {
  const { data } = await authApi({
    method: 'get',
    url: '/boj/my-rank',
  });
};

/**
 * 깃허브 닉네임 검색
 * @param nicknameParam 검색할 닉네임
 */
export const getSearchGitUser = async (nicknameParam: string | undefined) => {
  const params = {
    nickname: nicknameParam,
  };

  const { data } = await basicApi({
    method: 'get',
    url: '/github/search',
    params: params,
  });

  return data;
};
