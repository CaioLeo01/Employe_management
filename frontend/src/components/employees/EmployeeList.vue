<template>
  <div>
    <v-btn color="primary" @click="$emit('create')" class="mb-4">
      Novo Funcionário
    </v-btn>

    <v-progress-linear v-if="loading" indeterminate></v-progress-linear>

    <v-alert v-if="error" type="error">{{ error }}</v-alert>

    <v-table v-if="employees.length > 0">
      <thead>
        <tr>
          <th>Nome</th>
          <th>Email</th>
          <th>Cargo</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="employee in employees" :key="employee.id">
          <td>{{ employee.name }}</td>
          <td>{{ employee.email }}</td>
          <td>{{ employee.role?.name }}</td>
          <td>
            <v-btn icon @click="$emit('edit', employee)">
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
            <v-btn icon @click="$emit('delete', employee.id)">
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </td>
        </tr>
      </tbody>
    </v-table>
  </div>
</template>

<script setup>
defineProps({
  employees: {
    type: Array,
    required: true
  },
  loading: {
    type: Boolean,
    default: false
  },
  error: {
    type: String,
    default: null
  }
})

defineEmits(['edit', 'delete', 'create'])
</script>