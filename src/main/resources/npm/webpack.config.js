var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, '../static/app');
var APP_DIR = path.resolve(__dirname, 'src');

var config = {
    entry: APP_DIR + '/index.js',
    output: {
        path: BUILD_DIR,
        filename: 'open-games.js'
    },
    module : {
        rules : [
            {
                test : /\.jsx?/,
                include : APP_DIR,
                loader : 'babel-loader'
            }
        ]
    }
};

module.exports = config;
