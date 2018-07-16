const publicPath = process.env.NODE_ENV === 'production' ? '/student/' : '/';
module.exports = {
  outputDir: './dist/static',
  devServer: {
    port: 8000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080'
      }
    }
  },
  lintOnSave: false,
  parallel: false
}
