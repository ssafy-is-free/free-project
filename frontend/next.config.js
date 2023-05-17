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

  images: {
    domains: ['avatars.githubusercontent.com', 'd2gd6pc034wcta.cloudfront.net', 'mqr.kr'],
  },

  reactStrictMode: false,
  // async headers() {
  //   return [
  //     {
  //       source: '/api/:path*',
  //       headers: [
  //         { key: 'Access-Control-Allow-Credentials', value: 'true' },
  //         {
  //           key: 'Access-Control-Allow-Origin',
  //           value: 'https://k8b102.p.ssafy.io',
  //         },
  //       ],
  //     },
  //   ];
  // },
  // CORS 처리 다른 방법
  // swcMinify: true,
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: `${process.env.NEXT_PUBLIC_API_URL}/:path*`,
      },
    ];
  },
};

const withBundleAnalyzer = require('@next/bundle-analyzer')({
  enabled: process.env.ANALYZE === 'true',
});

const removeImports = require('next-remove-imports')();
const withPWA = require('next-pwa')({
  dest: 'public',
  disable: process.env.NODE_ENV === 'development',
});

module.exports = removeImports(withPWA(nextConfig));
