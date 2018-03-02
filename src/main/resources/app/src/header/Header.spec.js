import React from "react";
import {Header} from "./Header";
import {shallow} from "enzyme";

describe("test header", () => {
  it("should display header", () => {
    // When
    const component = shallow(<Header/>);

    // Then
    const headerLeft = component.find("#header-left");
    const openGamesLink = headerLeft.find("Link");
    expect(openGamesLink.length).toEqual(1);
  });
});