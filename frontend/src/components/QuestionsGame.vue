<template>

  <div class="container">
    <div v-if="!showTimeUpMessage && currentQuestion">
      <p>Оставшееся время: {{ formattedTime }}</p>
      <div v-if="userInfo">
       <!-- <h2>Привет, {{ userInfo.username }}</h2>-->
        <!-- Вывод случайного предложения из массива -->
        <h4 class="fixed-width-h4">{{ userInfo.username }}, {{  currentQuestion.randomSentence }}</h4>
        <!-- ... другие элементы ... -->
      </div>
      <div v-if="currentQuestion">
        <h4 v-html="formattedQuestion(currentQuestion.question)"></h4>
        <div class="mt-3">
          <button class="btn btn-success btn-lg mr-3 fixed-width-btn " @click="answerQuestion(true)">Да</button>
          <button class="btn btn-danger btn-lg fixed-width-btn " @click="answerQuestion(false)">Нет</button>
        </div>
        <div>
          <button class="btn btn-secondary mt-3 fixed-width-btn" @click="goBack">Закончить</button>
        </div>
     <!--   <p>Ваш ответ: {{ currentAnswer }}</p>
        <p>Правильный ответ: {{ currentQuestion.correct ? 'Да' : 'Нет' }}</p>
        -->
      </div>
    </div>

    <div v-else-if="showTimeUpMessage">
      <p>К сожалению, у меня на Вас больше нет времени..</p>
      <p>В следующий раз постарайтесь уложится в доступное время</p>
      <button class="btn btn-secondary mt-3 fixed-width-btn" @click="goBack">Назад</button>
      <button class="btn btn-primary mt-3 fixed-width-btn" @click="restartGame">Попробовать снова</button>
    </div>

    <!-- Блок отображения статистики после завершения всех вопросов -->
    <div v-if="answeredAllQuestions && !showTimeUpMessage">
      <p>Правильных ответов: {{ correctAnswersCount }}</p>
      <p>Неправильных ответов: {{ wrongAnswersCount }}</p>
      <p v-if="answeredAllQuestions" v-html="calculatePercentage() === 100 ? 'Отлично! <br> Вы ответили правильно на все вопросы, <br> перейдём к следующей теме' : 'Правильно ответили на ' + calculatePercentage() + '% вопросов' "></p>


      <!-- Показать неверные ответы или текст об успешном завершении -->
      <div v-if="calculatePercentage() !== 100">
        <button class="btn btn-info mt-3 " @click="toggleShowIncorrectAnswers">
          {{ showIncorrectAnswers ? 'Скрыть неправильные ответы' : 'Показать неправильные ответы' }}
        </button>
        <div v-if="showIncorrectAnswers" class="mt-3">
          <!-- Блок с неверными ответами -->
          <div v-for="(answer, index) in incorrectAnswers" :key="index" class="card mt-2">
            <div class="card-body">
              <p>{{ answer.question }}</p>
              <p :class="{ 'note-background': answer.note }" v-if="answer.note">
                Примечание: {{ answer.note }}
              </p>
            </div>
          </div>
        </div>
      </div>
      <!-- Дополнительные кнопки или элементы управления, если нужно -->
      <button v-if="answeredAllQuestions" class="btn btn-danger mt-3 fixed-width-btn" @click="goBack">Назад</button>
      <!-- ... -->
    </div>
  </div>
</template>



<script>
import { getUserInfo } from '@/components/userData/userData';
import QuestionDataService from '@/service/admin/QuestionDataService';
import DataService from '@/service/game/UserDataService';
import sentences from "@/text/sentences";

