import React from "react";
import {Login} from "./Login";
import {shallow} from 'enzyme';

describe("test login", () => {
    it('should display login', () => {
        // Given
        const subject = shallow(<Login/>);

        // When
        const loginDiv = subject.find('div');

        // Then
        expect(loginDiv.text()).toEqual("Login");
    });
});