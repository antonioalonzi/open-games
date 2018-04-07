import {Utils} from './Utils';

describe('test header', () => {
  it('isBlank test', () => {
    expect(Utils.isBlank(null)).toEqual(true);
    expect(Utils.isBlank('')).toEqual(true);
    expect(Utils.isBlank('   ')).toEqual(true);
    expect(Utils.isBlank('something')).toEqual(false);
  });
});
