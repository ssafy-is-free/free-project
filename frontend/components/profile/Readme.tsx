import ReadmeDetail from './ReadmeDetail';
import { useState, useEffect } from 'react';
import { IRepository } from './IProfile';
import styled from 'styled-components';
import { getReadme } from '@/pages/api/profileAxios';
import Image from 'next/image';

const ReadmeDiv = styled.div`
  border: solid;
  border-radius: 0.5rem;
  border-color: ${(props) => props.theme.secondary};
`;

const ReadmeTitle = styled.div<{ active: boolean }>`
  display: flex;
  justify-content: space-between;
  padding: 0.5rem;
  border-bottom: ${(props) => (props.active ? `2px solid ${props.theme.secondary}` : '0px')};
  img {
    transform: ${(props) => (props.active ? 'rotate(180deg)' : 'rotate(0deg)')};
  }
`;

const ReadmeDetailDiv = styled.div`
  min-height: 50vh;
  max-height: 80vh;
  overflow: auto;
  padding: 0.5rem;
  p {
    border-bottom: 2px solid ${(props) => props.theme.secondary};
  }
  span {
    font-size: small;
    color: gray;
  }
`;

interface IReadme {
  repository: IRepository;
  githubId: number;
}

const Readme = ({ repository, githubId }: IReadme) => {
  const [link, setLink] = useState<string>('');
  const [open, setOpen] = useState(false);
  const clicked = () => {
    setOpen(!open);
  };

  useEffect(() => {
    (async () => {
      const data = await getReadme(githubId, repository.id);
      setLink(data.data.readme);
    })();
  }, []);

  return (
    <ReadmeDiv>
      <ReadmeTitle onClick={clicked} active={open}>
        <h4>{repository.name}</h4>
        <Image src="/Icon/FilterArrowIcon.svg" alt="arrow" width={16} height={16} />
      </ReadmeTitle>
      {open && (
        <ReadmeDetailDiv>
          <a href={repository.link} target="_blank">
            README.md
            <span>(깃허브에서 보려면 눌러주세요)</span>
          </a>
          <ReadmeDetail link={link}></ReadmeDetail>
        </ReadmeDetailDiv>
      )}
    </ReadmeDiv>
  );
};
export default Readme;
