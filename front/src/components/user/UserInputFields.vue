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
      :inputErrors="userInput.inputErrorsp"
    />

    <InputField
      fieldId="phoneNumber"
      :value="userInput.phoneNumber"
      label="電話番号"
      placeholder="03-1234-5678"
      :inputErrors="userInput.inputErrors"
    />

    <RadioField
      fieldId="gender"
      :value="userInput.gender"
      label="性別"
      :items="userInput.genderTypes"
      :inputErrors="userInput.inputErrors"
    />
  </div>
</template>
<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import InputField from '@/components/InputField.vue';
import RadioField from '@/components/RadioField.vue';
import UserInput from '@/components/user/UserInput';

@Component({
  components: {
    InputField,
    RadioField
  }
})
export default class UserInputFields extends Vue {
  @Prop({ type: Object, default: () => new UserInput() })
  public userInput?: UserInput;
}
</script>