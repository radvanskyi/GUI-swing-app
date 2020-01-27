package com.radvanskyi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.util.Collections;
import java.util.List;

public class AppTest extends Assert {

    private static final String INPUT = "input";
    private static final String EXPECTING_RESULT = "30";

    private App app;
    private JFrame sortFrame;
    private JTextField textField;

    @Before
    public void setUp() {
        app = new App();
        JFrame introFrame = app.new IntroScreen();
        textField = app.getTextField();
        introFrame.add(textField);
        textField.setName(INPUT);
    }

    @Test
    public void correctInputJTextFieldTest() {
        textField.setText(EXPECTING_RESULT);
        textField.postActionEvent();
        assertEquals(EXPECTING_RESULT, textField.getText());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenNewSortScreenWithoutIntroScreenTest() {
        sortFrame = new App().new SortScreen();
    }

    @Test
    public void sortScreenShouldBeNotNullDefaultConstructorTest() {
        textField.setText(EXPECTING_RESULT);
        sortFrame = app.new SortScreen();
        assertNotNull(sortFrame);
    }

    @Test
    public void sortScreenShouldBeNotNullWhenConstructorWithArgumentsTest() {
        List<Integer> list = Collections.singletonList(5);
        textField.setText(EXPECTING_RESULT);
        sortFrame = app.new SortScreen(list);
        assertNotNull(sortFrame);
    }
}
