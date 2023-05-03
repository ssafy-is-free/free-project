import ReadmeDetail from './ReadmeDetail';
import { useState, useEffect } from 'react';
import { IRepository } from './IProfile';
import styled from 'styled-components';
import { getReadme } from '@/pages/api/profileAxios';

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
        <img src="/Icon/FilterArrowIcon.svg" alt="#" />
      </ReadmeTitle>
      {open && (
        <ReadmeDetailDiv>
          <p>README.md</p>
          <ReadmeDetail link={link}></ReadmeDetail>
        </ReadmeDetailDiv>
      )}
    </ReadmeDiv>
  );
};
export default Readme;
