import RadioItem from '@/components/RadioItem';

export default class GenderTypeRadioItem implements RadioItem {
  private readonly _value: string;

  public constructor(value: string) {
    this._value = value;
  }

  public get value(): string {
    return this._value;
  }

  public get caption(): string {
    return this._value;
  }
}
