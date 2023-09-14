const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    headers: { "Access-Control-Allow-Origin": "*" },
    proxy: {
      backend: {
        target: 'http://localhost:8080/',
        ws: true,
        changeOrigin: true
      },
      socket: {
        target: process.env.VUE_APP_SOCKET_ENDPOINT,
        ws: true,
        changeOrigin: true
      }

    }
  }
})

/*module.exports = {
  devServer: {
    proxy: 'http://localhost:8080'
  }
}*/
