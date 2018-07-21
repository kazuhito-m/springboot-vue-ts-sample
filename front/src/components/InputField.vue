<template>
    <!--入力フィールドの部品-->
    <div class="field" 
        :class="classNameWhenError()">
        <label>{{ label }}</label>
        <input type="text" class="short-input" 
            :id="fieldId"
            :name="fieldId"
            :placeholder="placeholder" 
            :value="value"/>
        <div class="ui left pointing red basic label" 
            v-if="hasError()" >
            <span>{{ errorMessage() }}</span>
        </div>
    </div>
</template>
<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

@Component
export default class InputField extends Vue {
  @Prop({ type: String })
  private value!: string;

  @Prop({ type: String })
  private fieldId!: string;

  @Prop({ type: String })
  private label!: string;

  @Prop({ type: String })
  private placeholder!: string;

  @Prop({ type: Object, default: () => {} })
  private inputErrors!: { [key: string]: string };

  public async created() {
  }

  private nonEmptyInputErrors(): { [key: string]: string } {
    if (this.inputErrors === undefined) return {};
    return this.inputErrors;
  }

  public hasError(): boolean {
    return this.fieldId in this.nonEmptyInputErrors();
  }

  public errorMessage(): string {
    if (!this.hasError()) return '';
    return this.nonEmptyInputErrors()[this.fieldId];
  }

  public classNameWhenError(): string {
    return this.hasError() ? 'error' : '';
  }
}
</script>