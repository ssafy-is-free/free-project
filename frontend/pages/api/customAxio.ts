import axios from 'axios';
import { config } from 'process';

// const BASE_URL = 'https://k8b102.p.ssafy.io/api';
const BASE_URL = '/api';

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

  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }

  // console.log('request config : ', config);

  return config;
});

// 응답 인터셉터 추가
authApi.interceptors.response.use(
  (response) => {
    // console.log('response', response);

    return response;
  },
  async (error) => {
    // console.log('error', error);

    return error;
    // const { config, response } = error;
    // const originalRequest = config;

    // if (response.status === 401) {
    //   console.log('access 만료');
    //   const accessToken = localStorage.getItem('accessToken');

    //   await axios
    //     .get(`${BASE_URL}/reissue`, {
    //       withCredentials: true,
    //       headers: {
    //         Authorization: `Bearer ${accessToken}`,
    //       },
    //     })
    //     .then((res) => {
    //       if (res.status === 200) {
    //         const newAccessToken = res.headers.authorization;
    //         originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
    //         localStorage.setItem('accessToken', newAccessToken);
    //         return axios(originalRequest);
    //       }
    //     })
    //     .catch((err) => {
    //       if (err.response.status === 401) {
    //         localStorage.removeItem('accessToken');
    //         window.location.href = '/';
    //       }
    //     });
    // }

    // return Promise.reject(error);
  }
);
