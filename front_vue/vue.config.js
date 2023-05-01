const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      api: {
        target: process.env.VUE_APP_API_ENDPOINT,
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
