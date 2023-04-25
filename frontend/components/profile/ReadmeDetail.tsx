import dynamic from 'next/dynamic';
import { useEffect, useState } from 'react';
import '@uiw/react-markdown-preview/markdown.css';
import { MarkdownPreviewProps } from '@uiw/react-markdown-preview';
import { readmeApi } from '@/utils/api/getReadme';

const MDPreview = dynamic<MarkdownPreviewProps>(() => import('@uiw/react-markdown-preview'), {
  ssr: false,
});

interface IReadmeDetail {
  link: string;
}

export default function ReadmeDetail({ link }: IReadmeDetail) {
  const [readme, setReadme] = useState<string>('');
  const getReadme = async () => {
    readmeApi(link).then((answer) => {
      setReadme(answer.data);
    });
  };
  useEffect(() => {
    getReadme();
  }, []);
  return <MDPreview source={readme} />;
}