export default {
  name: 'GameQuestions',
  data() {
    return {
      topicName: '',
      questions: [],
      currentQuestion: null,
      currentAnswer: null,
      chapterId: null,
      topicId: null,
      correctAnswersCount: 0,
      wrongAnswersCount: 0,
      answeredAllQuestions: false,
      note: null,
      incorrectAnswers: [],
      showIncorrectAnswers: false,
      userInfo: null,
      timer: null,
     // elapsedTime: 10,
      elapsedTime: 1800,
      showTimeUpMessage: false, // Добавляем переменную состояния для управления отображением сообщения

    };

  },
  computed: {
    formattedTime() {
      const minutes = Math.floor(this.elapsedTime / 60);
      const seconds = this.elapsedTime % 60;
      return `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
    },
    randomSentence() {
      const randomIndex = Math.floor(Math.random() * sentences.length);
      return sentences[randomIndex];
    },
  },

  watch: {
    answeredAllQuestions(newVal) {
      if (newVal) {
        this.sendStatisticsToBackend();
      }
    },
  },
  methods: {
    loadUserInfo() {
      // используем функцию из userData.js для получения информации о пользователе
      this.userInfo = getUserInfo();
    },
    loadQuestions() {
      this.chapterId = this.$route.query.chapterId;
      this.topicId = this.$route.query.topicId;

      QuestionDataService.getAllQuestions(this.topicId)
          .then((response) => {
            if (Array.isArray(response.data)) {
              const shuffledQuestions = this.shuffleQuestions(response.data);
              this.totalQuestions = shuffledQuestions.length;
              if (this.totalQuestions > 0) {
                // Добавляем randomSentence для каждого вопроса
                this.questions = shuffledQuestions.map(question => ({
                  ...question,
                  randomSentence: this.getRandomSentence(),
                }));
                this.getNextQuestion();
                // Вывод содержимого объекта вопроса в консоль
                console.log(this.questions);
              } else {
                // Вопросов нет - переход на предыдущую страницу
                this.$router.go(-1);
              }
            } else {
              console.error('Ошибка: данные вопросов не являются массивом');
              // Данные не являются массивом - переход на предыдущую страницу
              this.$router.go(-1);
            }
          })
          .catch((error) => {
            console.error('Ошибка при загрузке вопросов:', error);
            // Ошибка при загрузке - переход на предыдущую страницу
            this.$router.go(-1);
          });
    },

    getRandomSentence() {
      const randomIndex = Math.floor(Math.random() * sentences.length);
      return sentences[randomIndex];
    },

    shuffleQuestions(questions) {
      return questions.sort(() => Math.random() - 0.5);
    },
    getNextQuestion() {
      if (this.questions.length > 0) {
        this.currentQuestion = this.questions.pop();
        // Обновляем случайную фразу для нового вопроса
        this.currentQuestion.randomSentence = this.getRandomSentence();
      } else {
        this.currentQuestion = null; // Все вопросы были показаны
        this.answeredAllQuestions = true;
        this.stopTimer();
      }
    },
    answerQuestion(answer) {
      if (this.currentQuestion.correct === answer) {
        this.currentAnswer = 'Да';
        this.correctAnswersCount++;
      } else {
        this.currentAnswer = 'Нет';
        this.wrongAnswersCount++;
        this.incorrectAnswers.push({
          question: this.currentQuestion.question,
          note: this.currentQuestion.note || null, // добавляем note в массив неправильных ответов
        });
      }
      this.getNextQuestion();
    },
    goBack() {
      this.$router.push({name: 'GameTopics', query: {chapterId: this.chapterId}});
    },
    startTimer() {
      this.timer = setInterval(() => {
        if (this.elapsedTime <= 0) {
          this.stopTimer();
          this.answeredAllQuestions = true;
          this.showTimeUpMessage = true;
        } else {
          this.elapsedTime--;
        }
      }, 1000);
    },
    stopTimer() {
      clearInterval(this.timer); // останавливаем таймер
    },
    restartGame() {
      this.stopTimer();
      //this.elapsedTime = 100; // Устанавливаем время обратно в 10 секунд (или любое другое начальное значение)
      this.elapsedTime = 1800;
      this.correctAnswersCount = 0;
      this.wrongAnswersCount = 0;
      this.incorrectAnswers = [];
      this.answeredAllQuestions = false;
      this.showTimeUpMessage = false; // Скрываем сообщение о завершении времени
      this.loadQuestions();
      this.startTimer();
    },

    formattedQuestion(question) {
      // Пример форматирования текста внутри тега <p> для улучшения читаемости
      return `<p style="font-family: Arial, sans-serif; line-height: 1.6;">${question}</p>`;
    },
    calculatePercentage() {
      const totalQuestions = this.totalQuestions; // Используем общее количество вопросов
      if (totalQuestions === 0) {
        return 0;
      }
      return Math.round((this.correctAnswersCount / totalQuestions) * 100);
    },
    toggleShowIncorrectAnswers() {
      this.showIncorrectAnswers = !this.showIncorrectAnswers;
    },
    sendStatisticsToBackend() {
     // const totalTimeInSeconds = 10; // Пример общего предельного времени в секундах
      const totalTimeInSeconds = 1800;
      const elapsedTime = this.elapsedTime; // Прошедшее время
      const timeSpent = totalTimeInSeconds - elapsedTime; // Вычисляем затраченное время
      const statisticsData = {
        userId: this.userInfo ? this.userInfo.id : null,
        chapter: { id: Number(this.chapterId) },
        topic: { id: Number(this.topicId) },
        correctAnswers: this.correctAnswersCount,
        wrongAnswers: this.wrongAnswersCount,
        progressPercentage: this.calculatePercentage(),
        totalQuestions: this.totalQuestions,
        elapsedTime: timeSpent // Используем timeSpent вместо elapsedTime
      };
      if (statisticsData.userId) {
        DataService.sendStatistics(statisticsData, statisticsData.userId)
            .then((response) => {
              console.log('Статистика успешно отправлена на бэкэнд:', response);
              // Действия при успешной отправке
            })
            .catch((error) => {
              console.error('Ошибка при отправке статистики на бэкэнд:', error);
              // Действия при ошибке отправки
            });
      } else {
        console.error('Ошибка: id пользователя недоступен');
        // Действия при отсутствии id пользователя
      }
    },
  },
  created() {
    this.loadUserInfo();

    // Проверяем, определен ли userInfo после загрузки
    if (!this.userInfo) {
      this.$router.push('/'); // Замените '/some_other_page' на ваш путь для перенаправления
    } else {
      this.loadQuestions();
      this.startTimer();
    }
  },
};
</script>
<style>

</style>