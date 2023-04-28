import type { AppProps } from 'next/app';
import wrapper from '@/redux';
import { Provider } from 'react-redux';
import { ThemeProvider } from 'styled-components';
import { lightTheme } from '@/styles/theme';
import GlobalStyle from '@/styles/GlobalStyle';
import Footer from '@/components/common/Footer';
import Head from 'next/head';
import { useCookies } from 'react-cookie';
import { useEffect } from 'react';

function App({ Component, ...rest }: AppProps) {
  const { store, props } = wrapper.useWrappedStore(rest);
  const [cookies, setCookie] = useCookies(['redirect-uri']);

  useEffect(() => {
    if (process.env.NEXT_PUBLIC_MODE === 'prod') {
      setCookie('redirect-uri', 'prod');
    }
  }, []);

  return (
    <Provider store={store}>
      <GlobalStyle />
      <ThemeProvider theme={lightTheme}>
        <Head>
          <title>CHPO</title>
        </Head>
        <Component {...props.pageProps} />
        <Footer />
      </ThemeProvider>
    </Provider>
  );
}
export default App;
