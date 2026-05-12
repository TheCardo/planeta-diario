import { createRouter, createWebHistory } from 'vue-router'
import AuthPage from '@/pages/AuthPage.vue'
import HomePage from '@/pages/HomePage.vue'

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
      path: '/profile',
      name: 'profile',
      component: () => import('@/pages/ProfilePage.vue')
    }
  ]
})

export default router