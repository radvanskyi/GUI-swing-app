package com.radvanskyi;

import com.google.common.collect.Comparators;
import org.apache.commons.lang3.StringUtils;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * The application is responsible for getting number as user input and show number of buttons according to this input.
 * The functionality includes reading user input, painting buttons according to the user input (buttons names are
 * numbers generated randomly in the range between 1 and 1000), buttons sorting in ascending and descending orders,
 * returning to the main page, painting another number of buttons by pressing on one of them. Buttons sorting is done
 * in a separate thread.
 *
 *  @author Artem_Radvanskyi
 */
public class SortingButtonApplication extends JFrame {

    private static final int DEFAULT_SCREEN_WIDTH = 1200;
    private static final int DEFAULT_SCREEN_HEIGHT = 600;
    private static final int MAX_RANDOM_NUMBER = 1000;
    private static final int MAX_BUTTON_QUANTITY = 30;
    private static final int DEFAULT_INDENT = 20;
    private static final int DEFAULT_LENGTH = 10;
    private static final int BUTTON_WIDTH = 80;

    private JTextField textField;
    private JButton button;
    private Insets insets;

    public SortingButtonApplication() {
        insets = new Insets(DEFAULT_INDENT, DEFAULT_INDENT, 0, DEFAULT_INDENT);
        new IntroScreen(this);
    }

    /**
     * The main method executing this app
     */
    public static void main(String[] args) {
        new SortingButtonApplication();
    }

    /**
     * Internal class. Main page of the application.
     * Its functionality includes displaying the start message to user, input field, and button reading the input
     * and calling another class
     */
    public class IntroScreen {
        private static final String WRONG_INPUT_MESSAGE = "The input must be number more than 0 and less than 300";
        private static final String INTRO_MESSAGE = "How many numbers to display?";
        private static final String ENTER_BUTTON = "Enter";

