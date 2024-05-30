<template>
  <div>
    <h2>Статистика</h2>

    <!-- Анимированный спойлер для статистики -->
    <div class="spoiler">
      <button @click="toggleStatistics" :class="{ 'active': showStatistics }">Показать/скрыть статистику</button>
      <transition name="fade">
        <div v-show="showStatistics" class="statistics-content" :class="{ show: showStatistics }">
        <!-- Вывод статистики -->
          <div v-if="currentUserStatistics !== null">
            <div v-if="Object.keys(currentUserStatistics).length > 0">
              <div v-for="(chapterStats, chapterName) in currentUserStatistics" :key="chapterName">
                <h3>Глава - {{ chapterName }}</h3>
                <div v-for="(topicStats, topicName) in chapterStats" :key="topicName">
                  <h4>Топик - {{ topicName }}</h4>
                  <p>Прогресс: {{ topicStats.progressPercentage }}%</p>
                  <p>Правильные ответы: {{ topicStats.correctAnswers }}</p>
                  <p>Неправильные ответы: {{ topicStats.wrongAnswers }}</p>
                  <!-- Другие свойства статистики, если есть -->
                </div>
              </div>
            </div>
            <div v-else>
              <p>Вы еще не прошли ни одного собеседования</p>
            </div>
          </div>
          <div v-else>
            <p>Статистика не найдена</p>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
import DataService from '@/service/game/UserDataService';
import { getUserInfo } from '@/components/userData/userData';
import {Transition} from "vue";

export default {
  components: {
    Transition, // Добавляем компонент Transition в свойство components
  },
  data() {
    return {
      currentUserStatistics: null,
      userInfo: null,
      showStatistics: false,
    };
  },
  created() {
    this.userInfo = getUserInfo();
    this.fetchCurrentUserStatistics();
  },
  methods: {
    fetchCurrentUserStatistics() {
      if (this.userInfo && this.userInfo.id) {
        DataService.getUserProgress(this.userInfo.id)
            .then((data) => {
              if (data && data.length > 0) {
                this.currentUserStatistics = this.groupStatisticsByChapterAndTopic(data);
              } else {
                // Если статистика пустая, установить currentUserStatistics в null
                this.currentUserStatistics = null;
              }
            })
            .catch(() => {
              // Обработка ошибок, если необходимо, без объявления переменной error
            });
      }
    },
    groupStatisticsByChapterAndTopic(statistics) {
      const groupedStats = {};
      statistics.forEach(stat => {
        const chapterName = stat.chapter.chapterName;
        const topicName = stat.topic.topicName;

        if (!groupedStats[chapterName]) {
          groupedStats[chapterName] = {};
        }

        if (!groupedStats[chapterName][topicName]) {
          groupedStats[chapterName][topicName] = stat;
        }
      });
      return groupedStats;
    },
    toggleStatistics() {
      this.showStatistics = !this.showStatistics; // Изменение состояния переменной для показа/скрытия статистики
    },
  },
};
</script>

<style>


</style>
