import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`

@font-face {
    font-family: 'Pretendard-Regular';
    src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
    font-weight: 400;
    font-style: normal;
}

body{
  -webkit-user-select:none;
  -moz-user-select:none;
  -ms-user-select:none;
  user-select:none
}

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

  font-family: 'Pretendard-Regular';
  src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
  font-weight: normal;
  font-style: normal;
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
