package com.ey.automation.controls.elements;

import com.ey.automation.controls.api.ImplementedBy;
import com.ey.automation.controls.internals.Control;

@ImplementedBy(TextBoxBase.class)
public interface TextBox extends Control {

    void EnterText(String text);
    String GetTextValue();

}
