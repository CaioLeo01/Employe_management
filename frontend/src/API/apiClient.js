import axios from 'axios'

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json'
  }
})

// Interceptor para tratamento global de erros
apiClient.interceptors.response.use(
  response => response,
  error => {
    const errorMessage = error.response?.data?.message || 'Ocorreu um erro'
    console.error('API Error:', errorMessage)
    return Promise.reject(error)
  }
)

export default apiClient