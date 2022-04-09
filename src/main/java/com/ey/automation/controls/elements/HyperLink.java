package com.ey.automation.controls.elements;

import com.ey.automation.controls.api.ImplementedBy;
import com.ey.automation.controls.internals.Control;


@ImplementedBy(HyperLinkBase.class)
public interface HyperLink extends Control{


    void ClickLink();
    String GetUrlText();
    boolean CheckUrlTextContains(String containsText);
}