        /**
         * Constructor
         *
         * @param frame - application window
         */
        public IntroScreen(JFrame frame) {
            addComponentsToIntroScreenContainer(frame);
            frame.setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        /**
         * This method is responsible for adding all components to the page container
         *
         * @param frame - application window
         */
        private void addComponentsToIntroScreenContainer(JFrame frame) {
            Font font = new Font("Serif", Font.BOLD, DEFAULT_INDENT);
            Container container = frame.getContentPane();
            container.setLayout(new GridBagLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();

            gridBagConstraints.insets = insets;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            container.add(getJLabel(font), gridBagConstraints);

            gridBagConstraints.gridy++;
            container.add(getTextField(font), gridBagConstraints);

            gridBagConstraints.gridy++;
            button = getJButton(ENTER_BUTTON, Color.CYAN, getNewScreenListener(frame));
            container.add(button, gridBagConstraints);
        }

        /**
         * This method is responsible for returning label with font given as an argument
         *
         * @param font - font for the label text.
         * @return new label
         */
        private JLabel getJLabel(Font font) {
            JLabel jLabel = new JLabel(INTRO_MESSAGE);
            jLabel.setFont(font);
            return jLabel;
        }

        /**
         * This method is responsible for reading from textField and passing to the next screen
         * or invoking a pop-up message if input is incorrect
         *
         * @param frame - application window
         */
        private ActionListener getNewScreenListener(JFrame frame) {
            return e -> {
                String input = textField.getText();
                int buttonQuantity = checkValidInputAndGetNumber(input);
                if (isValidInputRange(buttonQuantity)) {
                    frame.getContentPane().removeAll();
                    new SortScreen(frame);
                    frame.getContentPane().repaint();
                } else {
                    JOptionPane.showMessageDialog(null, WRONG_INPUT_MESSAGE);
                }
            };
        }

        /**
         * This method is responsible for checking of numbers range
         *
         * @param buttonQuantity - number of buttons to show on page
         * @return true || false
         */
        private boolean isValidInputRange(int buttonQuantity) {
            return buttonQuantity > 0 && buttonQuantity < 300;
        }

        /**
         * This method is responsible for checking is input is an integer. If not - returns 0.
         *
         * @param input - user input in the textField
         * @return number of buttons to show on page
         */
        private int checkValidInputAndGetNumber(String input) {
            int buttonNum = 0;
            if (StringUtils.isNumeric(input)) {
                buttonNum = Integer.parseInt(input);
            }
            return buttonNum;
        }
    }

    /**
     * Internal class. Second page of the application.
     * It includes number of buttons according to the user input (buttons names are numbers generated randomly in
     * the range between 1 and 1000), two functional buttons: sort and reset. Sort button provides sorting buttons
     * numbers in ascending and descending order in separate thread. Reset button returns user to the main page.
     * Pressing on number button less or equal 30 will generate another list of buttons according to the button's name.
     */
    public class SortScreen {

        private static final String WRONG_QUANTITY_MESSAGE = "The number must be in the range from 1 to 30";
        private static final String RESET_BUTTON_NAME = "Reset";
        private static final String SORT_BUTTON_NAME = "Sort";
        private static final double WEIGHT_X = 0.5;
        private static final int NUM_ONE = 1;

        private List<Integer> listOfNumbers;

        /**
         * Constructor
         *
         * @param frame - application window
         */
        public SortScreen(JFrame frame) {
            addComponentsToSortScreenContainer(frame);
            frame.setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        /**
         * Constructor with list as an additional argument to perform buttons sorting
         *
         * @param frame - application window
         * @param sortedList - sorted list of numbers for buttons
         */
        public SortScreen(JFrame frame, List<Integer> sortedList) {
            addSortedComponentsToSortScreenContainer(frame, sortedList);
            frame.setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        /**
         * This method is responsible for adding all components to the page container
         *
         * @param frame - application window
         */
        private void addComponentsToSortScreenContainer(JFrame frame) {
            Container container = frame.getContentPane();
            container.setLayout(new GridBagLayout());
            int buttonQuantity = Integer.parseInt(textField.getText());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = insets;
            listOfNumbers = addListOfNumbers(buttonQuantity);
            addNumberButtons(gridBagConstraints, buttonQuantity, listOfNumbers, frame);
            addFuncButtons(gridBagConstraints, frame);
        }

        /**
         * This method is responsible for adding all components to the page container. It is used with the constructor
         * with 2 arguments.
         *
         * @param frame - application window
         * @param sortedList - sorted list of numbers for buttons
         */
        private void addSortedComponentsToSortScreenContainer(JFrame frame, List<Integer> sortedList) {
            Container container = frame.getContentPane();
            container.setLayout(new GridBagLayout());
            int buttonNum = sortedList.size();

            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = insets;
            listOfNumbers = sortedList;
            addNumberButtons(gridBagConstraints, buttonNum, listOfNumbers, frame);
            addFuncButtons(gridBagConstraints, frame);
        }

        /**
         * This method is responsible for adding functional buttons to the page container.
         *
         * @param gridBagConstraints - grid for elements
         * @param frame - application window
         */
        private void addFuncButtons(GridBagConstraints gridBagConstraints, JFrame frame) {
            gridBagConstraints.gridx++;
            gridBagConstraints.gridy = 0;
            button = getJButton(SORT_BUTTON_NAME, Color.GREEN, getSortListener(frame));
            frame.add(button, gridBagConstraints);
            gridBagConstraints.gridy++;
            button = getJButton(RESET_BUTTON_NAME, Color.GREEN, getResetListener(frame));
            frame.add(button, gridBagConstraints);
        }

        /**
         * This method is responsible for adding number buttons to the page container.
         *
         * @param gridBagConstraints - grid for elements
         * @param buttonQuantity - buttons quantity
         * @param listOfNumbers - list of numbers for buttons
         * @param frame - application window
         */
        private void addNumberButtons(GridBagConstraints gridBagConstraints, int buttonQuantity,
                                      List<Integer> listOfNumbers, JFrame frame) {
            gridBagConstraints.weightx = WEIGHT_X;
            gridBagConstraints.gridx = 0;
            for (int i = 0, colCounter = 0; i < buttonQuantity; i++, colCounter++) {
                if (colCounter == DEFAULT_LENGTH) {
                    colCounter = 0;
                    gridBagConstraints.gridx++;
                }
                gridBagConstraints.gridy = colCounter;
                String buttonName = String.valueOf(listOfNumbers.get(i));
                button = getJButton(buttonName, Color.CYAN, getNumButtonListener(frame));
                frame.add(button, gridBagConstraints);
            }
        }

        /**
         * This method is responsible for generating random numbers according to the user input and
         * returning list of them
         *
         * @param buttonQuantity - buttons quantity
         * @return list of numbers for buttons
         */
        private List<Integer> addListOfNumbers(int buttonQuantity) {
            Random random = new Random();
            int requiredNum = random.nextInt(MAX_BUTTON_QUANTITY);
            List<Integer> list = new ArrayList<>();
            list.add(requiredNum);
            list.addAll(random.ints(1, MAX_RANDOM_NUMBER)
                    .limit(buttonQuantity - NUM_ONE)
                    .boxed()
                    .collect(Collectors.toList()));
            return list;
        }

        /**
         * This method is responsible for returning to the main page.
         *
         * @param frame - application window
         */
        private ActionListener getResetListener(JFrame frame) {
            return e -> {
                frame.getContentPane().removeAll();
                new IntroScreen(frame);
                frame.repaint();
            };
        }

        /**
         * This method is responsible for buttons sorting.
         *
         * @param frame - application window
         */
        private ActionListener getSortListener(JFrame frame) {
            return e -> {
                boolean ascendingOrder = Comparators.isInOrder(listOfNumbers, Comparator.naturalOrder());
                Integer[] array = getArrayFromList(listOfNumbers);
                QuickSort quickSort = new QuickSort(frame, array, ascendingOrder);
                Thread thread = new Thread(quickSort);
                thread.start();
            };
        }

        /**
         * This method is responsible for screen repainting when user pressed on a number button. If its name is
         * less or equals then 30 - the new buttons will be generated according to the pressed button name. If its name
         * will be more 30 - the pop-up message will be shown.
         *
         * @param frame - application window
         */
        private ActionListener getNumButtonListener(JFrame frame) {
            return e -> {
                int num = Integer.parseInt(e.getActionCommand());
                if (num > MAX_BUTTON_QUANTITY) {
                    JOptionPane.showMessageDialog(null, WRONG_QUANTITY_MESSAGE);
                } else {
                    frame.getContentPane().removeAll();
                    new SortScreen(frame, addListOfNumbers(num));
                    frame.repaint();
                }
            };
        }

        /**
         * This method is responsible for getting Array from List.
         *
         * @param listOfNumbers - list of numbers for buttons
         * @return array of numbers for buttons
         */
        private Integer[] getArrayFromList(List<Integer> listOfNumbers) {
            Integer[] array = new Integer[listOfNumbers.size()];
            return listOfNumbers.toArray(array);
        }
    }

    /**
     * This method is responsible for providing buttons
     *
     * @param name - button name
     * @param color - button color
     * @param actionListener - actionListener assigned to current button
     * @return new button
     */
    private JButton getJButton(String name, Color color, ActionListener actionListener) {
        button = new JButton(name);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, MAX_BUTTON_QUANTITY));
        button.setBackground(color);
        button.addActionListener(actionListener);
        return button;
    }

    /**
     * This method is responsible for providing textFields
     *
     * @param font - font for text in current textField
     * @return new textField
     */
    private JTextField getTextField(Font font) {
        textField = new JTextField(DEFAULT_LENGTH);
        textField.setFont(font);
        return textField;
    }

    /**
     * This method is responsible for getting textField from app instance
     *
     * @return current textField of the application
     */
    public JTextField getTextField() {
        return textField;
    }

    /**
     * Internal class. Thread that is responsible for sorting number buttons.
     */
    public class QuickSort implements Runnable {

        private boolean ascOrder;
        private Integer[] array;
        private JFrame frame;

        /**
         * Constructor
         *
         * @param  frame - application window
         * @param array - array of numbers for buttons
         * @param ascOrder - flag according to which sorting occurs
         */
        public QuickSort(JFrame frame, Integer[] array, boolean ascOrder) {
            this.ascOrder = ascOrder;
            this.array = array;
            this.frame = frame;
        }

        /**
         * Thread override method
         */
        @Override
        public void run() {
            quickSort(array, 0, array.length - 1, ascOrder);
        }

        /**
         * This method is responsible for sorting. It implements quick-sort algorithm.
         *
         * @param array - array of numbers for buttons
         * @param arrStart - start index of array to sort
         * @param arrEnd - end index of array to sort
         * @param ascOrder - flag according to which sorting occurs
         */
        public void quickSort(Integer[] array, int arrStart, int arrEnd, boolean ascOrder) {
            if (array.length == 0) {
                return;
            }
            if (arrStart >= arrEnd) {
                return;
            }

            int middleItem = array[arrStart + (arrEnd - arrStart) / 2];
            int tempStart = arrStart;
            int tempEnd = arrEnd;

            while (tempStart <= tempEnd) {
                if (!ascOrder) {
                    while (array[tempStart] < middleItem) {
                        tempStart++;
                    }
                    while (array[tempEnd] > middleItem) {
                        tempEnd--;
                    }
                } else {
                    while (array[tempStart] > middleItem) {
                        tempStart++;
                    }
                    while (array[tempEnd] < middleItem) {
                        tempEnd--;
                    }
                }
                if (tempStart <= tempEnd) {
                    int temp = array[tempStart];
                    array[tempStart] = array[tempEnd];
                    array[tempEnd] = temp;
                    tempStart++;
                    tempEnd--;
                }
                repaintAfterSorting(array);
            }

            if (arrStart < tempEnd) {
                quickSort(array, arrStart, tempEnd, ascOrder);
            }
            if (arrEnd > tempStart) {
                quickSort(array, tempStart, arrEnd, ascOrder);
            }
        }

        /**
         * This method is responsible for repainting number buttons after sorting.
         *
         * @param array - array of numbers for buttons
         */
        private void repaintAfterSorting(Integer[] array) {
            treadSleep();
            frame.getContentPane().removeAll();
            new SortScreen(frame, Arrays.asList(array));
            frame.repaint();
        }

        /**
         * This method is responsible for thread sleeping.
         */
        private void treadSleep() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
