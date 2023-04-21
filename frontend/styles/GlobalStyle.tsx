import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`

html{
  scroll-behavior: smooth;
}

*, 
*::before, 
*::after {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  border: 0;
};

a {
  text-decoration: none; /* 링크의 밑줄 제거 */
  color: inherit; /* 링크의 색상 제거 */
};

li{
  list-style: none;
}
`;

export default GlobalStyle;
