import { ServerStyleSheet } from 'styled-components';
import Document, { Html, Head, Main, NextScript, DocumentContext } from 'next/document';

class MyDocument extends Document {
  static async getInitialProps(ctx: DocumentContext) {
    const sheet = new ServerStyleSheet();
    const originalRenderPage = ctx.renderPage;
    try {
      ctx.renderPage = () =>
        originalRenderPage({
          enhanceApp: (App) => (props) => sheet.collectStyles(<App {...props} />),
        });
      const initialProps = await Document.getInitialProps(ctx);
      return {
        ...initialProps,
        styles: (
          <>
            {initialProps.styles} {sheet.getStyleElement()}
          </>
        ),
      };
    } finally {
      sheet.seal();
    }
  }

  render() {
    return (
      <Html lang="ko">
        <Head>
          <link rel="manifest" href="/manifest.json" />
          <link rel="icon" sizes="16x16" href="/pwa/icon-16x16.png" />
          <link rel="icon" sizes="32x32" href="/pwa/icon-32x32.png" />
          <link rel="icon" sizes="96x96" href="/pwa/icon-96x96.png" />
          <link rel="icon" sizes="192x192" href="/pwa/icon-192x192.png" />
          <link rel="apple-touch-icon" sizes="57x57" href="/pwa/icon-57x57.png" />
          <link rel="apple-touch-icon" sizes="60x60" href="/pwa/icon-60x60.png" />
          <link rel="apple-touch-icon" sizes="72x72" href="/pwa/icon-72x72.png" />
          <link rel="apple-touch-icon" sizes="76x76" href="/pwa/icon-76x76.png" />
          <link rel="apple-touch-icon" sizes="114x114" href="/pwa/icon-114x114.png" />
          <link rel="apple-touch-icon" sizes="120x120" href="/pwa/icon-120x120.png" />
          <link rel="apple-touch-icon" sizes="144x144" href="/pwa/icon-144x144.png" />
          <link rel="apple-touch-icon" sizes="152x152" href="/pwa/icon-152x152.png" />
          <link rel="apple-touch-icon" sizes="180x180" href="/pwa/icon-180x180.png" />
          <link rel="apple-touch-icon" sizes="192x192" href="/pwa/icon-192x192.png" />
          <meta name="msapplication-TileColor" content="#4A58A9"></meta>
          <meta name="theme-color" content="#4A58A9" />
        </Head>
        <body style={{ margin: '0px' }}>
          <Main />
          <NextScript />
          {/* Global Site Tag (gtag.js) - Google Analytics */}
          {process.env.NODE_ENV !== 'production' && (
            <script
              dangerouslySetInnerHTML={{
                __html: `
                window.dataLayer = window.dataLayer || [];
                function gtag(){dataLayer.push(arguments);}
                gtag('js', new Date());
              `,
              }}
            />
          )}
        </body>
      </Html>
    );
  }
}

export default MyDocument;
