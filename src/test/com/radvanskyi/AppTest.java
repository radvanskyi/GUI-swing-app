package com.radvanskyi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

public class AppTest extends Assert {

    private static final String WRONG_QUANTITY_MESSAGE = "Sorry, but number must be from 1 to 30";

    private JFrame introFrame;
    private JFrame sortFrame;
    private JButton button;
    private JTextField textField;

    @Before
    public void setUp() {
        introFrame = new App().new IntroScreen();
        button = new JButton();
        textField = new JTextField();
        introFrame.add(button);
        introFrame.add(textField);
        textField.setName("input");
        button.setName("enter");

    }

    @Test
    public void testWrongInputJTextField() {
        textField = (JTextField) TestUtils.getChildNamed(introFrame, "input");
        assertNotNull(textField);
    }

    @Test
    public void testCorrectInputJTextField() {
        String expectingResult = "30";

        textField = (JTextField) TestUtils.getChildNamed(introFrame, "input");
        textField.setText(expectingResult);
        textField.postActionEvent();

        assertEquals(expectingResult, textField.getText());
    }

    @Test
    public void testCorrectOutput() {
        String expectingResult = "1";
        JButton newButton = new JButton();

        textField = (JTextField) TestUtils.getChildNamed(introFrame, "input");
        textField.setText(expectingResult);
        button = (JButton) TestUtils.getChildNamed(introFrame, "enter");
        textField.postActionEvent();
        sortFrame = new App().new SortScreen();

        assertNotNull(sortFrame);
    }
}
