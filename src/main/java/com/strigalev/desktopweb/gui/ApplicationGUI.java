package com.strigalev.desktopweb.gui;

import com.strigalev.desktopweb.model.Employee;
import com.strigalev.desktopweb.list.ListUsr;

import javax.swing.*;
import java.util.regex.Pattern;

public class ApplicationGUI extends JFrame {
    private final ListUsr<Employee> listUsr = new ListUsr<>();

    private JPanel mainPanel;
    private JRadioButton englishButton;
    private JRadioButton russianButton;
    private JTextField firstNameInput;
    private JTextField surnameInput;
    private JTextField emailInput;
    private JButton submitButton;
    private JLabel firstNameText;
    private JLabel surnameText;
    private JLabel emailText;
    private JRadioButton postButton;
    private JRadioButton getButton;
    private JLabel countLabel;


    public ApplicationGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        addTestEmployees();
        englishButton.addActionListener(e -> {
            if (!englishButton.isSelected() && !russianButton.isSelected()) {
                russianButton.setSelected(true);
                setFieldsForRus();
                return;
            }
            russianButton.setSelected(false);
            setFieldsForEng();
        });
        russianButton.addActionListener(e -> {
            if (!englishButton.isSelected() && !russianButton.isSelected()) {
                englishButton.setSelected(true);
                setFieldsForEng();
                return;
            }
            englishButton.setSelected(false);
            setFieldsForRus();
        });
        postButton.addActionListener(e -> {
            boolean flag = false;
            if (!postButton.isSelected() && !getButton.isSelected()) {
                getButton.setSelected(true);
                flag = true;
            }
            if (!flag) {
                getButton.setSelected(false);
            }
            if (englishButton.isSelected()) {
                setFieldsForEng();
            }
            if (russianButton.isSelected()) {
                setFieldsForRus();
            }
        });
        getButton.addActionListener(e -> {
            boolean flag = false;
            if (!postButton.isSelected() && !getButton.isSelected()) {
                postButton.setSelected(true);
                flag = true;
            }
            if (!flag) {
                postButton.setSelected(false);
            }
            if (englishButton.isSelected()) {
                setFieldsForEng();
            }
            if (russianButton.isSelected()) {
                setFieldsForRus();
            }
        });
        submitButton.addActionListener(e -> saveOrGetEmployee());
        setCountLabel();

    }

    private void setFieldsForEng() {
        if (postButton.isSelected()) {
            firstNameText.setVisible(true);
            firstNameInput.setVisible(true);
            firstNameText.setText("First name");
            surnameText.setText("Surname");
            emailText.setVisible(true);
            emailInput.setVisible(true);
            emailText.setText("Email");
            submitButton.setText("Save");
        }
        if (getButton.isSelected()) {
            firstNameText.setVisible(false);
            firstNameInput.setVisible(false);
            emailText.setVisible(false);
            emailInput.setVisible(false);
            surnameText.setText("Index");
            submitButton.setText("Enter");
        }
        postButton.setText("Post");
        getButton.setText("Get");
        surnameInput.setText("");
        setCountLabel();
    }

    private void setFieldsForRus() {
        if (postButton.isSelected()) {
            firstNameText.setVisible(true);
            firstNameInput.setVisible(true);
            firstNameText.setText("Имя");
            surnameText.setText("Фамилия");
            emailText.setVisible(true);
            emailInput.setVisible(true);
            emailText.setText("Адрес почты");
            submitButton.setText("Сохранить");
        }
        if (getButton.isSelected()) {
            firstNameText.setVisible(false);
            firstNameInput.setVisible(false);
            emailText.setVisible(false);
            emailInput.setVisible(false);
            surnameText.setText("Индекс");
            submitButton.setText("Ввод");
        }
        surnameInput.setText("");
        postButton.setText("Создать");
        getButton.setText("Просмотреть");
        setCountLabel();
    }

    private void saveOrGetEmployee() {
        if (postButton.isSelected()) {
            if (!validateInputInfo()) {
                return;
            }
            Employee employee = new Employee();
            employee.setId(listUsr.size());
            employee.setFirstName(firstNameInput.getText());
            firstNameInput.setText("");
            employee.setLastName(surnameInput.getText());
            surnameInput.setText("");
            employee.setEmailId(emailInput.getText());
            emailInput.setText("");
            listUsr.add(employee);
            setCountLabel();
            if (englishButton.isSelected()) {
                JOptionPane.showMessageDialog(null, "Employee created, index: " +
                        (listUsr.size() - 1), "Success", JOptionPane.PLAIN_MESSAGE);
            }
            if (russianButton.isSelected()) {
                JOptionPane.showMessageDialog(null, "Работник создан, индекс: "
                        + (listUsr.size() - 1), "Успешно", JOptionPane.PLAIN_MESSAGE);
            }
        }
        if (getButton.isSelected()) {
            int index;
            try {
                index = Integer.parseInt(surnameInput.getText());
            } catch (NumberFormatException ex) {
                if (englishButton.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Invalid index",
                            "Exception", JOptionPane.PLAIN_MESSAGE);
                }
                if (russianButton.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Неправильный индекс",
                            "Ошибка", JOptionPane.PLAIN_MESSAGE);
                }
                surnameInput.setText("");
                return;
            }
            if (index > listUsr.size() - 1) {
                if (englishButton.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Not exists with index " + index,
                            "Exception", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (russianButton.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Не существует с индексом " + index,
                            "Ошибка", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
            }
            Employee employee = listUsr.get(index);
            if (englishButton.isSelected()) {
                JOptionPane.showMessageDialog(null,
                        "First name: " + employee.getFirstName() +
                                "\nSurname: " + employee.getLastName() +
                                "\nEmail: " + employee.getEmailId(),
                        "Employee info", JOptionPane.PLAIN_MESSAGE);
            }
            if (russianButton.isSelected()) {
                JOptionPane.showMessageDialog(null,
                        "Имя: " + employee.getFirstName() +
                                "\nФамилия: " + employee.getLastName() +
                                "\nПочта: " + employee.getEmailId(),
                        "Информация о работнике", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private boolean validateInputInfo() {
        if (englishButton.isSelected()) {
            if (firstNameInput.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Not valid first name",
                        "Exception", JOptionPane.PLAIN_MESSAGE);
                firstNameInput.setText("");
                return false;
            }
            if (surnameInput.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Not valid surname",
                        "Exception", JOptionPane.PLAIN_MESSAGE);
                surnameInput.setText("");
                return false;
            }
            if (!isValidEmail(emailInput.getText())) {
                JOptionPane.showMessageDialog(null, "Not valid email",
                        "Exception", JOptionPane.PLAIN_MESSAGE);
                emailInput.setText("");
                return false;
            }
            return true;
        }
        if (russianButton.isSelected()) {
            if (!isValidEmail(emailInput.getText())) {
                JOptionPane.showMessageDialog(null, "Неправильный адрес почты",
                        "Ошибка", JOptionPane.PLAIN_MESSAGE);
                emailInput.setText("");
                return false;
            }
        }
        if (firstNameInput.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Неправильный ввод поля 'Имя'",
                    "Ошибка", JOptionPane.PLAIN_MESSAGE);
            firstNameInput.setText("");
            return false;
        }
        if (surnameInput.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Неправильный ввод поля 'Фамилия'",
                    "Ошибка", JOptionPane.PLAIN_MESSAGE);
            surnameInput.setText("");
            return false;
        }
        return true;
    }

    private void setCountLabel() {
        String msg = "";
        if (englishButton.isSelected()) {
            msg = "Actual employees count: " + listUsr.size();
        }
        if (russianButton.isSelected()) {
            msg = "Текущее количество рабочих: " + listUsr.size();
        }
        countLabel.setText(msg);
    }
    private void addTestEmployees() {
        Employee employee1 = new Employee();
        employee1.setEmailId("adfdsf@mail.ru");
        employee1.setFirstName("Mihail");
        employee1.setLastName("Zemskiy");
        listUsr.add(employee1);

        Employee employee2 = new Employee();
        employee2.setEmailId("asf@mail.ru");
        employee2.setFirstName("Alex");
        employee2.setLastName("Zeleniy");
        listUsr.add(employee2);

    }

    public static void main(String[] args) {
        JFrame frame = new ApplicationGUI("Test application");
        frame.setVisible(true);
    }

}

