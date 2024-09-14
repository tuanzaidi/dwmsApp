import { createRouter, createWebHistory } from 'vue-router';
import MasterLayout from '../components/MasterLayout.vue';
import HomePage from '../views/HomePage.vue';
import AboutPage from '../views/AboutPage.vue';
import MirfPage from '../views/MirfPage.vue';

import LoginPage from '../views/LoginPage.vue';
import store from '../store';

const routes = [
  {
    path: '/',
    component: MasterLayout,
    children: [
      {
        path: '',
        name: 'home',
        component: HomePage,        
        meta: { title: 'Home - DWMS' },
      },
      {
        path: 'about',
        name: 'about',
        component: AboutPage,
        meta: { title: 'About - DWMS' },
      },
      {
        path: 'mirf',
        name: 'mirf',
        component: MirfPage,
        meta: { title: 'Mirf - DWMS', requiresAuth: true },
      },
      {
        path: 'login',
        name: 'login',
        component: LoginPage,
        meta: { title: 'Login - DWMS' },
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

// Global navigation guard to set document title
router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title;
  } else {
    document.title = 'DWMS'; // Fallback title
  }

  if (to.meta.requiresAuth && !store.getters.isAuthenticated) {
    next({ name: 'login' });
  } else {
    next();
  }
});

export default router;
