export default class UserInput {
  public identifier: string = '';
  public name: string = '';

  public readonly inputErrors: { [key: string]: string } = {};
}
