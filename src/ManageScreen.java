import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ManageScreen extends JPanel {
    private JLabel classLabel;
    private JLabel passengerIdLabel;
    private JLabel nameLabel;
    private JLabel genderLabel;
    private JLabel sibSpLabel;
    private JLabel parchLabel;
    private JLabel ticketNumLabel;
    private JLabel ticketCostLabel;
    private JLabel cabinNumLabel;
    private JLabel embarkedLabel;
    private JLabel answerLabel;

    private JComboBox<String> classComboBox;
    private JComboBox<String> genderComboBox;
    private JComboBox<String> embarkedComboBox;

    private JTextField minPassengerIdText;
    private JTextField maxPassengerIdText;
    private JTextField minTicketCostText;
    private JTextField maxTicketCostText;
    private JTextField nameText;
    private JTextField sibSpText;
    private JTextField parchText;
    private JTextField ticketNumText;
    private JTextField cabinNumText;

    private JButton filterButton;
    private JButton statisticsButton;

    private Image background;

    public ManageScreen(int x, int y, int width, int height) {
        File file = new File(Constants.PATH_TO_DATA_FILE); //this is the path to the data file
        if (file.exists()) {
            this.setLayout(null);
            this.setBounds(x, y , width, height);

            addImage();
            createObjects();

            FilterData filterData = new FilterData(this,file,this.answerLabel,this.filterButton,this.statisticsButton,this.classComboBox
                    ,this.genderComboBox,this.embarkedComboBox,this.minPassengerIdText,
                    this.maxPassengerIdText,this.minTicketCostText,this.maxTicketCostText ,
                    this.nameText,this.sibSpText,this.parchText,this.ticketNumText,this.cabinNumText);

            addObjects();

        }
    }

    private void addImage()
    {
        try {
            this.background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/data/ship.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(this.background, 0, 0,
                Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, null);
    }

    private void createObjects()
    {
        this.classLabel =CreateNew.newLabel("Passenger Class: ", this.getX() +Constants.MARGIN_FROM_LEFT,
                this.getY()+Constants.MARGIN_FROM_TOP,Constants.REGULAR_LABEL);
        this.classComboBox =CreateNew.newComboBox(Constants.PASSENGER_CLASS_OPTIONS,
                this.classLabel.getX()+Constants.LABEL_WIDTH+Constants.MARGIN_FROM_LEFT
                ,this.classLabel.getY());
        this.passengerIdLabel = CreateNew.newLabel("Passenger ID: ",this.classLabel.getX(),
                this.classLabel.getY()+Constants.MARGIN_FROM_TOP + Constants.LABEL_HEIGHT,Constants.REGULAR_LABEL);
        this.minPassengerIdText = CreateNew.newTextFieldForNum(this.passengerIdLabel.getX()+Constants.LABEL_WIDTH+Constants.MARGIN_FROM_LEFT
                ,this.passengerIdLabel.getY());
        this.maxPassengerIdText = CreateNew.newTextFieldForNum(this.minPassengerIdText.getX()+Constants.TEXT_FILED_WIDTH+Constants.MARGIN_FROM_LEFT
                ,this.passengerIdLabel.getY());
        this.nameLabel=CreateNew.newLabel("Passenger Name: ",this.passengerIdLabel.getX(),
                this.passengerIdLabel.getY()+Constants.MARGIN_FROM_TOP + Constants.LABEL_HEIGHT,Constants.REGULAR_LABEL);
        this.nameText=CreateNew.newTextFieldForText(this.nameLabel.getX()+Constants.LABEL_WIDTH+Constants.MARGIN_FROM_LEFT
                ,this.nameLabel.getY());
        this.genderLabel =CreateNew.newLabel("Gender: ",this.nameLabel.getX(),
                this.nameLabel.getY()+Constants.MARGIN_FROM_TOP + Constants.LABEL_HEIGHT,Constants.REGULAR_LABEL);
        this.genderComboBox = CreateNew.newComboBox(Constants.GENDER_OPTIONS,
                this.genderLabel.getX()+Constants.LABEL_WIDTH+Constants.MARGIN_FROM_LEFT,this.genderLabel.getY());
        this.sibSpLabel=CreateNew.newLabel("Siblings Number: ",this.genderLabel.getX(),
                this.genderLabel.getY()+Constants.MARGIN_FROM_TOP + Constants.LABEL_HEIGHT,Constants.REGULAR_LABEL);
        this.sibSpText=CreateNew.newTextFieldForText(this.sibSpLabel.getX()+Constants.LABEL_WIDTH+Constants.MARGIN_FROM_LEFT
                ,this.sibSpLabel.getY());
        this.parchLabel = CreateNew.newLabel("Parch Number: ",this.sibSpLabel.getX(),
                this.sibSpLabel.getY()+Constants.MARGIN_FROM_TOP + Constants.LABEL_HEIGHT,Constants.REGULAR_LABEL);
        this.parchText=CreateNew.newTextFieldForText(this.parchLabel.getX()+Constants.LABEL_WIDTH+Constants.MARGIN_FROM_LEFT
                ,this.parchLabel.getY());
        this.ticketNumLabel = CreateNew.newLabel("Ticket Number: ",this.parchLabel.getX(),
                this.parchLabel.getY()+Constants.MARGIN_FROM_TOP + Constants.LABEL_HEIGHT,Constants.REGULAR_LABEL);
        this.ticketNumText=CreateNew.newTextFieldForText(this.ticketNumLabel.getX()+Constants.LABEL_WIDTH+Constants.MARGIN_FROM_LEFT
                ,this.ticketNumLabel.getY());
        this.ticketCostLabel = CreateNew.newLabel("Ticket Cost: ",this.ticketNumLabel.getX(),
                this.ticketNumLabel.getY()+Constants.MARGIN_FROM_TOP + Constants.LABEL_HEIGHT,Constants.REGULAR_LABEL);
        this.minTicketCostText = CreateNew.newTextFieldForNum(this.ticketCostLabel.getX()+Constants.LABEL_WIDTH+Constants.MARGIN_FROM_LEFT
                ,this.ticketCostLabel.getY());
        this.maxTicketCostText = CreateNew.newTextFieldForNum(this.minTicketCostText.getX()+Constants.TEXT_FILED_WIDTH+Constants.MARGIN_FROM_LEFT
                ,this.ticketCostLabel.getY());
        this.cabinNumLabel = CreateNew.newLabel("Cabin Number: ",this.ticketCostLabel.getX(),
                this.ticketCostLabel.getY()+Constants.MARGIN_FROM_TOP + Constants.LABEL_HEIGHT,Constants.REGULAR_LABEL);
        this.cabinNumText=CreateNew.newTextFieldForText(this.cabinNumLabel.getX()+Constants.LABEL_WIDTH+Constants.MARGIN_FROM_LEFT
                ,this.cabinNumLabel.getY());
        this.embarkedLabel=CreateNew.newLabel("Embarked: ",this.cabinNumLabel.getX(),
                this.cabinNumLabel.getY()+Constants.MARGIN_FROM_TOP + Constants.LABEL_HEIGHT,Constants.REGULAR_LABEL);
        this.embarkedComboBox = CreateNew.newComboBox(Constants.EMBARKED_OPTIONS,
                this.embarkedLabel.getX()+Constants.LABEL_WIDTH+Constants.MARGIN_FROM_LEFT,this.embarkedLabel.getY());
        this.answerLabel= CreateNew.newLabel("fill details",(Constants.WINDOW_WIDTH/3)*2,(Constants.WINDOW_HEIGHT/3)*2 - Constants.MARGIN_FROM_TOP
        ,Constants.ANSWER_LABEL);


        this.filterButton = CreateNew.newButton("Filter",
                this.answerLabel.getX() - (Constants.BUTTON_WIDTH/3)*2,
                this.answerLabel.getY()+Constants.LABEL_HEIGHT+Constants.MARGIN_FROM_TOP);

        this.statisticsButton = CreateNew.newButton("Create statistics",
                this.filterButton.getX()+ Constants.BUTTON_WIDTH + Constants.MARGIN_FROM_LEFT,
                this.filterButton.getY());
    }


    private void addObjects()
    {
        this.add(this.answerLabel);
        this.add(this.filterButton);
        this.add(this.statisticsButton);
        this.add(this.classComboBox);
        this.add(this.classLabel);
        this.add(this.passengerIdLabel);
        this.add(this.minPassengerIdText);
        this.add(this.maxPassengerIdText);
        this.add(this.nameLabel);
        this.add(this.nameText);
        this.add(this.genderLabel);
        this.add(this.genderComboBox);
        this.add(this.sibSpLabel);
        this.add(this.sibSpText);
        this.add(this.parchLabel);
        this.add(this.parchText);
        this.add(this.ticketNumLabel);
        this.add(this.ticketNumText);
        this.add(this.ticketCostLabel);
        this.add(this.minTicketCostText);
        this.add(this.maxTicketCostText);
        this.add(this.cabinNumLabel);
        this.add(this.cabinNumText);
        this.add(this.embarkedLabel);
        this.add(this.embarkedComboBox);
    }



}
