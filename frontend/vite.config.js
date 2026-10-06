import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/h2-console': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    },
  },
  build: {
    // Output directly into Spring Boot's static resources directory
    outDir: '../backend/src/main/resources/static',
    emptyOutDir: true,
  },
})
