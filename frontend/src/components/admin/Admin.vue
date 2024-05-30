
<template>
  <div>
    <h1>Admin</h1>
    <div v-if="!authenticated">
      <input type="password" v-model="pin" placeholder="Введите пин-код">
      <button @click="checkPin">Войти</button>
    </div>
    <div v-else>
      <router-link to="/chapters">
        <button>Темы</button>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pin: '',
      authenticated: false,
      correctPin: '0000'
    };
  },
  methods: {
    checkPin() {
      if (this.pin === this.correctPin) {
        this.authenticated = true;
      } else {
        // Если пин-код неверный, можно вернуть пользователя на предыдущую страницу
        this.$router.go(-1);
      }
    }
  }
};
</script>

<style scoped>
/* Ваш стиль */
</style>
