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
  reactStrictMode: false,
  async headers() {
    return [
      {
        source: '/api/:path*',
        headers: [
          { key: 'Access-Control-Allow-Credentials', value: 'true' },
          {
            key: 'Access-Control-Allow-Origin',
            value: 'https://k8b102.p.ssafy.io',
          },
        ],
      },
    ];
  },
  // CORS 처리 다른 방법
  // swcMinify: true,
  // async rewrites() {
  //   return [
  //     {
  //       source: '/:path*',
  //       destination: 'https://k8b102.p.ssafy.io/api:path*',
  //     },
  //   ];
  // },
};

const removeImports = require('next-remove-imports')();

module.exports = removeImports(nextConfig);
