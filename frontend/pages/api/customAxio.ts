import axios from 'axios';
import { config } from 'process';

const BASE_URL = 'https://k8b102.p.ssafy.io/api';

/**
 * 회원 전용 기능일 때 사용할 axios
 */
export const authApi = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

/** 아래처럼 사용
 * export const getRank = async (page: number) => {
  const params = {
    size: 8,
    rank: 9,
  };

  const { data } = await authApi<>({
    method: 'get',
    url: '/github/rank',
    params: params,
  });
  return data;
};
 */

/**
 * 비회원도 쓸 수 있는 기능일 때 사용할 axios
 */
export const basicApi = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// authApi header에 token 추가
// 요청 인터셉터 추가
authApi.interceptors.request.use((config: any) => {
  const accessToken = localStorage.getItem('accessToken');

  //   if (!accessToken) {
  console.log(config);
  //   }
  return config;
});

// 응답 인터셉터 추가
authApi.interceptors.response.use();
