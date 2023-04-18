import "@/styles/globals.css";
import type { AppProps } from "next/app";
import { lightTheme } from "@/styles/theme";
import { ThemeProvider } from "styled-components";

export default function App({ Component, pageProps }: AppProps) {
  return (
    <ThemeProvider theme={lightTheme}>
      <Component {...pageProps} />
    </ThemeProvider>
  );
}
