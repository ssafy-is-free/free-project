import type { AppProps } from 'next/app';
import wrapper, { RootState, persistor } from '@/redux';
import { Provider, useDispatch, useSelector } from 'react-redux';
import { ThemeProvider } from 'styled-components';
import { lightTheme } from '@/styles/theme';
import GlobalStyle from '@/styles/GlobalStyle';
import Footer from '@/components/common/Footer';
import Head from 'next/head';
import { useCookies } from 'react-cookie';
import { PersistGate } from 'redux-persist/integration/react';
import { useEffect } from 'react';
import * as gtag from '@/utils/gtag';
import Script from 'next/script';

function App({ Component, ...rest }: AppProps) {
  // google analytics
  gtag.useGtag();
  const ID = gtag.GA_TRACKING_ID;

  const { store, props } = wrapper.useWrappedStore(rest);
  const [cookies, setCookie] = useCookies(['redirect-uri']);

  useEffect(() => {
    if (process.env.NEXT_PUBLIC_MODE && process.env.NODE_ENV === 'production') {
      setCookie('redirect-uri', 'k8b');
    }
  }, []);

  return (
    <>
      {process.env.NODE_ENV === 'production' && (
        <>
          {/* Global Site Tag (gtag.js) - Google Analytics */}
          <Script strategy="afterInteractive" src={`https://www.googletagmanager.com/gtag/js?id=${ID}`} />
          <Script
            id="gtag-init"
            strategy="afterInteractive"
            dangerouslySetInnerHTML={{
              __html: `
              window.dataLayer = window.dataLayer || [];
              function gtag(){dataLayer.push(arguments);}
              gtag('js', new Date());
              gtag('config', '${ID}', {
                page_path: window.location.pathname,
              });
            `,
            }}
          />
        </>
      )}
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
    </>
  );
}
export default App;
