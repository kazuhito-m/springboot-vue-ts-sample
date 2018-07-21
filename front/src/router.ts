import Vue from 'vue';
import Router from 'vue-router';
import UserList from '@/views/user/UserList.vue';
import UserRegisterForm from '@/views/user/register/UserRegisterForm.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'top',
      component: UserList,
    },
    {
      path: '/user',
      name: 'userList',
      component: UserList,
    },
    {
      path: '/user/register',
      name: 'userRegister',
      component: UserRegisterForm,
    },
  ],
});
