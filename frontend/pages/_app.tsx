import type { AppProps } from 'next/app';
import wrapper, { persistor } from '@/redux';
import { Provider, useDispatch } from 'react-redux';
import { ThemeProvider } from 'styled-components';
import { lightTheme } from '@/styles/theme';
import GlobalStyle from '@/styles/GlobalStyle';
import Footer from '@/components/common/Footer';
import Head from 'next/head';
import { useCookies } from 'react-cookie';
import { PersistGate } from 'redux-persist/integration/react';
import { useEffect } from 'react';

function App({ Component, ...rest }: AppProps) {
  const { store, props } = wrapper.useWrappedStore(rest);
  const [cookies, setCookie] = useCookies(['redirect-uri']);

  useEffect(() => {
    if (process.env.NEXT_PUBLIC_MODE && process.env.NODE_ENV === 'production') {
      setCookie('redirect-uri', 'k8b');
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
