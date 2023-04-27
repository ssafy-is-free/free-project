import dynamic from 'next/dynamic';
import { useEffect, useState } from 'react';
import '@uiw/react-markdown-preview/markdown.css';
import { MarkdownPreviewProps } from '@uiw/react-markdown-preview';
import { readmeApi } from '@/utils/api/getReadme';
import { Spinner } from '../common/Spinner';

const MDPreview = dynamic<MarkdownPreviewProps>(() => import('@uiw/react-markdown-preview'), {
  ssr: false,
  loading: () => <Spinner></Spinner>,
});

interface IReadmeDetail {
  link: string;
}

export default function ReadmeDetail({ link }: IReadmeDetail) {
  const [readme, setReadme] = useState<string>('');
  useEffect(() => {
    (async () => {
      const response = await readmeApi(link);
      setReadme(response.data);
    })();
  }, []);
  return <MDPreview source={readme} />;
}
