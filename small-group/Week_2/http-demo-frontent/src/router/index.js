import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: '根目录',
            redirect: '/http'
        },
        {
            path:'/http',
            name:'http',
            component:() => import('../views/Http.vue')
        }
    ]
})

export default router
