import { createWebHistory, createRouter } from "vue-router";

const routes = [
    {
        path: "/",
        name: "FirstPage",
        component: () => import("./components/FirstPage.vue"),
    },
    {
        path: "/admin/",
        name: "Admin",
        component: () => import("./components/admin/Admin.vue"),
    },
    {
        path: "/chapters",
        name: "ChapterList",
        component: () => import("./components/admin/ChapterList.vue"),
    },
    {
        path: "/add",
        name: "AddChapter",
        component: () => import("./components/admin/AddChapter.vue"),
    },
    {
        path: "/topics",
        name: "TopicList",
        component: () => import("./components/admin/TopicList.vue"),
    },
    {
        path: "/add-topic",
        name: "AddTopic",
        component: () => import("./components/admin/AddTopic.vue"),
    },
    {
        path: '/questions',
        name: 'TopicQuestions',
        component: () => import("./components/admin/QuestionsList.vue"),
    },
    {
        path: '/question',
        name: 'QuestionForm',
        component: () => import("./components/admin/AddQuestion.vue"),
    },
    {
        path: "/game",
        name: "Game",
        component: () => import("./components/Game"),
        props: true,
    },
    {
        path: "/gametopics",
        name: "GameTopics",
        component:() => import("./components/TopicsGame.vue")
    },
    {
        path: "/gamequestions",
        name: "GameQuestions",
        component:() => import("./components/QuestionsGame.vue")
    }

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;