import { createRouter, createWebHistory } from 'vue-router'
import EmployeesView from '@/views/EmployeesView.vue'
import RolesView from '@/views/RolesView.vue'
import DashboardView from '@/views/DashboardView.vue'

const routes = [
  {
    path: '/',
    name: 'dashboard',
    component: DashboardView
  },
  {
    path: '/funcionarios',
    name: 'employees',
    component: EmployeesView
  },
  {
    path: '/cargos',
    name: 'roles',
    component: RolesView
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router