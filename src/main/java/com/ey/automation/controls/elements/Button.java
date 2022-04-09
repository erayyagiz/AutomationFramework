package com.ey.automation.controls.elements;

import com.ey.automation.controls.api.ImplementedBy;
import com.ey.automation.controls.internals.Control;


@ImplementedBy(ButtonBase.class)
public interface Button extends Control {

    void PerformClick();
    String GetButtonText();
    void PerformSubmit();
}
