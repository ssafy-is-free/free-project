import type { AppProps } from 'next/app';
import wrapper, { persistor } from '@/redux';
import { Provider } from 'react-redux';
import { ThemeProvider } from 'styled-components';
import { lightTheme } from '@/styles/theme';
import GlobalStyle from '@/styles/GlobalStyle';
import Footer from '@/components/common/Footer';
import Head from 'next/head';
import { useCookies } from 'react-cookie';
import { useEffect } from 'react';
import { PersistGate } from 'redux-persist/integration/react';

function App({ Component, ...rest }: AppProps) {
  const { store, props } = wrapper.useWrappedStore(rest);
  const [cookies, setCookie] = useCookies(['redirect-uri']);

  useEffect(() => {
    // 모바일 100vh 문제
    function setScreenSize() {
      let vh = window.innerHeight * 0.01;
      document.documentElement.style.setProperty('--vh', `${vh}px`); //"--vh"라는 속성으로 정의해준다.
    }

    window.addEventListener('resize', () => setScreenSize());

    if (process.env.NEXT_PUBLIC_MODE === 'prod') {
      setCookie('redirect-uri', 'prod');
    }
  }, []);

  return (
    <Provider store={store}>
      <PersistGate persistor={persistor} loading={null}>
        <GlobalStyle />
        <ThemeProvider theme={lightTheme}>
          <Head>
            <title>CHPO</title>
          </Head>
          <Component {...props.pageProps} />
          <Footer />
        </ThemeProvider>
      </PersistGate>
    </Provider>
  );
}
export default App;
