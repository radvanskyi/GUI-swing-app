package com.radvanskyi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class AppTest extends Assert {

    private static final String INPUT = "input";
    private static final String EXPECTING_RESULT = "30";

    private App app;
    private JTextField textField;
    JFrame jFrame;

    @Before
    public void setUp() {
        app = new App();
        textField = app.getTextField();
        textField.setName(INPUT);
        jFrame = new JFrame();
    }

    @Test
    public void correctInputJTextFieldTest() {
        textField.setText(EXPECTING_RESULT);
        textField.postActionEvent();
        assertEquals(EXPECTING_RESULT, textField.getText());
    }

    @Test
    public void sortScreenShouldBeNotNullDefaultConstructorTest() {
        textField.setText(EXPECTING_RESULT);
        App.SortScreen sortScreen = app.new SortScreen(jFrame);
        assertNotNull(sortScreen);
    }

    @Test
    public void sortScreenShouldBeNotNullWhenConstructorWithArgumentsTest() {
        List<Integer> list = Collections.singletonList(5);
        App.SortScreen sortScreen = app.new SortScreen(jFrame, list);
        assertNotNull(sortScreen);
    }

    @Test
    public void quickSortTest() {
        Integer[] array = {1, 2, 3, 4};
        Integer[] anotherArr = array;

        App.QuickSort quickSort = app.new QuickSort(jFrame, array,true);
        quickSort.quickSort(anotherArr, 0, array.length - 1, true);

        assertArrayEquals(array, anotherArr);
        assertNotNull(quickSort);
    }
}
