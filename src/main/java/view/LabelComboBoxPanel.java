package view;

import javax.swing.*;

/**
 * A panel containing a label and a text field.
 */
class LabelComboBoxPanel extends JPanel {
    LabelComboBoxPanel(JLabel label, JComboBox comboBox) {
        this.add(label);
        this.add(comboBox);
    }
}
