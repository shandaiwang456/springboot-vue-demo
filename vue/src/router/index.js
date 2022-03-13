import {createRouter, createWebHistory} from 'vue-router'
import Layout from '../layout/Layout.vue'

import AdminPageLayout from "../layout/AdminPageLayout.vue";

const routes = [

    {
        path: '/login',
        name: 'Login',
        component: () => import("@/views/Login")
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import("@/views/Register")
    },


    // 管理员界面
    {
        path: '/admin',
        // 一级目录
        name: 'Admin',
        component: AdminPageLayout,
        // 引用自建组件
        children: [
            {
                path: 'home02',
                // 二级目录
                name: 'Home02',
                // 主界面
                component: () => import("@/views/Test"),
            },
            {
                path: '/admin/film',
                name: 'Film',
                component: () => import("@/views/Film"),
            },
            {
                path: '/admin/user',
                name: 'User',
                component: () => import("@/views/User"),
            },
            {
                path: '/admin/adminrec',
                name: 'Adminrec',
                component: () => import("@/views/Adminrec"),
            },
            {
                path: '/admin/adminrecord',
                name: 'Adminrecord',
                component: () => import("@/views/Adminrecord"),
            },
        ]
    },


    // 用户系统
    {
        path: '/',
        name: 'Layout',
        component: Layout,
        redirect: "/waitlook",
        // login 直接到 / 有进行重定向到 user
        children: [
            {
                path: '/waitlook',
                name: 'Waitlook',
                component: () => import("@/views/Waitlook"),
            },
            {
                path: '/rec1',
                name: 'Rec1',
                component: () => import("@/views/Rec1"),
            },
            {
                path: '/rec2',
                name: 'Rec2',
                component: () => import("@/views/Rec2"),
            },
            {
                path: '/searchfilm',
                name: 'Searchfilm',
                component: () => import("@/views/Searchfilm"),
            },
            {
                path: '/userjournal',
                name: 'Userjournal',
                component: () => import("@/views/Userjournal"),
            },
            {
                path: '/userroom',
                name: 'Userroom',
                component: () => import("@/views/Userroom"),
            },
            {
                path: 'book',
                name: 'Book',
                component: () => import("@/views/Book"),
            }
        ]
    },

];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

export default router
