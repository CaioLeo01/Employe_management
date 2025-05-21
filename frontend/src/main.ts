import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify' // A extensão .ts é opcional
import 'vuetify/styles' // ✅ Importe ESTE primeiro
import '@mdi/font/css/materialdesignicons.css' // Importação correta dos ícones

const app = createApp(App)
app.use(vuetify)
app.mount('#app')