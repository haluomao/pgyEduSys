import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Home from '@/components/Home'

Vue.use(Router)

//const login = r => require.ensure([], () => r(require('@/page/login')), 'login');
//const addGrade = r => require.ensure([], () => r(require('@/page/addGrade')), 'addGrade');
const categoryList = r => require.ensure([], () => r(require('@/page/categoryList')), 'categoryList');
const coursewareList = r => require.ensure([], () => r(require('@/page/coursewareList')), 'coursewareList');
const addCourseware = r => require.ensure([], () => r(require('@/page/addCourseware')), 'addCourseware');

const gradeSide = r => require.ensure([], () => r(require('@/components/gradeSide')), 'gradeSide');

export default new Router({
  routes: [
    {
      path: '/test',
      component: gradeSide
    },
    {
      path: '/hello',
      component: HelloWorld
    },
    {
      path: '/home',
      component: Home
    },
    {
      path: '/categoryList',
      component: categoryList
    },
    {
      path: '/coursewareList',
      component: coursewareList
    },
    {
      path: '/addCourseware',
      component: addCourseware
    }
  ]
})
