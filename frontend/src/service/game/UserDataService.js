import axios from 'axios';

const BASE_URL = process.env.VUE_APP_BASE_URL;
const USER_API_URL = `${BASE_URL}/api/users`;

class DataService {

    createUser(userData) {
        console.log('Отправляется запрос на создание пользователя:', userData);
        return axios.post(`${USER_API_URL}/create`, userData)
            .then(response => {
                console.log('Ответ от сервера:', response.data);
                return response.data;
            })
            .catch(error => {
                console.error('Ошибка при запросе на создание пользователя:', error);
                throw error;
            });
    }
    sendStatistics(statisticsData, userId) {
        console.log('Отправляется статистика на бэкэнд:', statisticsData);
        return axios.post(`${USER_API_URL}/${userId}/update-progress`, statisticsData)
            .then((response) => {
                console.log('Ответ от сервера:', response.data);
                return response.data;
            })
            .catch((error) => {
                console.error('Ошибка при отправке статистики на бэкэнд:', error);
                throw error;
            });
    }
    getUserProgress(userId) {
        return axios.get(`${USER_API_URL}/${userId}/all-progress`)
            .then(response => {
                console.log(`Статистика для пользователя с ID ${userId}:`, response.data);
                return response.data;
            })
            .catch(() => {
                console.error(`нет статистики для пользователя с ID ${userId}`);
                throw new Error(`нет статистики для пользователя с ID ${userId}`);
            });
    }
    getProgressByTopic(userId, topicId) {
        return axios.get(`${USER_API_URL}/${userId}/progress/${topicId}`)
            .then(response => {
                console.log(`Статистика для пользователя с ID ${userId} по топику ${topicId}:`, response.data);
                return response.data;
            })
            .catch(() => {
                console.error(`Нет статистики для пользователя с ID ${userId} по топику ${topicId}`);
                throw new Error(`Нет статистики для пользователя с ID ${userId} по топику ${topicId}`);
            });
    }
    getAllUserProgress() {
        return axios.get(`${USER_API_URL}/all-progress`)
            .then(response => {
                console.log('Отправлен запрос на получение всей статистики:', response); // Добавленный console.log для просмотра всего ответа
                console.log('Полученные данные всей статистики:', response.data);
                return response.data;
            })
            .catch(error => {
                console.error('Ошибка при получении всей статистики:', error);
                throw error;
            });
    }
    getTop10Users() {
        return axios.get(`${USER_API_URL}/top-10-users`)
            .then(response => {
                console.log('Отправлен запрос на получение топ-10 статистики:', response); // Добавленный console.log для просмотра всего ответа
                console.log('Полученные данные топ-10 статистики:', response.data);
                return response.data;
            })
            .catch(error => {
                console.error('Ошибка при получении топ-10 статистики:', error);
                throw error;
            });
    }
}

export default new DataService();