import '@/styles/globals.css';
import type { AppProps } from 'next/app';
import { lightTheme } from '@/styles/theme';
import { ThemeProvider } from 'styled-components';
import wrapper from '@/redux';

function App({ Component, pageProps }: AppProps) {
  return (
    <ThemeProvider theme={lightTheme}>
      <Component {...pageProps} />
    </ThemeProvider>
  );
}
export default wrapper.withRedux(App);
