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
};

const removeImports = require('next-remove-imports')();

module.exports = removeImports(nextConfig);
