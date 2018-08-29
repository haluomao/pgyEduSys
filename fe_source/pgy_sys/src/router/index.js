import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'

Vue.use(Router)

//const login = r => require.ensure([], () => r(require('@/page/login')), 'login');
//const addGrade = r => require.ensure([], () => r(require('@/page/addGrade')), 'addGrade');
//const categoryList = r => require.ensure([], () => r(require('@/page/categoryList')), 'categoryList');
const coursewareList = r => require.ensure([], () => r(require('@/page/coursewareList')), 'coursewareList');
const addCourseware = r => require.ensure([], () => r(require('@/page/addCourseware')), 'addCourseware');
const accountList = r => require.ensure([], () => r(require('@/page/accountList')), 'accountList');
const gradeList = r => require.ensure([], () => r(require('@/page/gradeList')), 'gradeList');
const homepage = r => require.ensure([], () => r(require('@/page/homepage')), 'homepage');
const contact = r => require.ensure([], () => r(require('@/page/contact')), 'contact');
const login = r => require.ensure([], () => r(require('@/page/login')), 'login');

const gradeSide = r => require.ensure([], () => r(require('@/components/gradeSide')), 'gradeSide');

export default new Router({
  routes: [
    {
      path: '/login',
      component: login
    },
    {
      path: '/test',
      component: gradeSide
    },
    {
      path: '/hello',
      component: HelloWorld
    },
    {
      path: '/homepage',
      component: homepage
    },
    {
      path: '/coursewareList1',
      component: coursewareList
    },
    {
      path: '/coursewareList2',
      component: coursewareList
    },
    {
      path: '/coursewareList3',
      component: coursewareList
    },
    {
      path: '/coursewareList4',
      component: coursewareList
    },
    {
      path: '/addCourseware/:id',
      component: addCourseware
    },
    {
      path: '/accountList',
      component: accountList
    },
    {
      path: '/gradeList',
      component: gradeList
    },
    {
      path: '/contact',
      component: contact
    }
  ]
})
