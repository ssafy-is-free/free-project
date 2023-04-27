/** @type {import('next').NextConfig} */

const nextConfig = {
  compiler: {
    styledComponents: true,
  },
  webpack(config) {
    config.module.rules.push({
      test: /\.svg$/i,
      use: ['@svgr/webpack'],
    });
    return config;
  },
  reactStrictMode: true,
  // swcMinify: true,
  // async rewrites() {
  //   return [
  //     {
  //       source: '/:path*',
  //       destination: 'https://k8b102.p.ssafy.io/api/:path*',
  //     },
  //   ];
  // },
};

const removeImports = require('next-remove-imports')();

module.exports = removeImports(nextConfig);
