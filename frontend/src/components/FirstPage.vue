<template>
  <div class="container">
    <div class="welcome">
      <h2>Привет</h2>
      <div id="usercard">
        <h2 v-if="userInfo">{{ userInfo.username }}</h2>
        <p v-else>No user information available</p>
      </div>
    </div>
    <div class="buttons">
      <button class="start-game-btn" @click="createNewUser">Начать игру</button>
      <router-link v-if="userInfo && (
        (userInfo.id === '11111111' && userInfo.username === 'defaultUsername') ||
        (userInfo.id === '239326010' && userInfo.username === 'N911')
      )" to="/admin/">
        <button class="admin-btn">Администратор</button>
      </router-link>
    </div>
    <UserStatisticsDisplay />
  </div>
</template>

<script>
import { getUserInfo, setUserInfo, setDefaultUserInfo } from '@/components/userData/userData';
import DataService from '@/service/game/UserDataService';
import UserStatisticsDisplay from '@/components/UserStatisticsDisplay'; // Убедитесь, что путь к вашему компоненту корректен

export default {
  components: {
    UserStatisticsDisplay,
  },
  data() {
    return {
      telegramAppChatLabs: null,
      userInfo: null,
    };
  },

  created() {
    this.telegramAppChatLabs = window.Telegram.WebApp;
    this.telegramAppChatLabs.expand();

    const user = this.telegramAppChatLabs.initDataUnsafe?.user;

    if (user) {
      setUserInfo(user);
      this.userInfo = getUserInfo();
    } else {
      setDefaultUserInfo();
      this.userInfo = getUserInfo();
    }
  },
  methods: {
    createNewUser() {
      const { id: userTelId, username } = getUserInfo();

      DataService.createUser({ userTelId, username })
          .then((response) => {
            this.userInfo = response.data;
            this.$router.push('/game/');
          })
          .catch((error) => {
            console.error('Ошибка при создании пользователя:', error.response.data);
            this.userInfo = 'Ошибка при создании пользователя';
          });
    },
  },
};
</script>

<style>


</style>
