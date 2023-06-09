import { authApi } from './customAxio';

/**
 * 백준 아이디 중복체크 API
 * @param bojId 중복체크할 백준 아이디
 */
export const checkBojId = async (bojId: string) => {
  const params = {
    id: bojId,
  };

  const { data } = await authApi({
    method: 'get',
    url: '/boj-id',
    params: params,
  });

  return data;
};

/**
 * 백준 아이디 등록 API
 * @param bojId 등록할 백준 아이디
 */
export const postBojId = async (bojId: string) => {
  const body = {
    boj_id: bojId,
  };

  const { data } = await authApi({
    method: 'patch',
    url: '/boj-id',
    data: body,
  });

  return data?.status;
};

/**
 * 크롤링 api
 */
export const patchCrawaling = async () => {
  const { data } = await authApi({
    method: 'patch',
    url: '/github/crawling',
  });

  return data;
};
