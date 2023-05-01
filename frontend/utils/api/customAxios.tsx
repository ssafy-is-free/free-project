import axios from 'axios';

// const BASE_URL = 'https://k8b102.p.ssafy.io/api';
const BASE_URL = process.env.NEXT_PUBLIC_API_URL;

// 회원
export const authApi = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 비회원
export const basicApi = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// authApi.interceptors.request.use();

// authApi.interceptors.response.use();
