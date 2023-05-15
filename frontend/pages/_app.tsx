import type { AppProps } from 'next/app';
import wrapper, { persistor } from '@/redux';
import { Provider } from 'react-redux';
import { ThemeProvider } from 'styled-components';
import { lightTheme } from '@/styles/theme';
import GlobalStyle from '@/styles/GlobalStyle';
import Footer from '@/components/common/Footer';
import Head from 'next/head';
import { PersistGate } from 'redux-persist/integration/react';
import { useState, useEffect } from 'react';
import * as gtag from '@/utils/gtag';
import Script from 'next/script';
import { MobileView, BrowserView } from 'react-device-detect';
import WebGuide from '@/components/common/WebGuide';
import { getCookie, setCookie } from '@/utils/cookies';

function App({ Component, ...rest }: AppProps) {
  // google analytics
  gtag.useGtag();
  const ID = gtag.GA_TRACKING_ID;

  const { store, props } = wrapper.useWrappedStore(rest);

  // pc 일경우
  const [loading, setLoading] = useState(true);
  const [check, setCheck] = useState(true);
  const today = new Date();

  useEffect(() => {
    if (process.env.NEXT_PUBLIC_MODE && process.env.NODE_ENV === 'production') {
      setCookie('redirect-uri', 'k8b');
    }
    const savedDate = new Date(getCookie('webview-check'));
    const dayDiff = today.getDate() - savedDate.getDate();
    if (dayDiff < 1) {
    } else {
      setCheck(false);
    }
    setLoading(false);
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
            <MobileView>
              <Component {...props.pageProps} />
              <Footer />
            </MobileView>
            <BrowserView>
              {check && !loading ? (
                <div>
                  <Component {...props.pageProps} />
                  <Footer />
                </div>
              ) : (
                <WebGuide></WebGuide>
              )}
            </BrowserView>
          </ThemeProvider>
        </PersistGate>
      </Provider>
    </>
  );
}
export default App;
