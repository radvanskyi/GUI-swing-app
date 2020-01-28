package com.radvanskyi;

import com.google.common.collect.Comparators;
import org.apache.commons.lang3.StringUtils;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class App {

    private static final int DEFAULT_SCREEN_WIDTH = 1200;
    private static final int DEFAULT_SCREEN_HEIGHT = 600;
    private static final int MAX_BUTTON_QUANTITY = 30;
    private static final int DEFAULT_INDENT = 20;
    private static final int DEFAULT_LENGTH = 10;
    private static final int BUTTON_WIDTH = 80;
    private static final int INCREMENT = 1;

    private Insets insets = new Insets(DEFAULT_INDENT, DEFAULT_INDENT, 0, DEFAULT_INDENT);
    private JTextField textField;
    private JButton button;

    public static void main(String[] args) {
        new App().new IntroScreen();
    }

    public class IntroScreen extends JFrame {

        public static final String WRONG_INPUT_MESSAGE = "The input must be number more than 0";
        private static final String INTRO_MESSAGE = "How many numbers to display?";
        private static final String INTRO_SCREEN_NAME = "Intro Screen";
        private static final String ENTER_BUTTON = "Enter";

        public IntroScreen() {
            JFrame introFrame = new JFrame(INTRO_SCREEN_NAME);
            addComponentsToIntroScreenContainer(introFrame);
            introFrame.setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
            introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            introFrame.setLocationRelativeTo(null);
            introFrame.setVisible(true);
        }

        private void addComponentsToIntroScreenContainer(JFrame introFrame) {
            Font font = new Font("Serif", Font.BOLD, DEFAULT_INDENT);
            Container container = introFrame.getContentPane();
            container.setLayout(new GridBagLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();

            gridBagConstraints.insets = insets;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            container.add(getJLabel(font), gridBagConstraints);

            gridBagConstraints.gridy++;
            container.add(getJTextField(font), gridBagConstraints);

            gridBagConstraints.gridy++;
            button = getJButton(ENTER_BUTTON, Color.CYAN, getNewScreenListener(introFrame));
            container.add(button, gridBagConstraints);
        }

        private JLabel getJLabel(Font font) {
            JLabel jLabel = new JLabel(INTRO_MESSAGE);
            jLabel.setFont(font);
            return jLabel;
        }

        private ActionListener getNewScreenListener(JFrame jFrame) {
            return e -> {
                String content = textField.getText();
                int buttonNum = checkValidInputAndGetNumber(content);
                if (buttonNum > 0) {
                    jFrame.setVisible(false);
                    new SortScreen();
                } else {
                    JOptionPane.showMessageDialog(null, WRONG_INPUT_MESSAGE);
                }
            };
        }


        private int checkValidInputAndGetNumber(String content) {
            int buttonNum = 0;
            if (StringUtils.isNumeric(content)) {
                buttonNum = Integer.parseInt(content);
            }
            return buttonNum;
        }
    }

    public class SortScreen extends JFrame {

        private static final String WRONG_QUANTITY_MESSAGE = "The number must be in the range from 1 to 30";
        private static final String SORT_SCREEN_NAME = "Sort Screen";
        private static final String RESET_BUTTON_NAME = "Reset";
        private static final String SORT_BUTTON_NAME = "Sort";
        private static final int MAX_RANDOM_NUMBER = 1000;
        private static final double WEIGHT_X = 0.5;

        private List<Integer> listOfNumbers;

        public SortScreen() {
            JFrame sortFrame = new JFrame(SORT_SCREEN_NAME);
            addComponentsToSortScreenContainer(sortFrame);
            sortFrame.setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
            sortFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            sortFrame.setLocationRelativeTo(null);
            sortFrame.setVisible(true);
        }

        public SortScreen(List<Integer> sortedList) {
            JFrame sortFrame = new JFrame(SORT_SCREEN_NAME);
            addSortedComponentsToSortScreenContainer(sortFrame, sortedList);
            sortFrame.setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
            sortFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            sortFrame.setLocationRelativeTo(null);
            sortFrame.setVisible(true);
        }

        private void addComponentsToSortScreenContainer(JFrame sortFrame) {
            Container container = sortFrame.getContentPane();
            container.setLayout(new GridBagLayout());
            int buttonQuantity = Integer.parseInt(textField.getText());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = insets;
            listOfNumbers = addListOfNumbers(buttonQuantity);
            addNumberButtons(gridBagConstraints, buttonQuantity, listOfNumbers, sortFrame);
            addFuncButtons(gridBagConstraints, sortFrame);
        }

        private void addSortedComponentsToSortScreenContainer(JFrame sortFrame, List<Integer> sortedList) {
            Container container = sortFrame.getContentPane();
            container.setLayout(new GridBagLayout());
            int buttonNum = sortedList.size();

            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = insets;
            listOfNumbers = sortedList;
            addNumberButtons(gridBagConstraints, buttonNum, listOfNumbers, sortFrame);
            addFuncButtons(gridBagConstraints, sortFrame);
        }

        private void addFuncButtons(GridBagConstraints gridBagConstraints, JFrame sortFrame) {
            gridBagConstraints.gridx++;
            gridBagConstraints.gridy = 0;
            button = getJButton(SORT_BUTTON_NAME, Color.GREEN, getSortListener(sortFrame));
            sortFrame.add(button, gridBagConstraints);
            gridBagConstraints.gridy++;
            button = getJButton(RESET_BUTTON_NAME, Color.GREEN, getResetListener(sortFrame));
            sortFrame.add(button, gridBagConstraints);
        }

        private void addNumberButtons(GridBagConstraints gridBagConstraints, int buttonNum,
                                      List<Integer> listOfNumbers, JFrame sortFrame) {
            gridBagConstraints.weightx = WEIGHT_X;
            gridBagConstraints.gridx = 0;
            for (int i = 0, colCounter = 0; i < buttonNum; i++, colCounter++) {
                if (colCounter == DEFAULT_LENGTH) {
                    colCounter = 0;
                    gridBagConstraints.gridx++;
                }
                gridBagConstraints.gridy = colCounter;
                String buttonName = String.valueOf(listOfNumbers.get(i));
                button = getJButton(buttonName, Color.CYAN, getNumButtonListener(sortFrame));
                sortFrame.add(button, gridBagConstraints);
            }
        }

        private List<Integer> addListOfNumbers(int buttonQuantity) {
            Random random = new Random();
            int requiredNum = random.nextInt(MAX_BUTTON_QUANTITY);
            List<Integer> list = new ArrayList<>();
            list.add(requiredNum);
            list.addAll(random.ints(0, MAX_RANDOM_NUMBER)
                    .limit(buttonQuantity - INCREMENT)
                    .boxed()
                    .collect(Collectors.toList()));
            return list;
        }

        private ActionListener getResetListener(JFrame sortFrame) {
            return e -> {
                sortFrame.dispose();
                new IntroScreen();
            };
        }

        private ActionListener getSortListener(JFrame sortFrame) {
            return e -> {
                boolean inAscendingOrder = Comparators.isInOrder(listOfNumbers, Comparator.naturalOrder());
                if (inAscendingOrder) {
                    listOfNumbers.sort(Collections.reverseOrder());
                } else {
                    Collections.sort(listOfNumbers);
                }
                sortFrame.dispose();
                new SortScreen(listOfNumbers);
            };
        }

        private ActionListener getNumButtonListener(JFrame sortFrame) {
            return e -> {
                int num = Integer.parseInt(e.getActionCommand());
                if (num > MAX_BUTTON_QUANTITY) {
                    JOptionPane.showMessageDialog(null, WRONG_QUANTITY_MESSAGE);
                } else {
                    sortFrame.dispose();
                    new SortScreen(listOfNumbers);
                }
            };
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
}
