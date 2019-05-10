<template>
    <div th:fragment="macro(field_id,label,members)" class="inline fields" th:classappend="${#fields.hasErrors('__${field_id}__')}? 'error'">

        <label>{{ label }}</label>
        <div class="field" v-for="item in items" v-bind:key="item.value">
            <div class="ui radio checkbox">
                <input type="radio" 
                    :value="item.value"
                    :id="fieldId"
                    :name="fieldId"
                    :checked="same(item.value)"
                />
                <label >{{ item.caption }}</label>
            </div>
        </div>
        <div class="ui left pointing red basic label" 
            v-if="hasError()" >
            <span>{{ errorMessage() }}</span>
        </div>
    </div>
</template>
<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';
import RadioItem from '@/components/RadioItem';

@Component
export default class RadioField extends Vue {
  @Prop({ type: String })
  private value!: string;

  @Prop({ type: String })
  private fieldId!: string;

  @Prop({ type: String })
  private label!: string;

  @Prop({ type: Array, default: () => [] })
  private items!: RadioItem[];

  @Prop({ type: Object, default: () => {} })
  private inputErrors!: { [key: string]: string };

  public same(other: string): boolean {
    return this.value === other;
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