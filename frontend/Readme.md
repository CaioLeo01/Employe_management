# 🚀 Sistema de Gestão de Empregados e Cargos (Frontend)

Frontend desenvolvido como parte do teste técnico, conectado à API de backend para gerenciamento de empregados e cargos. A aplicação permite operações de **CRUD completo**, com **validações de formulário**, **feedback visual** e **modo escuro/claro**.

---

## 📌 Funcionalidades

- ✅ Listar empregados e cargos
- ✅ Cadastrar, editar e excluir cargos
- ✅ Cadastrar, editar e excluir empregados
- ✅ Validação de dados antes do envio
- ✅ Exibição de mensagens de erro, sucesso e carregamento
- ✅ Interface 100% responsiva
- ✅ Alternância entre tema claro e escuro

---

## 🛠️ Tecnologias Utilizadas

- [Vue 3 + TypeScript](https://vuejs.org/)
- [Vuetify (UI Framework)](https://vuetifyjs.com/)
- [Axios (requisições HTTP)](https://axios-http.com/)
- [Vite (empacotador)](https://vitejs.dev/)

---

## 📦 Instalação Local

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
Instale as dependências:

bash
Copiar
Editar
npm install
Execute a aplicação:

bash
Copiar
Editar
npm run dev
⚠️ Importante: certifique-se de que a API backend está ativa em http://localhost:3000 (ou o endereço configurado no seu ambiente).

🌐 Backend
A aplicação depende de uma API RESTful previamente desenvolvida para:

GET /api/cargos

POST /api/cargos

PUT /api/cargos/:id

DELETE /api/cargos/:id

GET /api/empregados

POST /api/empregados

PUT /api/empregados/:id

DELETE /api/empregados/:id

📸 Screenshots
📱 Interface Responsiva
Modo Claro	Modo Escuro

🎯 Diferenciais Implementados
✅ Temas dinâmicos (modo claro/escuro)

✅ Feedback por snackbar em todas as operações

✅ Validações no frontend antes de qualquer requisição

✅ Estilização com Vuetify e animações suaves

✅ Organização por métodos e componentes

📁 Organização de Pastas
plaintext
Copiar
Editar
src/
├── components/          # Componentes reutilizáveis (ex: formulário, tabela)
├── views/               # Views principais da aplicação
├── assets/              # Ícones, logos, imagens
├── App.vue              # Root do app
├── main.ts              # Entry point
public/
├── screenshot-light.png # Screenshot do tema claro
├── screenshot-dark.png  # Screenshot do tema escuro
🧪 Como Testar
Suba a API backend localmente

Inicie o frontend

Acesse http://localhost:5173

Teste todas as funcionalidades: cadastrar, editar, excluir, trocar temas, etc.

📃 Licença
Este projeto está licenciado sob a MIT License. Sinta-se à vontade para usá-lo como base.