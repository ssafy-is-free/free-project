import styled from 'styled-components';

const DefaultDiv = styled.div`
  background-color: ${(props) => props.theme.lightGray};
  border-radius: 1rem;
  height: 7rem;

  display: flex;
  align-items: center;
  justify-content: center;
`;

interface ICareerListItemDefaultProps {
  category: number;
}

const CareerListItemDefault = ({ category }: ICareerListItemDefaultProps) => {
  if (category === 0) {
    return (
      <DefaultDiv>
        <div>
          현재 진행중인 이력이 없어요
          <br />
          우측 상단의 버튼을 눌러 추가해보세요
        </div>
      </DefaultDiv>
    );
  } else {
    return (
      <DefaultDiv>
        <div>현재 종료된 이력이 없어요</div>
      </DefaultDiv>
    );
  }
};

export default CareerListItemDefault;
