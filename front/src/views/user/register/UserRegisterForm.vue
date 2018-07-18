<template>
<div class="ui container">

  <div class="ui vertical segment">
  <h1 class="ui header">利用者の新規登録</h1>
  </div>

  <div class="ui vertical segment">
  <form id="custom-form" class="ui form" name="body"
        method="post" action="#"
        th:object="${user}" th:action="@{/serversiderendaring/user/register/confirm}">

    <div class="ui vertical segment">
    <div th:replace="fragments/input::macro('identifier.value','利用者ID','someone@example.com')">
      <label>ID</label>
      <input type="text">
    </div>

    <div th:include="user/update/form ::input-fields"></div>

    </div>
    <div class="ui basic segment">
      <button class="ui positive basic button">確認する</button>
      <a th:href="@{/serversiderendaring}">一覧に戻る</a>
    </div>
  </form>
  </div>
</div>

<!-- <footer th:include="fragments/layout :: footer"/> -->
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import User from '@/views/user/User';
import axios from '@/parts/network/AxiosWrapper';

@Component
export default class UserRegisterForm extends Vue {
  public async created() {
  }
}
</script>