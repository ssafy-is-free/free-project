import Head from 'next/head';
import Script from 'next/script';
import type { AppProps } from 'next/app';
import { useEffect } from 'react';
import { Provider } from 'react-redux';
import wrapper, { persistor } from '@/redux';
import { useMediaQuery } from 'react-responsive';
import { PersistGate } from 'redux-persist/integration/react';
import { lightTheme } from '@/styles/theme';
import GlobalStyle from '@/styles/GlobalStyle';
import { ThemeProvider } from 'styled-components';
import * as gtag from '@/utils/gtag';
import { setCookie } from '@/utils/cookies';
import Footer from '@/components/common/Footer';
import dynamic from 'next/dynamic';

const Desktop = dynamic(() => import('./desktop'), {
  ssr: false,
});

function App({ Component, ...rest }: AppProps) {
  // google analytics
  gtag.useGtag();
  const ID = gtag.GA_TRACKING_ID;

  const { store, props } = wrapper.useWrappedStore(rest);

  //모바일 데스크탑 분기
  const isMobile = useMediaQuery({ query: '(max-width: 768px)' });

  // useEffect(() => {
  //   console.log(isMobile);
  // }, [isMobile]);

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
            {!isMobile ? (
              <Desktop />
            ) : (
              <div>
                <Component {...props.pageProps} />
                <Footer />
              </div>
            )}
          </ThemeProvider>
        </PersistGate>
      </Provider>
    </>
  );
}
export default App;
