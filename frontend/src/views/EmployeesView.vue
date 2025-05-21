<template>
  <v-container>
    <EmployeeList
      :employees="employees"
      :loading="loading"
      :error="error"
      @edit="openEditDialog"
      @create="openCreateDialog"
      @delete="deleteEmployee"
    />

    <EmployeeForm
      v-model="showDialog"
      :employee="currentEmployee"
      :roles="roles"
      @submit="handleSubmit"
    />
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useEmployeeStore } from '@/stores/employeeStore'
import { useRoleStore } from '@/stores/roleStore'
import EmployeeList from '@/components/employees/EmployeeList.vue'
import EmployeeForm from '@/components/employees/EmployeeForm.vue'

const employeeStore = useEmployeeStore()
const roleStore = useRoleStore()

const { employees, loading, error } = storeToRefs(employeeStore)
const { roles } = storeToRefs(roleStore)

const showDialog = ref(false)
const currentEmployee = ref(null)

onMounted(() => {
  employeeStore.fetchEmployees()
  roleStore.fetchRoles()
})

const openEditDialog = (employee) => {
  currentEmployee.value = employee
  showDialog.value = true
}

const openCreateDialog = () => {
  currentEmployee.value = null
  showDialog.value = true
}

const handleSubmit = async (formData) => {
  try {
    if (currentEmployee.value) {
      await employeeStore.updateEmployee(currentEmployee.value.id, formData)
    } else {
      await employeeStore.createEmployee(formData)
    }
    showDialog.value = false
  } catch (error) {
    console.error('Error:', error)
  }
}

const deleteEmployee = async (id) => {
  if (confirm('Tem certeza que deseja excluir este funcionário?')) {
    await employeeStore.deleteEmployee(id)
  }
}
</script>