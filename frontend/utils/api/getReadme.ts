import axios from 'axios';

/**
 *
 * @param url raw 리드미 주소
 * @returns
 */
export const readmeApi = async (url: string) => {
  return axios.get(url);
};
