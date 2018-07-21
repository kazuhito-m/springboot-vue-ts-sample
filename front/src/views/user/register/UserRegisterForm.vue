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

      <InputField 
        fieldId="identifier" 
        :value="userInput.identifier"
        label="利用者ID" 
        placeholder="someone@example.com" 
        :inputErrors="userInput.inputErrors" />

      <UserInputFields :userInput="userInput" />

    </div>
    <div class="ui basic segment">
      <button class="ui positive basic button">確認する</button>
      <router-link to="/user">一覧に戻る</router-link>
    </div>
  </form>
  </div>
</div>

<!-- <footer th:include="fragments/layout :: footer"/> -->
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import axios from '@/parts/network/AxiosWrapper';
import InputField from '@/components/InputField.vue';
import UserInputFields from '@/components/user/UserInputFields.vue';
import UserInput from '@/components/user/UserInput';

@Component({
  components: {
    InputField,
    UserInputFields
  }
})
export default class UserRegisterForm extends Vue {
  public userInput: UserInput = new UserInput();

  public async created() {
    const genderTypeValues: string[] = await this.receiveGenderTypeValues();
    this.userInput.initializeGenderTypes(genderTypeValues);
  }

  private async receiveGenderTypeValues(): Promise<string[]> {
    const response = await axios.get('/api/user/genderTypes');
    return response.data;
  }
}
</script>