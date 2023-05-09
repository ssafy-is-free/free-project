import { authApi, basicApi } from './customAxio';

/**
 * 채용 공고 깃허브 랭킹 조회 api
 * @param sizeParam
 * @param jobPostingIdParam
 * @param rankParam
 * @param useIdParam
 * @param scoreParam
 * @returns
 */
export const getGithubRanking = async (
  sizeParam: number,
  jobPostingIdParam: number,
  rankParam?: number,
  useIdParam?: number,
  scoreParam?: number
) => {
  const params = {
    size: sizeParam,
    jobPostingId: jobPostingIdParam,
    // rank: rankParam,
    // userId: useIdParam,
    // score: scoreParam,
  };

  const { data } = await basicApi({
    method: 'get',
    url: '/github/ranks',
    params: params,
  });

  return data.data.ranks;
};

/**
 * 백준 랭킹 조회
 * @param sizeParam
 * @param rankParam
 * @param useIdParam
 * @param scoreParam
 * @returns
 */
export const getBojRanking = async (
  sizeParam: number,
  jobPostingIdParam: number,
  rankParam?: number,
  useIdParam?: number,
  scoreParam?: number
) => {
  const params = {
    size: sizeParam,
    jobPostingId: jobPostingIdParam,
    // rank: rankParam,
    // userId: useIdParam,
    // score: scoreParam,
  };

  const { data } = await basicApi({
    method: 'get',
    url: '/boj/ranks',
    params: params,
  });

  return data.data;
};

/**
 * 공고별 전체 사용자 정보 가져오기
 * @param jobPostingId 채용 공고 아이디
 * @returns
 */
export const getPostingsAllUsers = async (jobPostingId: number) => {
  const { data } = await authApi({
    method: 'get',
    url: `/analysis/github/postings/${jobPostingId}`,
  });

  // console.log(data.data);

  return data.data;
};

/**
 * 내 깃허브 랭킹 조회 api
 * @param jobPostingId 채용 공고 아이디
 * @returns
 */
export const getMyGitRanking = async (jobPostingIdParam: number) => {
  const params = {
    jobPostingId: jobPostingIdParam,
  };
  const { data } = await authApi({
    method: 'get',
    url: '/github/my-rank',
    params: params,
  });

  return data.data.githubRankingCover;
};