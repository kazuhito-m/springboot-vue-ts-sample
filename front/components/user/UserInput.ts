import GenderTypeRadioItem from '@/components/user/GenderTypeRadioItem';

export default class UserInput {
  public identifier: string = '';
  public name: string = '';
  public dateOfBirth: string = '';
  public phoneNumber: string = '';
  public gender: string = '不明';

  public genderTypes: GenderTypeRadioItem[] = [];

  public initializeGenderTypes(values: string[]) {
    this.genderTypes = values.map(v => new GenderTypeRadioItem(v));
  }

  public readonly inputErrors: { [key: string]: string } = {};
}
