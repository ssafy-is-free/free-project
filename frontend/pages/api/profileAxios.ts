import { authApi, basicApi } from './customAxio';

export const getGithub = async (userId: number) => {
  const { data } = await basicApi({
    method: 'get',
    url: `github/users/${userId}`,
  });

  return data.data;
};
