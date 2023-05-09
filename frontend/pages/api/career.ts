import { authApi, basicApi } from './customAxio';

export const getJobPost = async (keyword: string) => {
  const { data } = await basicApi({
    method: 'get',
    url: `/job/posting`,
    params: {
      keyword: keyword,
    },
  });
  return data;
};

export const getJobStatus = async () => {
  const { data } = await basicApi({
    method: 'get',
    url: `/job/status`,
  });
  return data;
};

export const postJob = async (formData: any) => {
  const form = {
    statusId: parseInt(formData.statusId),
    jobPostingId: parseInt(formData.jobPostingId),
    objective: formData.objective,
    memo: formData.memo,
    dDayName: formData.dDayName,
    dDay: formData.dDay,
  };
  console.log(form);
  const { data } = await authApi({
    method: 'post',
    url: `/job`,
    data: form,
  });
  return data;
};
