import { authApi, basicApi } from './customAxio';

export const getGithub = async (userId: string | number) => {
  const { data } = await basicApi({
    method: 'get',
    url: `/github/users/${userId}`,
  });
  return data;
};

export const getMyGithub = async () => {
  const { data } = await authApi({
    method: 'get',
    url: `/github/users`,
  });
  return data;
};

export const getBoj = async (userId: string | number) => {
  const { data } = await basicApi({
    method: 'get',
    url: `/boj/users/${userId}`,
  });

  return data;
};

export const getMyBoj = async () => {
  const { data } = await authApi({
    method: 'get',
    url: `/boj/users`,
  });

  return data;
};

export const getReadme = async (githubId: number, repoId: number) => {
  const { data } = await basicApi({
    method: 'get',
    url: `/github/${githubId}/repositories/${repoId}`,
  });

  return data;
};

export const patchRepoOpen = async (isOpen: boolean) => {
  const { data } = await authApi({
    method: 'patch',
    url: `/github/open`,
    data: {
      isOpen: isOpen,
    },
  });
  return data;
};
