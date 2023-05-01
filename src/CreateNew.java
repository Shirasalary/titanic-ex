import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;

public class CreateNew  {



    public static JLabel newLabel (String text, int x, int y ,int kind)
    {
        JLabel label = new JLabel(text);
        if (kind == Constants.REGULAR_LABEL)
        {
            label.setBounds(x,y,Constants.LABEL_WIDTH,Constants.LABEL_HEIGHT);
        } else if (kind == Constants.ANSWER_LABEL) {
            label.setBounds(x,y,Constants.LABEL_WIDTH *3,Constants.LABEL_HEIGHT);
        }
        label.setFont(Constants.myFont);
        return label;
    }
    public static JTextField newTextFieldForNum(int x, int y)
    {
        JTextField textFiled = new JTextField();
        textFiled.setBounds(x,y,Constants.TEXT_FILED_WIDTH,Constants.TEXT_FILED_HEIGHT);
        textFiled.setFont(Constants.myFont);
        return textFiled;
    }

    public static JTextField newTextFieldForText(int x, int y)
    {
        JTextField textFiled = new JTextField();
        textFiled.setBounds(x,y,Constants.TEXT_FILED_WIDTH*2,Constants.TEXT_FILED_HEIGHT);
        textFiled.setFont(Constants.myFont);
        return textFiled;
    }

    public static JButton newButton (String text, int x, int y)
    {
        JButton button = new JButton(text);
        button.setBounds(x,y,Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        button.setFocusable(false);
        button.setFont(Constants.myFont);
        return button;
    }

    public static JComboBox newComboBox (String[] options, int x, int y)
    {
        JComboBox comboBox = new JComboBox(options);
        comboBox.setBounds(x,y,Constants.COMBO_BOX_WIDTH,Constants.COMBO_BOX_HEIGHT);
        comboBox.setFont(Constants.myFont);
        return comboBox;
    }

    public  static void showMessage(Component window,String message) {
        JOptionPane.showMessageDialog(window, message, message, JOptionPane.INFORMATION_MESSAGE);
    }


}

