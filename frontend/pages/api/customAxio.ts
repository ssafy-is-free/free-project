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
    // console.log('Response');

    // await axios.get(`${BASE_URL}/reissue`, {
    //   withCredentials: true,
    //   headers: {
    //     Authorization: `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdXRoIiwibmlja25hbWUiOiJzZXVuZ2JvazMyNDAiLCJpZCI6IjQyIiwiZXhwIjoxNjcwMDAwMDAwLCJpYXQiOjE2ODQxMzUxMDZ9.UzNEvwyL9pSF_njd4k-FR8GFo2v7pISKdxBEOu2HRrdAf05B1KWioyftt8-AbDN-D9-OPSmKw2KqzLUnVp78qA`,
    //   },
    // });

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
            console.log(originalRequest);
            return axios(originalRequest);
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
