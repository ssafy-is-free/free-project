import ReadmeDetail from './ReadmeDetail';
import { IRepository } from './IProfile';
import { useState } from 'react';
import styled from 'styled-components';

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
}

function Readme({ repository }: IReadme) {
  const [open, setOpen] = useState(false);
  const clicked = () => {
    setOpen(!open);
  };
  return (
    <ReadmeDiv>
      <ReadmeTitle onClick={clicked} active={open}>
        <h4>{repository.name}</h4>
        <img src="/Icon/FilterArrowIcon.svg" alt="#" />
      </ReadmeTitle>
      {open && (
        <ReadmeDetailDiv>
          <p>README.md</p>
          <ReadmeDetail link={repository.link}></ReadmeDetail>
        </ReadmeDetailDiv>
      )}
    </ReadmeDiv>
  );
}
export default Readme;
