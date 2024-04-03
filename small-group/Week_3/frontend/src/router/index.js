import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: '根目录',
            redirect: '/user'
        },
        {
            path: '/user',
            name: '用户管理',
            component: () => import("../views/User.vue")
        },
    ]
})

export default router
