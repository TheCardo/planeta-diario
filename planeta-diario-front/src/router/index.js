import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'auth',
      component: () => import('@/pages/AuthPage.vue') //? Lazy loading 
    },
    {
      path: '/',
      name: 'home',
      component: () => import('@/pages/HomePage.vue')
    },
    {
      path: '/perfil',
      name: 'profile',
      component: () => import('@/pages/ProfilePage.vue')
    },
    {
      path: '/noticia',
      name: 'noticia',
      component: () => import('@/pages/NoticiaPage.vue')
    },
  ]
})

export default router