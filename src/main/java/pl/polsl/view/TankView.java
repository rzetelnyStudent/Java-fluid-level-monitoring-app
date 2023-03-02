/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import pl.polsl.controller.RandomButtonListener;
import pl.polsl.controller.SetIn1ButtonListener;
import pl.polsl.controller.SetIn2ButtonListener;
import pl.polsl.controller.SetOutButtonListener;
import pl.polsl.controller.SetRadiusButtonListener;
import pl.polsl.controller.TankController;
import pl.polsl.controller.TypedButtonListener;

/**
 * Fluid level monitoring application - view class. Represents view in MVC
 * pattern.
 *
 * @author Michał Kapuściński
 * @version 3.0
 */
public class TankView extends JFrame {

    /**
     * Model for the taable with historic data.
     */
    private final DefaultTableModel tableModel;

    /**
     * Displayed table with historic data.
     */
    private final JTable table;

    /**
     * Formatter of the data displayed in the table.
     */
    private final SimpleDateFormat formatter;

    /**
     * Reference to a tankController. Required only for adding listeners to
     * buttons.
     */
    private TankController tankController;

    /**
     * Label that displays "IN1" text.
     */
    private final JLabel labelIn1;

    /**
     * Label that displays IN1 flow value.
     */
    private final JLabel labelIn1Val;

    /**
     * Label that displays "IN2" text.
     */
    private final JLabel labelIn2;

    /**
     * Label that displays IN2 flow value.
     */
    private final JLabel labelIn2Val;

    /**
     * Label that displays "OUT" text.
     */
    private final JLabel labelOut;

    /**
     * Label that displays OUT flow value.
     */
    private final JLabel labelOutVal;

    /**
     * Label that displays "Average" text.
     */
    private final JLabel labelAvg;

    /**
     * Label that displays average average fluid height value.
     */
    private final JLabel labelAvgVal;

    /**
     * Label that displays "Min" text.
     */
    private final JLabel labelMin;

    /**
     * Label that displays average min fluid height value.
     */
    private final JLabel labelMinVal;

    /**
     * Label that displays "Max" text.
     */
    private final JLabel labelMax;

    /**
     * Label that displays max fluid height value.
     */
    private final JLabel labelMaxVal;

    /**
     * Constructor. Initialzes all labels with text strings.
     */
    public TankView() {
        this.setTitle("Fluid level monitoring");
        this.setSize(700, 600);
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        labelIn1 = new JLabel("IN1 [l/min]:");
        labelIn1Val = new JLabel("1.0");
        labelIn2 = new JLabel("IN2 [l/min]:");
        labelIn2Val = new JLabel("1.0");
        labelOut = new JLabel("OUT [l/min]:");
        labelOutVal = new JLabel("1.0");
        labelAvg = new JLabel("Average height [m]:");
        labelAvgVal = new JLabel("1.0");
        labelMin = new JLabel("Min height [m]:");
        labelMinVal = new JLabel("1.0");
        labelMax = new JLabel("Max height [m]:");
        labelMaxVal = new JLabel("1.0");
    }

    /**
     * Public method for attaching reference to tank controller. It is needed
     * for adding listeners to buttons.
     *
     * @param tankController reference to tank controller class.
     */
    public void setController(TankController tankController) {
        this.tankController = tankController;
    }

    /**
     * Public method for launching main GUI window.
     */
    public void startWindow() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setPreferredSize(new Dimension(100, 400));

        JButton setIn1Flow = new JButton("Set IN1");
        setIn1Flow.addActionListener(new SetIn1ButtonListener(tankController));
        JButton setIn2Flow = new JButton("Set IN2");
        setIn2Flow.addActionListener(new SetIn2ButtonListener(tankController));
        JButton setOutFlow = new JButton("Set OUT");
        setOutFlow.addActionListener(new SetOutButtonListener(tankController));
        JButton setRadius = new JButton("Set radius");
        setRadius.addActionListener(new SetRadiusButtonListener(tankController));
        JRadioButton setRandomValues = new JRadioButton("Random");
        setRandomValues.addActionListener(new RandomButtonListener(tankController));
        JRadioButton setTypedValues = new JRadioButton("Typed");
        setTypedValues.addActionListener(new TypedButtonListener(tankController));
        ButtonGroup group = new ButtonGroup();
        group.add(setRandomValues);
        group.add(setTypedValues);
        setTypedValues.setSelected(true);

        JLabel labelSettings = new JLabel("Settings:");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setPreferredSize(new Dimension(100, 50));
        buttonPanel.add(labelSettings);
        buttonPanel.add(setIn1Flow);
        buttonPanel.add(setIn2Flow);
        buttonPanel.add(setOutFlow);
        buttonPanel.add(setRadius);
        buttonPanel.add(setRandomValues);
        buttonPanel.add(setTypedValues);

        JPanel dataPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        dataPanel.setPreferredSize(new Dimension(100, 50));
        dataPanel.add(labelIn1);
        dataPanel.add(labelIn1Val);
        dataPanel.add(labelIn2);
        dataPanel.add(labelIn2Val);
        dataPanel.add(labelOut);
        dataPanel.add(labelOutVal);
        dataPanel.add(labelAvg);
        dataPanel.add(labelAvgVal);
        dataPanel.add(labelMin);
        dataPanel.add(labelMinVal);
        dataPanel.add(labelMax);
        dataPanel.add(labelMaxVal);

        tablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Historic height", TitledBorder.CENTER, TitledBorder.TOP));
        tableModel.addColumn("Time");
        tableModel.addColumn("Fluid level [m]");
        tablePanel.add(new JScrollPane(table));

        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(dataPanel, BorderLayout.CENTER);
        this.add(tablePanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    /**
     * Method that updates current fluid height level on the display.
     *
     * @param height current fluid level height in [m]
     */
    public void updateCurrentHeight(double height) {
        Date date = new Date();
        tableModel.insertRow(tableModel.getRowCount(), new Object[]{formatter.format(date), String.valueOf(height)});
    }

    /**
     * Method that updates IN1 data display field.
     *
     * @param value IN1 stream flow value.
     */
    public void udpateIn1Val(String value) {
        labelIn1Val.setText(value);
    }

    /**
     * Method that updates IN1 data display field.
     *
     * @param value IN2 stream flow value.
     */
    public void udpateIn2Val(String value) {
        labelIn2Val.setText(value);
    }

    /**
     * Method that updates OUT data display field.
     *
     * @param value OUT stream flow value.
     */
    public void udpateOutVal(String value) {
        labelOutVal.setText(value);
    }

    /**
     * Method that updates average data display field.
     *
     * @param value average value of historic height.
     */
    public void udpateAvgVal(String value) {
        labelAvgVal.setText(value);
    }

    /**
     * Method that updates min data display field.
     *
     * @param value minimal fluid height from historic data.
     */
    public void udpateMinVal(String value) {
        labelMinVal.setText(value);
    }

    /**
     * Method that updates max data display field.
     *
     * @param value maximal fluid height from historic data.
     */
    public void udpateMaxVal(String value) {
        labelMaxVal.setText(value);
    }
}
