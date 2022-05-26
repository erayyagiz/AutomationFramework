package steps;

import base.CustomTestBase;
import org.testng.annotations.Test;

public class Home extends CustomTestBase {

    @Test
    public void runTest1(){
        page().writeText("HELLO WORLD!");
        page().clickSearchButton();
    }
}
