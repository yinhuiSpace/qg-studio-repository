import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  server:{
    port:13500,
    open:true,
    proxy:{
      '/api':{
        target:"http://localhost:13900/", //跨域地址
        changeOrigin:true, //支持跨域
        rewrite:(path) => path.replace(/^\/api/, "")//重写路径,替换/api
      }
    }
  },
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})
