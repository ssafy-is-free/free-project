import { authApi, basicApi } from './customAxio';

export const getJobPost = async (keyword: string) => {
  const { data } = await authApi({
    method: 'get',
    url: `/job/posting`,
    params: {
      keyword: keyword,
    },
  });
  return data;
};

export const getJobStatus = async () => {
  const { data } = await authApi({
    method: 'get',
    url: `/job/status`,
  });
  return data;
};

export const postJob = async (formData: any) => {
  let form;
  if (formData.ddayName === '') {
    form = {
      statusId: parseInt(formData.statusId),
      jobPostingId: parseInt(formData.jobPostingId),
      objective: formData.objective,
      memo: formData.memo,
    };
  } else {
    form = {
      statusId: parseInt(formData.statusId),
      jobPostingId: parseInt(formData.jobPostingId),
      objective: formData.objective,
      memo: formData.memo,
      ddayName: formData.ddayName,
      dday: formData.dday,
    };
  }
  const { data } = await authApi({
    method: 'post',
    url: `/job`,
    data: form,
  });
  return data;
};

export const getHistory = async (statusId: string[], size?: string) => {
  const params = new URLSearchParams();
  statusId.forEach((id: string) => {
    params.append('statusId', id);
  });
  if (size) {
    params.append('size', size);
  }
  const { data } = await authApi({
    method: 'get',
    url: `/job`,
    params: params,
  });
  return data;
};

export const getHistoryDtail = async (historyId: number) => {
  const { data } = await authApi({
    method: 'get',
    url: `/job/history/${historyId}`,
  });
  return data;
};

export const deleteHistory = async (historyIds: number[]) => {
  const { data } = await authApi({
    method: 'delete',
    url: `/job/history`,
    data: {
      historyId: historyIds,
    },
  });
  return data;
};

export const patchHistory = async (historyId: number, modifyValue: any) => {
  const { data } = await authApi({
    method: 'patch',
    url: `/job/history/${historyId}`,
    data: modifyValue,
  });
  return data;
};
