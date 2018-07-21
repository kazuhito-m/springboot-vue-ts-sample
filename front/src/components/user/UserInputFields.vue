<template>
<div>
    <InputField 
      fieldId="name" 
      :value="userInput.name"
      label="名前" 
      placeholder="名前" 
      :inputErrors="userInput.inputErrors" />

    <div th:replace="fragments/input::macro('dateOfBirth.value','生年月日','2000-01-01')">
        <label>生年月日</label>
        <input type="text">
    </div>

    <div th:replace="fragments/input::macro('phoneNumber.value','電話番号','03-1234-5678')">
        <label>電話番号</label>
        <input type="text">
    </div>

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
  @Prop({ type: Object, default: () => new UserInput() })
  public userInput?: UserInput;
}
</script>