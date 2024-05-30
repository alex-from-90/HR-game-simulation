<template>
  <div class="container">


    <!--  <p v-if="userInfo"> {{ userInfo.username }}</p> -->
   <!--  <p>ID пользователя: {{ userInfo.id }}</p> -->

    <h3>{{ chapterTitle }}</h3>

    <div class="table-responsive">
      <table class="table table-striped" v-if="userInfo">
        <thead>
        <tr>

          <th>{{ userInfo.username }}, давайте пройдёмся по основным темам</th>



        </tr>
        </thead>
        <tbody>
        <tr v-for="topic in topics" :key="topic.id">
          <td>
            <button class="btn btn-info btn-lg btn-block" @click="viewQuestions(chapterId, topic.id)">
              {{ topic.topicName }} {{topic.id}}
            </button>
            <div v-if="topic.progress && typeof topic.progress.progressPercentage !== 'undefined'">
              <!-- Проверка на нулевой прогресс -->
              <div v-if="topic.progress.progressPercentage === 0">
                <p>Прогресс 0%</p>
              </div>
              <!-- Отображение прогресса в виде шкалы Bootstrap, если прогресс не равен нулю -->
              <div v-else>
                <div class="progress">
                  <div class="progress-bar" :style="{ width: topic.progress.progressPercentage + '%' }" role="progressbar" :aria-valuenow="topic.progress.progressPercentage" aria-valuemin="0" aria-valuemax="100">
                    {{ topic.progress.progressPercentage }}%
                  </div>
                </div>
              </div>
            </div>
            <div v-else>
              <!-- Обработка, если прогресс не содержит проценты -->
              <p>Вы еще не начали этот раздел</p>
            </div>
          </td>
        </tr>
        </tbody>

      </table>
    </div>
    <div v-if="topics.length === 0">
      <h1>В главе пусто</h1>
    </div>

    <button class="btn btn-secondary" @click="goBack">Назад к главам</button>
  </div>
</template>

<script>
import TopicService from '@/service/admin/TopicDataService';
import {getUserInfo} from './userData/userData';
import ChapterDataService from '@/service/admin/ChapterDataService';
import DataService from '@/service/game/UserDataService';

export default {
  name: 'GameTopics',
  data() {
    return {
      topics: [],
      chapterId: null,
      chapterTitle: '',
      userInfo: null,

    };
  },
  methods: {
    loadUserInfo() {
      this.userInfo = getUserInfo(); // Получаем информацию о пользователе из сервиса
    },
    loadChapterInfo() {
      const params = new URLSearchParams(window.location.search);
      this.chapterId = params.get('chapterId');

      ChapterDataService.getChapterById(this.chapterId)
          .then((response) => {
            this.chapterTitle = response.data.chapterName;
          })
          .catch((error) => {
            console.error('Chapter Data Error:', error);
          });
    },
    async loadTopics() {
      const params = new URLSearchParams(window.location.search);
      this.chapterId = params.get('chapterId');
      this.chapterTitle = params.get('chapterTitle');

      try {
        const topicsResponse = await TopicService.getAllTopics(this.chapterId);
        this.topics = topicsResponse.data;

        const userId = this.userInfo.id; // Получаем ID пользователя

        // Получаем прогресс для каждого топика
        for (const topic of this.topics) {
          try {
            // Сохраняем прогресс для текущего топика
            topic.progress = await DataService.getProgressByTopic(userId, topic.id); // предположим, что в ответе есть свойство progress
          } catch (error) {
            console.error(error);
            // Если произошла ошибка при получении прогресса, установите для топика значение по умолчанию или обработайте ошибку соответственно
            topic.progress = null; // установите значение по умолчанию или обработайте ошибку
          }
        }
      } catch (error) {
        console.error(error);
      }
    },

    viewQuestions(chapterId, topicId) {
      this.$router.push({ path: '/gamequestions', query: { chapterId: chapterId, topicId: topicId } });
    },

    goBack() {
      this.$router.push('/game');
    },
  },

  mounted() {
    this.loadUserInfo();

    // Добавляем условную проверку на определение пользователя
    if (!this.userInfo) {
      this.$router.push('/'); // Замените '/some_other_page' на ваш путь для перенаправления
    } else {
      this.loadChapterInfo();
      this.loadTopics();
    }
  },
};
</script>

<style>
/* Стили, если требуется */
</style>