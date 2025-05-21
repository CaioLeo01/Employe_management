import { defineStore } from 'pinia'
import employeeService from '@/api/employeeService'

export const useEmployeeStore = defineStore('employee', {
  state: () => ({
    employees: [],
    loading: false,
    error: null
  }),
  actions: {
    async fetchEmployees() {
      this.loading = true
      try {
        const response = await employeeService.getEmployees()
        this.employees = response.data
      } catch (error) {
        this.error = error
      } finally {
        this.loading = false
      }
    },
    async createEmployee(employee) {
      try {
        const response = await employeeService.createEmployee(employee)
        this.employees.push(response.data)
        return response.data
      } catch (error) {
        throw error
      }
    },
    async updateEmployee(id, employee) {
      try {
        const response = await employeeService.updateEmployee(id, employee)
        const index = this.employees.findIndex(e => e.id === id)
        if (index !== -1) {
          this.employees.splice(index, 1, response.data)
        }
        return response.data
      } catch (error) {
        throw error
      }
    },
    async deleteEmployee(id) {
      try {
        await employeeService.deleteEmployee(id)
        this.employees = this.employees.filter(e => e.id !== id)
      } catch (error) {
        throw error
      }
    }
  }
})