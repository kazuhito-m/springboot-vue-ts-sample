<template>
  <div class="ui container">

    <div class="ui basic segment">
      <h1 class="ui header">利用者一覧</h1>
    </div>

    <router-link to="/user/register" class="ui positive basic button">利用者の新規登録</router-link>

    <div class="ui basic segment">

      <table class="ui celled table">
        <thead>
          <tr>
            <th>利用者ID</th>
            <th>利用者名</th>
            <th>年齢</th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users"
            :key="user.identifier">
            <td> {{ user.userIdentifier }} </td>
            <td> {{ user.name }} </td>
            <td> {{ user.age }} </td>
            <td>
              <a class="ui secondary basic button" th:href="@{/serversiderendaring/user/{userId}/update(userId=${user.identifier()})}">変更</a>
            </td>
            <td>
              <a class="ui negative basic button" th:href="@{/serversiderendaring/user/{userId}/delete/view(userId=${user.identifier()})}">削除</a>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <!-- <footer th:include="fragments/layout :: footer"/> -->
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import User from '@/views/user/User';
import axios from '@/parts/network/AxiosWrapper';

@Component
export default class UserList extends Vue {
  public users: User[] = [];

  public async created() {
    this.users = await this.receiveUsers();
  }

  private async receiveUsers(): Promise<User[]> {
    const response = await axios.get(`/api/user`);
    return response.data;
  }
}
</script>