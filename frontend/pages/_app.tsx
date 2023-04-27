import type { AppProps } from 'next/app';
import wrapper from '@/redux';
import { Provider } from 'react-redux';
import { ThemeProvider } from 'styled-components';
import { lightTheme } from '@/styles/theme';
import GlobalStyle from '@/styles/GlobalStyle';
import Footer from '@/components/common/Footer';
import Head from 'next/head';

function App({ Component, ...rest }: AppProps) {
  const { store, props } = wrapper.useWrappedStore(rest);

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
