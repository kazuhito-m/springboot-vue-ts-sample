<template>
  <div>
    <InputField 
      fieldId="name"
      :value="userInput.name"
      label="名前"
      placeholder="名前"
      :inputErrors="userInput.inputErrors"
    />

    <InputField
      fieldId="dateOfBirth"
      :value="userInput.dateOfBirth"
      label="生年月日"
      placeholder="2000-01-01"
      :inputErrors="userInput.inputErrors"
    />

    <InputField
      fieldId="phoneNumber"
      :value="userInput.phoneNumber"
      label="電話番号"
      placeholder="03-1234-5678"
      :inputErrors="userInput.inputErrors"
    />

    <div th:replace="fragments/radio::macro('gender.value','性別','genderTypes')">
      <div class="ui radio checkbox">
        <label>性別</label>
        <input type="radio" name="gender">
        <label>不明</label>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import InputField from '@/components/InputField.vue';
import UserInput from '@/components/user/UserInput';

@Component({
  components: {
    InputField
  }
})
export default class UserInputFields extends Vue {
  @Prop({
    type: Object,
    default: () => new UserInput()
  })
  public userInput?: UserInput;
}
</script>