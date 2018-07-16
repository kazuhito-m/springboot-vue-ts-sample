import Vue from 'vue';
import Router from 'vue-router';
import UserList from './views/user/UserList.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'userList',
      component: UserList,
    }
  ],
});
