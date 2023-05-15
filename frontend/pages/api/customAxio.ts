import { login, logout } from '@/redux/authSlice';
import axios from 'axios';
import { config } from 'process';
import { useDispatch } from 'react-redux';

// const BASE_URL = 'https://k8b102.p.ssafy.io/api';
const BASE_URL = '/api';

// const dispach = useDispatch();

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
  return config;
});

// 응답 인터셉터 추가
authApi.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const { config, response } = error;
    const originalRequest = config;

    console.log('response', response);

    if (response.status === 401) {
      const accessToken = localStorage.getItem('accessToken');
      // dispach(logout());

      await axios
        .get(`${BASE_URL}/reissue`, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((res) => {
          if (res.status === 200) {
            const newAccessToken = res.data.data['access-token'];
            originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
            localStorage.setItem('accessToken', newAccessToken);
            document.cookie = `refresh-token=${newAccessToken}`;
            // dispach(login());
            return axios(originalRequest);
          }
        })
        .catch((err) => {
          if (err.response.status === 401) {
            localStorage.removeItem('accessToken');
            // dispach(logout());
            alert('토큰 만료!');
            window.location.href = '/';
          }
        });
    }

    return Promise.reject(error);
  }
);
