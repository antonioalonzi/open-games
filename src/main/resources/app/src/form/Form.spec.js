import React from 'react';
import {mount} from 'enzyme';
import {Form, Input, Submit} from './Form';

describe('test form', () => {
  it('should submit a form', () => {
    // Given
    const onFormSubmit = jest.fn();
    const form = mount(
      <Form onFormSubmit={onFormSubmit}>
        <Input type={'text'} name={'aField'}/>
        <Submit/>
      </Form>
    );

    // When
    form.find('input[name="afield"]').simulate('change', { target: { name: 'afield', value: 'aValue' } });
    form.find('input[type="submit"]').simulate('submit');

    // Then
    expect(onFormSubmit.mock.calls.length).toEqual(1);
    expect(onFormSubmit.mock.calls[0][0]).toEqual({afield: 'aValue'});

    expect(form.find('.form-error').length).toEqual(0);
  });

  it('should display an error if mandatory field is missing', () => {
    // Given
    const onFormSubmit = jest.fn();
    const form = mount(
      <Form onFormSubmit={onFormSubmit}>
        <Input type={'text'} name={'aMandatoryField'} mandatory={true}/>
        <Submit/>
      </Form>
    );

    // When
    form.find('input[type="submit"]').simulate('submit');

    // Then
    expect(onFormSubmit.mock.calls.length).toEqual(0);

    expect(form.find('.form-error').length).toEqual(1);
    expect(form.find('.form-error').text()).toEqual('aMandatoryField is mandatory.');
  });

  it('should not display an error if a non mandatory field is missing', () => {
    // Given
    const onFormSubmit = jest.fn();
    const form = mount(
      <Form onFormSubmit={onFormSubmit}>
        <Input type={'text'} name={'aNonMandatoryField'}/>
        <Submit/>
      </Form>
    );

    // When
    form.find('input[type="submit"]').simulate('submit');

    // Then
    expect(onFormSubmit.mock.calls.length).toEqual(1);
    expect(onFormSubmit.mock.calls[0][0]).toEqual({anonmandatoryfield: ''});

    expect(form.find('.form-error').length).toEqual(0);
  });
});