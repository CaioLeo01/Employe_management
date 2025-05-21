<template>
  <v-dialog v-model="dialog" max-width="600px">
    <v-card>
      <v-card-title>
        {{ editMode ? 'Editar Funcionário' : 'Novo Funcionário' }}
      </v-card-title>
      <v-card-text>
        <v-form @submit.prevent="submit">
          <v-text-field
            v-model="form.name"
            label="Nome"
            :error-messages="errors.name"
          ></v-text-field>

          <v-text-field
            v-model="form.email"
            label="Email"
            type="email"
            :error-messages="errors.email"
          ></v-text-field>

          <v-text-field
            v-model="form.admissionDate"
            label="Data de Admissão"
            type="date"
            :error-messages="errors.admissionDate"
          ></v-text-field>

          <v-select
            v-model="form.roleId"
            :items="roles"
            item-title="name"
            item-value="id"
            label="Cargo"
            :error-messages="errors.roleId"
          ></v-select>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue-darken-1" variant="text" @click="close">
              Cancelar
            </v-btn>
            <v-btn
              color="blue-darken-1"
              variant="text"
              type="submit"
              :loading="submitting"
            >
              Salvar
            </v-btn>
          </v-card-actions>
        </v-form>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { useVuelidate } from '@vuelidate/core'
import { required, email } from '@vuelidate/validators'

const props = defineProps({
  modelValue: Boolean,
  employee: Object,
  roles: Array
})

const emit = defineEmits(['update:modelValue', 'submit'])

const dialog = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const form = ref({
  name: '',
  email: '',
  admissionDate: '',
  roleId: null
})

const rules = {
  name: { required },
  email: { required, email },
  admissionDate: { required },
  roleId: { required }
}

const v$ = useVuelidate(rules, form)

const editMode = computed(() => !!props.employee)
const submitting = ref(false)
const errors = ref({})

watch(() => props.employee, (employee) => {
  if (employee) {
    form.value = {
      name: employee.name,
      email: employee.email,
      admissionDate: employee.admissionDate,
      roleId: employee.role?.id
    }
  } else {
    resetForm()
  }
})

const resetForm = () => {
  form.value = {
    name: '',
    email: '',
    admissionDate: '',
    roleId: null
  }
  errors.value = {}
}

const close = () => {
  dialog.value = false
  resetForm()
}

const submit = async () => {
  submitting.value = true
  const isValid = await v$.value.$validate()
  
  if (!isValid) {
    errors.value = v$.value.$errors.reduce((acc, error) => {
      acc[error.$property] = error.$message
      return acc
    }, {})
    submitting.value = false
    return
  }

  emit('submit', form.value)
  submitting.value = false
}
</script>