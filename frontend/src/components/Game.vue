<template>
  <div class="container">
    <div v-if="userInfo">
      <h3> {{ userInfo.username }}</h3>
      <!--<p>ID пользователя: {{ userInfo.id }}</p>-->
    </div>
    <h4>На какую вакансию будем проходить собеседование ? </h4>

    <div class="chapter-buttons">
      <button v-for="chapter in chapters" :key="chapter.id" class="btn btn-info btn-lg btn-block" @click="allTopics(chapter.id)">
        {{ chapter.chapterName }}
      </button>
    </div>

    <div class="row mt-3">
      <div class="col-md-12">
        <button class="btn btn-danger btn-lg btn-block custom-btn" @click="goBackToGame()">Назад</button>
      </div>
    </div>
  </div>
</template>

<script>
import ChapterService from '@/service/admin/ChapterDataService';
import { getUserInfo } from './userData/userData';
export default {
  name: 'Game',
  data() {
    return {
      chapters: [],
      message: '',
      userInfo: null,
    };
  },
  methods: {
    loadUserInfo() {
      this.userInfo = getUserInfo(); // Получаем информацию о пользователе из сервиса
    },
    getChapters() {
      ChapterService.getAllChapters()
          .then((response) => {
            this.chapters = response.data;
          })
          .catch((error) => {
            console.error(error);
            // Добавьте обработку ошибок, например, отображение сообщения об ошибке
          });
    },
    allTopics(chapterId) {
      this.$router.push({ path: '/gametopics', query: { chapterId: chapterId } });
    },
    goBackToGame() {
      this.$router.push('/');
    },
  },
  mounted() {
    this.loadUserInfo();

    // Проверяем, определен ли userInfo после загрузки
    if (!this.userInfo) {
      this.$router.push('/'); // Замените '/some_other_page' на ваш путь для перенаправления
    } else {
      this.getChapters();
    }
  },
};
</script>
