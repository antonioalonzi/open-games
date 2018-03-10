import React from 'react';
import {mount} from 'enzyme';
import {Form, Input, Submit} from "./Form";

describe('test login', () => {
  it('should submit a form', () => {
    // Given
    const handleSubmit = jest.fn();
    const form = mount(
      <Form onSubmit={handleSubmit}>
        <Input type={'text'} name={'aField'}/>
        <Submit/>
      </Form>
    );

    // When
    form.find('input[type="submit"]').simulate('submit');

    // Then
    expect(handleSubmit.mock.calls.length).toBe(1);
  });
});