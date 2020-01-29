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

public class App extends JFrame {

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

    public App() {
        insets = new Insets(DEFAULT_INDENT, DEFAULT_INDENT, 0, DEFAULT_INDENT);
        new IntroScreen(this);
    }

    public static void main(String[] args) {
        new App();
    }

    public class IntroScreen {
        private static final String WRONG_INPUT_MESSAGE = "The input must be number more than 0 and less than 300";
        private static final String INTRO_MESSAGE = "How many numbers to display?";
        private static final String ENTER_BUTTON = "Enter";

        public IntroScreen(JFrame frame) {
            addComponentsToIntroScreenContainer(frame);
            frame.setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

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
            container.add(getJTextField(font), gridBagConstraints);

            gridBagConstraints.gridy++;
            button = getJButton(ENTER_BUTTON, Color.CYAN, getNewScreenListener(frame));
            container.add(button, gridBagConstraints);
        }

        private JLabel getJLabel(Font font) {
            JLabel jLabel = new JLabel(INTRO_MESSAGE);
            jLabel.setFont(font);
            return jLabel;
        }

        private ActionListener getNewScreenListener(JFrame frame) {
            return e -> {
                String content = textField.getText();
                int buttonNum = checkValidInputAndGetNumber(content);
                if (checkInputRange(buttonNum)) {
                    frame.getContentPane().removeAll();
                    new SortScreen(frame);
                    frame.getContentPane().repaint();
                } else {
                    JOptionPane.showMessageDialog(null, WRONG_INPUT_MESSAGE);
                }
            };
        }

        private boolean checkInputRange(int buttonNum) {
            return buttonNum > 0 && buttonNum < 300;
        }

        private int checkValidInputAndGetNumber(String content) {
            int buttonNum = 0;
            if (StringUtils.isNumeric(content)) {
                buttonNum = Integer.parseInt(content);
            }
            return buttonNum;
        }
    }

    public class SortScreen {

        private static final String WRONG_QUANTITY_MESSAGE = "The number must be in the range from 1 to 30";
        private static final String RESET_BUTTON_NAME = "Reset";
        private static final String SORT_BUTTON_NAME = "Sort";
        private static final double WEIGHT_X = 0.5;
        private static final int NUM_ONE = 1;

        private List<Integer> listOfNumbers;

        public SortScreen(JFrame frame) {
            addComponentsToSortScreenContainer(frame);
            frame.setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        public SortScreen(JFrame frame, List<Integer> sortedList) {
            addSortedComponentsToSortScreenContainer(frame, sortedList);
            frame.setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

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

        private void addFuncButtons(GridBagConstraints gridBagConstraints, JFrame frame) {
            gridBagConstraints.gridx++;
            gridBagConstraints.gridy = 0;
            button = getJButton(SORT_BUTTON_NAME, Color.GREEN, getSortListener(frame));
            frame.add(button, gridBagConstraints);
            gridBagConstraints.gridy++;
            button = getJButton(RESET_BUTTON_NAME, Color.GREEN, getResetListener(frame));
            frame.add(button, gridBagConstraints);
        }

        private void addNumberButtons(GridBagConstraints gridBagConstraints, int buttonNum,
                                      List<Integer> listOfNumbers, JFrame frame) {
            gridBagConstraints.weightx = WEIGHT_X;
            gridBagConstraints.gridx = 0;
            for (int i = 0, colCounter = 0; i < buttonNum; i++, colCounter++) {
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

        private ActionListener getResetListener(JFrame frame) {
            return e -> {
                frame.getContentPane().removeAll();
                new IntroScreen(frame);
                frame.repaint();
            };
        }

        private ActionListener getSortListener(JFrame frame) {
            return e -> {
                boolean ascendingOrder = Comparators.isInOrder(listOfNumbers, Comparator.naturalOrder());
                Integer[] array = getArrayFromList(listOfNumbers);
                QuickSort quickSort = new QuickSort(frame, array, ascendingOrder);
                Thread thread = new Thread(quickSort);
                thread.start();
            };
        }

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

        private Integer[] getArrayFromList(List<Integer> listOfNumbers) {
            Integer[] array = new Integer[listOfNumbers.size()];
            return listOfNumbers.toArray(array);
        }
    }

    private JButton getJButton(String name, Color color, ActionListener actionListener) {
        button = new JButton(name);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, MAX_BUTTON_QUANTITY));
        button.setBackground(color);
        button.addActionListener(actionListener);
        return button;
    }

    private JTextField getJTextField(Font font) {
        textField = new JTextField(DEFAULT_LENGTH);
        textField.setFont(font);
        return textField;
    }

    public JTextField getTextField() {
        return textField;
    }

    public class QuickSort implements Runnable {

        private boolean ascOrder;
        private Integer[] array;
        private JFrame frame;

        public QuickSort(JFrame frame, Integer[] array, boolean ascOrder) {
            this.ascOrder = ascOrder;
            this.array = array;
            this.frame = frame;
        }

        @Override
        public void run() {
            quickSort(array, 0, array.length - 1, ascOrder);
        }

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

        private void repaintAfterSorting(Integer[] array) {
            treadSleep();
            frame.getContentPane().removeAll();
            new SortScreen(frame, Arrays.asList(array));
            frame.repaint();
        }

        private void treadSleep() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
