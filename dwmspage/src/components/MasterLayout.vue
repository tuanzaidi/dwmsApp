<template>
  <div>
    <!-- Header or Navbar -->
    <nav class="navbar navbar-expand-lg bg-primary" data-bs-theme="dark">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">DWMS</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarColor01">
          <ul class="navbar-nav me-auto">
            <li class="nav-item">
              <router-link :class="getLinkClass('home')" to="/">Home</router-link>
            </li>
            <li class="nav-item">
              <router-link :class="getLinkClass('mirf')" to="/mirf">Mirf</router-link>
            </li>
            <li class="nav-item">
              <router-link :class="getLinkClass('about')" to="/about">About</router-link>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Dropdown</a>
              <div class="dropdown-menu">
                <a class="dropdown-item" href="#">Action</a>
                <a class="dropdown-item" href="#">Another action</a>
                <a class="dropdown-item" href="#">Something else here</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">Separated link</a>
              </div>
            </li>
          </ul>
          <div class="d-flex">
            <ul class="navbar-nav me-auto">              
              <div v-if="!isAuthenticated">
                <li class="nav-item">
                  <router-link :class="getLinkClass('login')"  to="/login">Login</router-link>
                </li>
              </div>
              <div v-else>
                <li class="nav-item">
                  <a class="nav-link" href="#">Welcome, {{ username }}!</a>
                </li>
              </div>              
              <li class="nav-item" v-if="isAuthenticated">
                  <a class="nav-link" href="#" @click="logout">Logout</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </nav>

    <!-- Main Content -->
    <main class="container router-view">
      <router-view></router-view>  
    </main>

    <!-- Footer -->
    <div class="d-flex flex-column text-center justify-content-between py-2 px-2 px-xl-2 bg-primary">
      <footer class="text-center text-white mb-md-0">
        <p>Copyright {{ currentYear }} TIME dotCom Bhd 199601040939 (413292-P). All rights reserved.</p>
      </footer>
    </div>
    
  </div>
</template>

<script>
export default {
  name: 'MasterLayout',
  computed: {
    currentYear() {
      return new Date().getFullYear();
    },
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    },
    username() {
      return this.$store.getters.getUser;
    }
  },
  methods: {
    getLinkClass(routeName) {
      return {
        'nav-link': true,
        'active': this.$route.name === routeName
      };
    },

    logout() {
      // Remove the token from local storage
      localStorage.removeItem('authToken');
      
      // Clear user data in Vuex store
      this.$store.dispatch('logout');
      this.$store.dispatch('logout');
      
      // Redirect to the login page
      this.$router.push({ name: 'login' });
    }
  },
};
</script>

<style scoped>
/* Add custom styles here */
</style>
  