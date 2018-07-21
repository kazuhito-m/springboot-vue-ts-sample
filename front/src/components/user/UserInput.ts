export default class UserInput {
  public identifier: string = '';
  public name: string = '';
  public dateOfBirth: string = '';
  public phoneNumber: string = '';

  public readonly inputErrors: { [key: string]: string } = {};
}
