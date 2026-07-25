package view;

import javax.swing.*;

/**
 * A panel containing a label, a combo box, and a text field.
 */
class LabelComboBoxTextPanel extends JPanel {
    LabelComboBoxTextPanel(JLabel label, JComboBox comboBox, JTextField textField) {
        this.add(label);
        this.add(comboBox);
        this.add(textField);
    }
}
