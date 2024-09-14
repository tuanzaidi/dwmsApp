import { createStore } from 'vuex';

const store = createStore({
  state: {
    // Initialize from localStorage
    user: JSON.parse(localStorage.getItem('user')) || null,    
    authToken: localStorage.getItem('authToken') || null, 
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
      // Save user to localStorage
      localStorage.setItem('user', JSON.stringify(user)); 
    },
    clearUser(state) {
      state.user = null;
      // Remove user from localStorage
      localStorage.removeItem('user'); 
    },
    setAuthToken(state, token) {
      state.authToken = token;
      // Save token to localStorage
      localStorage.setItem('authToken', token); 
    },
    clearAuthToken(state) {
      state.authToken = null;
      // Remove token from localStorage
      localStorage.removeItem('authToken'); 
    }
  },
  actions: {
    login({ commit }, { user, token }) {
      commit('setUser', user);
      commit('setAuthToken', token);
    },
    logout({ commit }) {
      commit('clearUser');
      commit('clearAuthToken');
    }
  },
  getters: {
    isAuthenticated(state) {
      // Check if the token is present
      return !!state.authToken; 
    },
    getUser(state) {
      return state.user;
    },
  }
});

export default store;
