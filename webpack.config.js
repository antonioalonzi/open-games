var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, 'src/main/resources/public/js');
var APP_DIR = path.resolve(__dirname, 'src/main/resources/app/src');

var config = {
  entry: APP_DIR + '/index.js',
  output: {
    path: BUILD_DIR,
    filename: 'open-games.js'
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        include: APP_DIR,
        loader: 'babel-loader'
      },
      {
        test: /\.css$/,
        use: [
          { loader: "style-loader" },
          { loader: "css-loader" }
        ]
      }
    ]
  }
};

module.exports = config;
