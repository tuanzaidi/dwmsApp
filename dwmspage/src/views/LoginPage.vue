<!-- src/components/Login.vue -->
<template>
  <div class="container-fluid h-custom vh-85">
      <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-md-9 col-lg-6 col-xl-5">
          <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
          class="img-fluid" alt="Sample image">
      </div>
      <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
          <form @submit.prevent="login">
              <div v-if="message" class="alert alert-danger" role="alert">{{ message }}</div>
              <!-- Username input -->
              <div data-mdb-input-init class="form-outline mb-4">
                  <input type="text" v-model="username" id="username" class="form-control form-control-lg"
                  placeholder="Enter username" />
              </div>  
              <!-- Password input -->
              <div data-mdb-input-init class="form-outline mb-3">
                  <input type="password" v-model="password" id="password" required class="form-control form-control-lg"
                  placeholder="Enter password" />
              </div>  
              <div class="d-flex justify-content-between align-items-center">
                  <!-- Checkbox -->
                  <div class="form-check mb-0">
                  <input class="form-check-input me-2" type="checkbox" value="" id="form2Example3" />
                  <label class="form-check-label" for="form2Example3">
                      Remember me
                  </label>
                  </div>
                  <a href="#!" class="text-body">Forgot password?</a>
              </div>
              
              <div class="text-center text-lg-start mt-4 pt-2">
                  <button  type="submit" data-mdb-button-init data-mdb-ripple-init class="btn btn-secondary btn-lg"
                  style="padding-left: 2.5rem; padding-right: 2.5rem;">Login</button>
              </div>
          </form>
      </div>
      </div>
  </div>
</template>

<script>
import apiClient from '../services/api';

export default {
  name: 'LoginPage',
  data() {
    return {
      username: '',
      password: '',
      message: ''
    };
  },
  methods: {
    async login() {
      try {
        const response = await apiClient.post('/api/login', {
          username: this.username,
          password: this.password
        });        
        // Check if the response contains a token
        if (response.data.token) {
          console.log(response.data.user);
          // Store the token in local storage
          localStorage.setItem('authToken', response.data.token);

          // Set user data in Vuex
          this.$store.dispatch('login', { user: response.data.user, token: response.data.token });
          
          // Redirect to a protected page
          this.$router.push({ name: 'home' });
        } else {
          this.message = response.data.message || 'Login failed';
        }
      } catch (error) {
        this.message = 'Login failed';
      }
    }
  }
};
</script>
  