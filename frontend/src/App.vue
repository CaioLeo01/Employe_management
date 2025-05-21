```vue
<template>
  <v-app>
    <v-main>
      <v-container class="py-8" style="max-width: 1200px;">
        <!-- Botão de alternância de tema animado -->
        <v-btn 
          @click="toggleTheme"
          icon
          size="large"
          class="theme-toggle animate-slide"
          :color="$vuetify.theme.current.dark ? 'white' : 'black'"
        >
          <v-icon>{{ $vuetify.theme.current.dark ? 'mdi-weather-sunny' : 'mdi-weather-night' }}</v-icon>
        </v-btn>

        <v-row>
          <!-- Seção de Cargos -->
          <v-col cols="12" md="6">
            <v-card elevation="3" class="pa-6 mb-6">
              <v-card-title class="text-h4 text-center mb-6">
                <v-icon large left>mdi-account-tie</v-icon>
                Gestão de Cargos
              </v-card-title>

              <v-card-text>
                <v-form @submit.prevent="submitCargo" ref="cargoForm">
                  <v-text-field
                    v-model="cargo.name"
                    label="Nome do Cargo"
                    :rules="[v => !!v || 'Campo obrigatório']"
                    required
                    outlined
                    clearable
                    prepend-inner-icon="mdi-rename-box"
                    class="mb-4"
                  ></v-text-field>

                  <v-textarea
                    v-model="cargo.description"
                    label="Descrição do Cargo"
                    :rules="[v => !!v || 'Campo obrigatório']"
                    required
                    outlined
                    rows="3"
                    prepend-inner-icon="mdi-text-box"
                    class="mb-12"
                    :style="{ height: '56px' }"
                    auto-grow
                  ></v-textarea>

                  <div class="mt-12">
                    <v-btn
                      type="submit"
                      color="primary"
                      x-large
                      block
                      :loading="loadingCargo"
                      prepend-icon="mdi-content-save"
                    >
                      Salvar Cargo
                    </v-btn>
                  </div>
                </v-form>
              </v-card-text>

              <v-card-text>
                <v-table v-if="cargos.length > 0" class="cargo-table">
                  <thead>
                    <tr>
                      <th width="50"></th>
                      <th>Nome</th>
                      <th>Descrição</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="cargo in cargos" :key="'cargo-'+cargo.id">
                      <td>
                        <v-menu>
                          <template v-slot:activator="{ props }">
                            <v-btn
                              icon
                              v-bind="props"
                              variant="outlined"
                              color="black"
                              size="small"
                              style="border-radius: 4px;"
                            >
                              <v-icon size="20">mdi-dots-vertical</v-icon>
                            </v-btn>
                          </template>
                          <v-list>
                            <v-list-item
                              value="edit"
                              @click="editCargo(cargo)"
                            >
                              <template v-slot:prepend>
                                <v-icon>mdi-pencil</v-icon>
                              </template>
                              <v-list-item-title>Editar</v-list-item-title>
                            </v-list-item>
                            <v-list-item
                              value="delete"
                              @click="deleteCargo(cargo.id)"
                            >
                              <template v-slot:prepend>
                                <v-icon>mdi-delete</v-icon>
                              </template>
                              <v-list-item-title>Excluir</v-list-item-title>
                            </v-list-item>
                          </v-list>
                        </v-menu>
                      </td>
                      <td>{{ cargo.name }}</td>
                      <td>{{ cargo.description }}</td>
                    </tr>
                  </tbody>
                </v-table>
                <v-alert v-else type="info" class="mt-4">
                  Nenhum cargo cadastrado ainda
                </v-alert>
              </v-card-text>
            </v-card>
          </v-col>

          <!-- Seção de Empregados -->
          <v-col cols="12" md="6">
            <v-card elevation="3" class="pa-6">
              <v-card-title class="text-h4 text-center mb-6">
                <v-icon large left>mdi-account-multiple</v-icon>
                Gestão de Empregados
              </v-card-title>

              <v-card-text>
                <v-form @submit.prevent="submitEmpregado" ref="empregadoForm">
                  <v-text-field
                    v-model="empregado.name"
                    label="Nome Completo"
                    :rules="[v => !!v || 'Nome é obrigatório']"
                    required
                    outlined
                    clearable
                    prepend-inner-icon="mdi-account"
                    class="mb-4"
                  ></v-text-field>

                  <v-text-field
                    v-model="empregado.email"
                    label="E-mail"
                    :rules="[
                      v => !!v || 'E-mail é obrigatório',
                      v => /.+@.+\..+/.test(v) || 'E-mail deve ser válido'
                    ]"
                    required
                    outlined
                    prepend-inner-icon="mdi-email"
                    class="mb-4"
                  ></v-text-field>

                  <v-text-field
                    v-model="empregado.admissionDate"
                    label="Data de Admissão"
                    type="date"
                    :rules="[v => !!v || 'Data é obrigatória']"
                    required
                    outlined
                    prepend-inner-icon="mdi-calendar"
                    class="mb-4"
                  ></v-text-field>

                  <v-select
                    v-model="empregado.roleId"
                    :items="cargosOptions"
                    label="Cargo"
                    item-title="name"
                    item-value="id"
                    :rules="[v => !!v || 'Cargo é obrigatório']"
                    required
                    outlined
                    prepend-inner-icon="mdi-badge-account"
                    class="mb-6"
                  ></v-select>

                  <div class="mt-8">
                    <v-btn
                      type="submit"
                      color="primary"
                      x-large
                      block
                      :loading="loadingEmpregado"
                      prepend-icon="mdi-content-save"
                    >
                      Salvar Empregado
                    </v-btn>
                  </div>
                </v-form>
              </v-card-text>

              <v-card-text>
                <v-table v-if="empregados.length > 0" class="empregado-table">
                  <thead>
                    <tr>
                      <th width="50"></th>
                      <th>Nome</th>
                      <th>E-mail</th>
                      <th>Admissão</th>
                      <th>Cargo</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="empregado in empregados" :key="'empregado-'+empregado.id">
                      <td>
                        <v-menu>
                          <template v-slot:activator="{ props }">
                            <v-btn
                              icon
                              v-bind="props"
                              variant="outlined"
                              color="black"
                              size="small"
                              style="border-radius: 4px;"
                            >
                              <v-icon size="20">mdi-dots-vertical</v-icon>
                            </v-btn>
                          </template>
                          <v-list>
                            <v-list-item
                              value="edit"
                              @click="editEmpregado(empregado)"
                            >
                              <template v-slot:prepend>
                                <v-icon>mdi-pencil</v-icon>
                              </template>
                              <v-list-item-title>Editar</v-list-item-title>
                            </v-list-item>
                            <v-list-item
                              value="delete"
                              @click="deleteEmpregado(empregado.id)"
                            >
                              <template v-slot:prepend>
                                <v-icon>mdi-delete</v-icon>
                              </template>
                              <v-list-item-title>Excluir</v-list-item-title>
                            </v-list-item>
                          </v-list>
                        </v-menu>
                      </td>
                      <td>{{ empregado.name }}</td>
                      <td>{{ empregado.email }}</td>
                      <td>{{ formatDate(empregado.admissionDate) }}</td>
                      <td>{{ getCargoName(empregado.roleId) }}</td>
                    </tr>
                  </tbody>
                </v-table>
                <v-alert v-else type="info" class="mt-4">
                  Nenhum empregado cadastrado ainda
                </v-alert>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>

      <!-- Diálogo de edição de Cargo -->
      <v-dialog v-model="editCargoDialog" max-width="600">
        <v-card>
          <v-card-title>Editar Cargo</v-card-title>
          <v-card-text>
            <v-text-field
              v-model="editingCargo.name"
              label="Nome do Cargo"
              :rules="[v => !!v || 'Campo obrigatório']"
              required
              outlined
              class="mb-4"
            ></v-text-field>

            <v-textarea
              v-model="editingCargo.description"
              label="Descrição do Cargo"
              :rules="[v => !!v || 'Campo obrigatório']"
              required
              outlined
              rows="3"
              auto-grow
            ></v-textarea>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue-darken-1" variant="text" @click="editCargoDialog = false">
              Cancelar
            </v-btn>
            <v-btn color="blue-darken-1" variant="text" @click="updateCargo">
              Salvar
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <!-- Diálogo de edição de Empregado -->
      <v-dialog v-model="editEmpregadoDialog" max-width="600">
        <v-card>
          <v-card-title>Editar Empregado</v-card-title>
          <v-card-text>
            <v-text-field
              v-model="editingEmpregado.name"
              label="Nome Completo"
              :rules="[v => !!v || 'Nome é obrigatório']"
              required
              outlined
              class="mb-4"
            ></v-text-field>

            <v-text-field
              v-model="editingEmpregado.email"
              label="E-mail"
              :rules="[
                v => !!v || 'E-mail é obrigatório',
                v => /.+@.+\..+/.test(v) || 'E-mail deve ser válido'
              ]"
              required
              outlined
              class="mb-4"
            ></v-text-field>

            <v-text-field
              v-model="editingEmpregado.admissionDate"
              label="Data de Admissão"
              type="date"
              :rules="[v => !!v || 'Data é obrigatória']"
              required
              outlined
              class="mb-4"
            ></v-text-field>

            <v-select
              v-model="editingEmpregado.roleId"
              :items="cargosOptions"
              label=" Cargo"
              item-title="name"
              item-value="id"
              :rules="[v => !!v || 'Cargo é obrigatório']"
              required
              outlined
              class="mb-6"
            ></v-select>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue-darken-1" variant="text" @click="editEmpregadoDialog = false">
              Cancelar
            </v-btn>
            <v-btn color="blue-darken-1" variant="text" @click="updateEmpregado">
              Salvar
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-snackbar v-model="snackbar" :timeout="3000" :color="snackbarColor">
        {{ snackbarText }}
        <template v-slot:actions>
          <v-btn variant="text" @click="snackbar = false">Fechar</v-btn>
        </template>
      </v-snackbar>
    </v-main>
  </v-app>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import axios from 'axios';

interface Cargo {
  id: number;
  name: string;
  description: string;
}

interface Empregado {
  id?: number;
  name: string;
  email: string;
  admissionDate: string;
  roleId: number | null;
  role?: {
    id: number;
    name: string;
  };
}

export default defineComponent({
  data() {
    return {
      // Dados para Cargos
      cargo: {
        name: '',
        description: ''
      },
      editingCargo: {
        id: null as number | null,
        name: '',
        description: ''
      },
      editCargoDialog: false,
      cargos: [] as Cargo[],
      loadingCargo: false,

      // Dados para Empregados
      empregado: {
        name: '',
        email: '',
        admissionDate: new Date().toISOString().substr(0, 10),
        roleId: null as number | null
      } as Empregado,
      editingEmpregado: {
        id: null as number | null,
        name: '',
        email: '',
        admissionDate: '',
        roleId: null as number | null
      } as Empregado,
      editEmpregadoDialog: false,
      empregados: [] as Empregado[],
      loadingEmpregado: false,

      // Notificações
      snackbar: false,
      snackbarText: '',
      snackbarColor: 'success'
    }
  },
  computed: {
    cargosOptions() {
      return this.cargos.map(cargo => ({
        id: cargo.id,
        name: cargo.name
      }));
    }
  },
  methods: {
    toggleTheme() {
      this.$vuetify.theme.global.name = this.$vuetify.theme.current.dark ? 'light' : 'dark';
      const themeBtn = document.querySelector('.theme-toggle');
      if (themeBtn) {
        themeBtn.classList.add('animate');
        setTimeout(() => themeBtn.classList.remove('animate'), 500);
      }
    },

    // Métodos para Cargos
    async submitCargo() {
      if (!(this.$refs.cargoForm as any).validate()) return;
      
      this.loadingCargo = true;
      
      try {
        const response = await axios.post('/api/cargos', this.cargo);
        
        this.snackbarText = 'Cargo cadastrado com sucesso!';
        this.snackbarColor = 'success';
        this.snackbar = true;
        
        (this.$refs.cargoForm as any).reset();
        this.fetchCargos();
      } catch (error) {
        this.handleError(error, 'cargo');
      } finally {
        this.loadingCargo = false;
      }
    },

    async fetchCargos() {
      try {
        const response = await axios.get('/api/cargos');
        this.cargos = response.data;
      } catch (error) {
        this.handleError(error, 'cargo', 'fetch');
      }
    },
    
    editCargo(cargo: Cargo) {
      this.editingCargo = { ...cargo };
      this.editCargoDialog = true;
    },

    async updateCargo() {
      try {
        await axios.put(`/api/cargos/${this.editingCargo.id}`, this.editingCargo);
        this.snackbarText = 'Cargo atualizado com sucesso!';
        this.snackbarColor = 'success';
        this.snackbar = true;
        this.editCargoDialog = false;
        this.fetchCargos();
      } catch (error) {
        this.handleError(error, 'cargo');
      }
    },

    async deleteCargo(id: number) {
      try {
        await axios.delete(`/api/cargos/${id}`);
        this.snackbarText = 'Cargo removido com sucesso';
        this.snackbarColor = 'success';
        this.snackbar = true;
        this.fetchCargos();
        this.fetchEmpregados();
      } catch (error) {
        this.handleError(error, 'cargo');
      }
    },

    // Métodos para Empregados
    async submitEmpregado() {
      if (!(this.$refs.empregadoForm as any).validate()) return;
      
      this.loadingEmpregado = true;
      
      try {
        const empregadoData = {
          ...this.empregado,
          role: { id: this.empregado.roleId }
        };
        
        const response = await axios.post('/api/empregados', empregadoData);
        
        this.snackbarText = 'Empregado cadastrado com sucesso!';
        this.snackbarColor = 'success';
        this.snackbar = true;
        
        (this.$refs.empregadoForm as any).reset();
        this.empregado.admissionDate = new Date().toISOString().substr(0, 10);
        this.empregado.roleId = null;

        // Manually add the new employee to the table to avoid a fetch delay
        this.empregados.push({
          id: response.data.id,
          name: response.data.name,
          email: response.data.email,
          admissionDate: response.data.admissionDate,
          roleId: response.data.role?.id || this.empregado.roleId
        });
      } catch (error) {
        this.handleError(error, 'empregado');
      } finally {
        this.loadingEmpregado = false;
      }
    },

    async fetchEmpregados() {
      try {
        const response = await axios.get('/api/empregados');
        this.empregados = response.data.map((emp: any) => ({
          id: emp.id,
          name: emp.name || '',
          email: emp.email || '',
          admissionDate: emp.admissionDate ? 
            (emp.admissionDate.includes('T') ? emp.admissionDate.split('T')[0] : emp.admissionDate) : 
            new Date().toISOString().substr(0, 10),
          roleId: emp.role?.id || emp.roleId || null
        }));
      } catch (error) {
        this.handleError(error, 'empregado', 'fetch');
      }
    },
    
    editEmpregado(empregado: Empregado) {
      this.editingEmpregado = { 
        ...empregado,
        roleId: empregado.role?.id || empregado.roleId,
        admissionDate: empregado.admissionDate.split('T')[0]
      };
      this.editEmpregadoDialog = true;
    },

    async updateEmpregado() {
      try {
        const empregadoData = {
          ...this.editingEmpregado,
          role: { id: this.editingEmpregado.roleId }
        };
        
        const response = await axios.put(`/api/empregados/${this.editingEmpregado.id}`, empregadoData);
        this.snackbarText = 'Empregado atualizado com sucesso!';
        this.snackbarColor = 'success';
        this.snackbar = true;
        this.editEmpregadoDialog = false;
        
        const index = this.empregados.findIndex(e => e.id === this.editingEmpregado.id);
        if (index !== -1) {
          this.empregados.splice(index, 1, {
            ...response.data,
            roleId: response.data.role?.id || response.data.roleId || this.editingEmpregado.roleId,
            admissionDate: response.data.admissionDate ? 
              (response.data.admissionDate.includes('T') ? response.data.admissionDate.split('T')[0] : response.data.admissionDate) : 
              this.editingEmpregado.admissionDate
          });
        }
      } catch (error) {
        this.handleError(error, 'empregado');
      }
    },

    async deleteEmpregado(id: number) {
      try {
        await axios.delete(`/api/empregados/${id}`);
        this.snackbarText = 'Empregado removido com sucesso';
        this.snackbarColor = 'success';
        this.snackbar = true;
        this.empregados = this.empregados.filter(e => e.id !== id);
      } catch (error) {
        this.handleError(error, 'empregado');
      }
    },

    // Métodos auxiliares
    handleError(error: any, tipo: 'cargo' | 'empregado', operacao: string = 'manipular') {
      if (axios.isAxiosError(error) && error.response) {
        switch (error.response.status) {
          case 409:
            this.snackbarText = tipo === 'empregado' 
              ? 'Erro: Este e-mail já está em uso por outro empregado' 
              : 'Erro: Já existe um cargo com este nome cadastrado';
            break;
          case 400:
            this.snackbarText = 'Erro: ' + (error.response.data.message || 'Dados inválidos');
            break;
          default:
            this.snackbarText = `Erro ao ${operacao} ${tipo === 'empregado' ? 'empregado' : 'cargo'}: ` + 
              (error.response.data.message || error.message);
        }
        console.error(`Erro ${tipo} (${error.response.status}):`, error.response.data);
      } else {
        this.snackbarText = 'Erro de conexão com o servidor';
        console.error('Erro de conexão:', error);
      }
      this.snackbarColor = 'error';
      this.snackbar = true;
    },

    formatDate(dateString: string) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleDateString('pt-BR');
    },

    getCargoName(roleId: number | null) {
      if (!roleId) return 'Não definido';
      const cargo = this.cargos.find(c => c.id === roleId);
      return cargo ? cargo.name : 'Não definido';
    }
  },
  mounted() {
    this.fetchCargos();
    this.fetchEmpregados();
  }
});
</script>

<style scoped>
.v-card {
  border-radius: 12px;
}

.v-btn {
  letter-spacing: 0.5px;
  font-weight: 600;
  transition: transform 0.2s;
}

.v-btn:hover {
  transform: translateY(-2px);
}

.v-text-field, .v-textarea {
  border-radius: 8px;
}

.v-table {
  margin-top: 20px;
  border-radius: 8px;
  overflow: hidden;
}

.v-table thead th {
  background-color: #f5f5f5;
  font-weight: 600;
}

.v-table tbody tr:hover {
  background-color: #f5f5f5;
}

.cargo-table, .empregado-table {
  width: 100%;
}

.empregado-table td:nth-child(3), 
.empregado-table th:nth-child(3) {
  white-space: nowrap;
}

.empregado-table td:nth-child(4), 
.empregado-table th:nth-child(4) {
  white-space: nowrap;
  width: 120px;
}

/* Estilos para o botão de tema */
.theme-toggle {
  position: fixed;
  top: 10px;
  right: 0px;
  z-index: 1000;
  transition: all 0.3s ease;
}

.theme-toggle.animate {
  animation: slide 0.5s ease;
}

@keyframes slide {
  0% { transform: translateX(0); }
  25% { transform: translateX(-10px); }
  50% { transform: translateX(10px); }
  75% { transform: translateX(-5px); }
  100% { transform: translateX(0); }
}

/* Espaçamento aumentado para o botão de salvar cargo */
.v-textarea.mb-12 {
  margin-bottom: 98px !important;
}

.mt-12 {
  margin-top: 48px !important;
}
</style>
```