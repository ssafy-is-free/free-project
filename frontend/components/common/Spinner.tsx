import styled, { keyframes } from 'styled-components';
import { ISpinner } from './ICommon';

const spin = keyframes`
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
`;

/**
 * options : size, borderWidth, duration
 */
export const Spinner = styled.div<ISpinner>`
  margin: auto;
  border: ${(props) =>
    props.borderWidth
      ? `${props.borderWidth}rem solid ${props.theme.secondary}`
      : `0.2rem solid ${props.theme.secondary}`};
  border-top: ${(props) =>
    props.borderWidth ? `${props.borderWidth}rem solid ${props.theme.primary}` : `0.2rem solid ${props.theme.primary}`};
  border-radius: 50%;
  width: ${(props) => (props.size ? `${props.size}rem` : '3rem')};
  height: ${(props) => (props.size ? `${props.size}rem` : '3rem')};
  animation: ${spin} ${(props) => (props.duration ? `${props.duration}s` : '0.8s')} linear infinite;
`;
