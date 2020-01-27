package com.radvanskyi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import javax.swing.*;

public class AppTest extends Assert {

    @Spy
    private JFrame sortFrame;

    private JFrame introFrame;
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

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException() {
        sortFrame = new App().new SortScreen();
    }

    @Test
    public void testCorrectOutput() {
        JTextField textField = Mockito.mock(JTextField.class);
        Mockito.when(textField.getText()).thenReturn("1").thenReturn("1");
        sortFrame = new App().new SortScreen();
    }
}