package com.radvanskyi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.util.Collections;
import java.util.List;

/**
 * The class is responsible for testing com.radvanskyi.SortingButtonApplication
 *
 *  @author Artem_Radvanskyi
 */
public class SortingButtonApplicationTest extends Assert {

    private static final String INPUT = "input";
    private static final String EXPECTING_RESULT = "30";

    private SortingButtonApplication sortingButtonApplication;
    private JTextField textField;
    JFrame jFrame;

    /**
     * Configure initial settings.
     */
    @Before
    public void setUp() {
        sortingButtonApplication = new SortingButtonApplication();
        textField = sortingButtonApplication.getTextField();
        textField.setName(INPUT);
        jFrame = new JFrame();
    }

    /**
     * Tests setText method to the textField.
     */
    @Test
    public void correctInputJTextFieldTest() {
        textField.setText(EXPECTING_RESULT);
        textField.postActionEvent();
        assertEquals(EXPECTING_RESULT, textField.getText());
    }

    /**
     * Tests SortScreen creating after setting value to the textField.
     */
    @Test
    public void sortScreenShouldBeNotNullDefaultConstructorTest() {
        textField.setText(EXPECTING_RESULT);
        SortingButtonApplication.SortScreen sortScreen = sortingButtonApplication.new SortScreen(jFrame);
        assertNotNull(sortScreen);
    }

    /**
     * Tests SortScreen creating with 2-arguments constructor.
     */
    @Test
    public void sortScreenShouldBeNotNullWhenConstructorWithArgumentsTest() {
        List<Integer> list = Collections.singletonList(5);
        SortingButtonApplication.SortScreen sortScreen = sortingButtonApplication.new SortScreen(jFrame, list);
        assertNotNull(sortScreen);
    }

    /**
     * Tests quickSort works correctly.
     */
    @Test
    public void quickSortTest() {
        Integer[] array = {1, 2, 3, 4};
        Integer[] anotherArr = new Integer[array.length];
        System.arraycopy(array, 0, anotherArr, 0, anotherArr.length);

        SortingButtonApplication.QuickSort quickSort = sortingButtonApplication.new QuickSort(jFrame, array,true);
        quickSort.quickSort(array, 0, array.length - 1, true);

        assertNotEquals(array, anotherArr);
        assertNotNull(quickSort);
    }
}
