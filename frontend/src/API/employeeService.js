import apiClient from './apiClient'

export default {
  getEmployees() {
    return apiClient.get('/empregados')
  },
  getEmployee(id) {
    return apiClient.get(`/empregados/${id}`)
  },
  createEmployee(employee) {
    return apiClient.post('/empregados', employee)
  },
  updateEmployee(id, employee) {
    return apiClient.put(`/empregados/${id}`, employee)
  },
  deleteEmployee(id) {
    return apiClient.delete(`/empregados/${id}`)
  }
}