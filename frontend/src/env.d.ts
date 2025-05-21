// shims.d.ts
declare module '*.vue' {
  import { DefineComponent } from 'vue'
  const component: DefineComponent<{}, {}, any>
  export default component
}

declare module './plugins/vuetify' {
  import { Vuetify } from 'vuetify'
  const vuetify: Vuetify
  export default vuetify
}
