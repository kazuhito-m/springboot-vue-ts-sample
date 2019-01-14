import axious from '@/parts/network/AxiosWrapper';

describe('AxiosWrapper.ts テスト', () => {
  it('sample test', () => {
    const test = axious.create();
    expect(test).not.toBeNull();
  });
});
