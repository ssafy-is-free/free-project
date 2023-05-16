import axios from 'axios';
import Swal from 'sweetalert2';

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
  async (response) => {
    return response;
  },
  async (error) => {
    const { config, response } = error;
    const originalRequest = config;

    if (response.status === 401) {
      const accessToken = localStorage.getItem('accessToken');

      await axios
        .get(`${BASE_URL}/reissue`, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        .then((res) => {
          if (res.data.status === 'SUCCESS') {
            const newAccessToken = res.data.data['access-token'];
            originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
            localStorage.setItem('accessToken', newAccessToken);
            window.location.reload(); // TODO : 이게 최선인가?
            // return axios(originalRequest);
          } else if (res.data.status === 'FAIL') {
            //로그아웃 시키기
            Swal.fire({
              text: '로그아웃 되었습니다.',
              icon: 'error',
            });
            localStorage.removeItem('accessToken');
            window.location.href = '/';
          }
        })
        .catch((err) => {
          if (err.response.status === 401) {
            localStorage.removeItem('accessToken');
            Swal.fire({
              text: '로그아웃 되었습니다.',
              icon: 'error',
            });
            window.location.href = '/';
          }
        });
    }

    return Promise.reject(error);
  }
);
