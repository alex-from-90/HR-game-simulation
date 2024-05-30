let userInfo = null;

export const setUserInfo = (user) => {
    userInfo = {
        id: user.id,
        username: user.first_name,
        // другие данные о пользователе, если необходимо
    };
};

export const getUserInfo = () => {
    return userInfo;
};

// Используйте эту функцию для установки информации о пользователе по умолчанию или для обработки случая, когда информация о пользователе недоступна
export const setDefaultUserInfo = () => {
    userInfo = {
        id: '11111111',
        username: 'defaultUsername',
        // другие значения по умолчанию, если необходимо
    };
};