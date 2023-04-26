import { authApi, basicApi } from './customAxio';

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
