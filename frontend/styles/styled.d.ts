import { DefaultTheme } from 'styled-components';

declare module 'styled-components' {
  export interface DefaultTheme {
    primary: string;
    secondary: string;
    fontBlack: string;
    fontWhite: string;
    outlineGray: string;
    transparent: string;
    footerGray: string;
    bgWhite: string;
    modalGray: string;
    fontGray: string;
    lightGray: string;
    btnGray: string;
    stateGreen: string;
    stateRed: string;
    stateIng: string;
    stateGreenFont: string;
    stateRedFont: string;
    stateIngFont: string;
    jobSelectCover: string;
    jobSelectDefault: string;
    jobSelectSelected: string;
    menuBg: string;
    fontDarkGray: string;
  }
}
