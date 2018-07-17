<template>
  <div class="ui container">

    <div class="ui basic segment">
      <h1 class="ui header">利用者一覧</h1>
    </div>

    <a class="ui positive basic button" th:href="@{/serversiderendaring/user/register}">利用者の新規登録</a>

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
            <td> {{ user.identifier }} </td>
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
import User from './User';

@Component
export default class UserList extends Vue {
  private _users: User[] = [];

  public created() {
    this._users = this.receiveUsers();
  }

  private receiveUsers(): User[] {
    // 仮実装
    return [
      {
        identifier: 'kazuhito@mail.com',
        name: 'かずひと みうら',
        age: 3
      }
    ];
  }

  public get users(): User[] {
    return this._users;
  }
}
</script>