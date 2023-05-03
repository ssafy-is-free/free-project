import axios from 'axios';

/**
 *
 * @param url raw 리드미 주소
 * @returns
 */
export const readmeApi = async (url: string) => {
  try {
    const response = await axios.get(url);
    return response;
  } catch (error) {
    return { data: '`리드미 파일이 없는 레포지토리거나 main브랜치에 readme파일이 없어요`' };
  }
};
