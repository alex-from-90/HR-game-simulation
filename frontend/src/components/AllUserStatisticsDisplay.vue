<template>
  <div>
    <h2>Статистика</h2>
    <!-- Отображение всей статистики -->
    <div v-if="allUserProgress">
      <h3>Вся статистика</h3>
      <ul>
        <li v-for="(progress, userId) in allUserProgress" :key="userId">
          <p>{{ userId }}</p>
          <p>Прогресс: {{ progress }}</p>
        </li>
      </ul>
    </div>

    <!-- Отображение топ-10 статистики -->
    <div v-if="top10Users">
      <h3>Топ-10 пользователей</h3>
      <ol>
        <li v-for="(user, index) in top10Users" :key="user.userId">
          <p>{{ index + 1 }}. {{ user.username }} - {{ user.progress }}</p>
        </li>
      </ol>
    </div>
  </div>
</template>

<script>
import DataService from '@/service/game/UserDataService';

export default {
  data() {
    return {
      allUserProgress: null,
      top10Users: null,
    };
  },
  created() {
    this.fetchAllUserProgress();
    this.fetchTop10Users();
  },
  methods: {
    fetchAllUserProgress() {
      DataService.getAllUserProgress()
          .then((data) => {
            this.allUserProgress = data;
          })
          .catch((error) => {
            console.error('Ошибка при получении всей статистики:', error);
          });
    },
    fetchTop10Users() {
      DataService.getTop10Users()
          .then((data) => {
            this.top10Users = data;
          })
          .catch((error) => {
            console.error('Ошибка при получении топ-10 статистики:', error);
          });
    },
  },
};
</script>

<style>
/* Ваш стиль */
</style>
