import React from "react";
import {Login} from "./Login";
import {shallow} from "enzyme";

describe("test login", () => {
    it("should display login", () => {
        // When
        const component = shallow(<Login/>);

        // Then
        const loginDiv = component.find("div");
        expect(loginDiv.text()).toEqual("Login");
    });
});