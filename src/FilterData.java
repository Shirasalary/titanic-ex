import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.*;


public class FilterData {

    private List<Passenger> data;
    private JButton filterButton;
    private JButton statisticsButton;
    private JLabel answerLabel;

    private Component window;

    private static int countFilter = 0;

    public FilterData(Component window,File dataFile, JLabel answerLabel, JButton filterButton, JButton statisticsButton, JComboBox<String> classComboBox
    , JComboBox<String> genderComboBox, JComboBox<String> embarkedComboBox, JTextField minPassengerIdText,
                      JTextField maxPassengerIdText, JTextField minTicketCostText, JTextField maxTicketCostText ,
                      JTextField nameText, JTextField sibSpText, JTextField parchText, JTextField ticketNumText, JTextField cabinNumText  )
    {

        this.data = readDataToPassenger(dataFile);
        this.filterButton = filterButton;
        this.statisticsButton = statisticsButton;
        this.answerLabel = answerLabel;
        this.window = window;

        this.filterButton.addActionListener((e) -> {

            if (checkUserEnterValid(minPassengerIdText,maxPassengerIdText,minTicketCostText,maxTicketCostText,sibSpText,parchText,cabinNumText,ticketNumText))
            {
                List<Passenger> filterData= filterByParameters(classComboBox,genderComboBox, embarkedComboBox, minPassengerIdText,
                        maxPassengerIdText, minTicketCostText, maxTicketCostText ,
                        nameText,  sibSpText,parchText, ticketNumText, cabinNumText );

                int[] statusPassengers = statusPassengers(filterData);
                this.answerLabel.setText("Total Rows: " + filterData.size() + " (" + statusPassengers[Constants.ALIVE_INDEX] + " survived, "
                        + statusPassengers[Constants.DEAD_INDEX] + " did not)");

                sortByName(filterData);

                countFilter ++;
                File filterFile = createFile(Constants.PATH_TO_FILTER_FILE+countFilter+Constants.CSV_END_PATH);
                writePassengerToCSVFile(filterFile,filterData);
            }

        });

        this.statisticsButton.addActionListener((e )->{
            File statisticsFile = createFile(Constants.PATH_TO_STATISTICS_FILE);
            writeToTextFile(statisticsFile,calculateStatisticsData());
            this.answerLabel.setText("Successfully created! Enter to data");
        } );


    }

    private boolean isValidRange(String min, String max) {
        boolean isValid = false;
        if (isOnlyNumbers(min) && isOnlyNumbers(max)) {
            int minNumber = Integer.parseInt(min);
            int maxNumber = Integer.parseInt(max);
            if (minNumber > 0 && maxNumber > 0 && maxNumber > minNumber)
                isValid = true;
        }
        return isValid;
    }

    private boolean isOnlyNumbers(String number) {
        boolean only = false;
        int counter = 0;
        for (int i = 0; i < number.length(); i++) {
            if (Character.isDigit(number.charAt(i)))
                counter++;
        }
        if (counter == number.length())
            only = true;
        return only;
    }

    private boolean checkUserEnterValid(JTextField minPassengerIdText,JTextField maxPassengerIdText,JTextField minTicketCostText,JTextField maxTicketCostText
    ,JTextField sibSpText,JTextField parchText, JTextField cabinNumText ,JTextField ticketNumText)
    {
        boolean result = true;
        if (!isMinAndMaxDataValid(minPassengerIdText.getText(),maxPassengerIdText.getText(),Constants.ID_KIND))
        {
            result = false;
        }

        if (!isMinAndMaxDataValid(minTicketCostText.getText(),maxTicketCostText.getText(),Constants.TICKET_COST_KIND))
        {
            result = false;
        }

        if (!isOnlyOneDigit(sibSpText,Constants.SIB_SP_KIND))
        {
            result = false;
        }
        if (!isOnlyOneDigit(parchText,Constants.PARCH_KIND))
        {
            result = false;
        }
        if (!isOnlyOneDigit(cabinNumText,Constants.CABIN_KIND))
        {
            result = false;
        }
        if (!isOnlyOneDigit(ticketNumText,Constants.TICKET_NUM_KIND))
        {
            result = false;
        }

        return result;
    }

    private boolean isOnlyOneDigit(JTextField toCheck , int kind)
    {
        boolean result = true;
        if (!toCheck.getText().equals(Constants.EMPTY_TEXT_FIELD))
        {
            if (toCheck.getText().length() > 1 || !isOnlyNumbers(toCheck.getText()))
            {
                if (kind == Constants.SIB_SP_KIND)
                {
                    CreateNew.showMessage(this.window,"Please enter only one digit to SibSp");
                } else if (kind == Constants.PARCH_KIND) {
                    CreateNew.showMessage(this.window,"Please enter only one digit to parch");
                } else if (kind == Constants.CABIN_KIND) {
                    CreateNew.showMessage(this.window,"Please enter only one digit to cabin num");
                } else if (kind == Constants.TICKET_NUM_KIND) {
                    CreateNew.showMessage(this.window,"Please enter only one digit to ticket num");
                }

                result = false;
            }
        }
        return result;
    }

    private boolean isMinAndMaxDataValid(String min, String max , int kind)
    {
        boolean result = true;
        if (!min.equals(Constants.EMPTY_TEXT_FIELD) && !max.equals(Constants.EMPTY_TEXT_FIELD) )
        {
            if (!isValidRange(min,max))
            {
                if (kind == Constants.ID_KIND)
                {
                    CreateNew.showMessage(this.window,"Passenger Id Not Valid");
                } else if (kind == Constants.TICKET_COST_KIND) {
                    CreateNew.showMessage(this.window,"Ticket Cost Not Valid");
                }

                result = false;
            }
        } else if (!min.equals(Constants.EMPTY_TEXT_FIELD) || !max.equals(Constants.EMPTY_TEXT_FIELD)) {

            if (!isOnlyNumbers(min) ||!isOnlyNumbers(max) )
            {
                CreateNew.showMessage(this.window,"Please enter only numbers AT Passenger Id and Ticket Cost");
                result = false;
            }
        }
        return result;
    }

    private void sortByName(List<Passenger> passengers)
    {
        for (int i = 0; i<passengers.size(); i++)
        {
            for (int j = i+1; j<passengers.size(); j++)
            {
                String nameIndexI = passengers.get(i).getFormattedName();
                String nameIndexJ = passengers.get(j).getFormattedName();
                if (nameIndexI!=null && nameIndexJ!=null)
                {
                    if (nameIndexI.compareTo(nameIndexJ) >0)
                    {
                        Passenger tempIndexI = passengers.get(i);
                        Passenger tempIndexJ = passengers.get(j);
                        passengers.set(i,tempIndexJ);
                        passengers.set(j, tempIndexI);

                    }
                }

            }
        }
    }

    private File createFile(String path) {
        File file = new File(path);
        if (!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.getMessage();
            }
        }
        return file;
    }

    private void writePassengerToCSVFile(File file, List<Passenger> data) {
        try {
            if (file != null && file.exists()) {
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                for (int i = 0; i< data.size(); i++)
                {
                    bufferedWriter.write(data.get(i).convertToCSV());
                    bufferedWriter.write("\n");
                }
                bufferedWriter.close();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private static void writeToTextFile(File file, String data) {
        try {
            if (file != null && file.exists()) {
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(data);
                bufferedWriter.close();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private String  calculateStatisticsData()
    {

        int[] statusClass1 = statusPassengers(filterByClass(Constants.FIRST_CLASS_OPTION_INDEX,this.data));
        int[] statusClass2 = statusPassengers(filterByClass(Constants.SECOND_CLASS_OPTION_INDEX,this.data));
        int[] statusClass3 = statusPassengers(filterByClass(Constants.THIRD_CLASS_OPTION_INDEX,this.data));

        int[] statusMale = statusPassengers(filterByGender(Constants.MALE_OPTION_INDEX,this.data));
        int[] statusFemale = statusPassengers(filterByGender(Constants.FEMALE_OPTION_INDEX,this.data));

        int[] statusTicketCostBetween0To10 = statusPassengers (filterByTicketCost("0","10",this.data));
        int[] statusTicketCostBetween11To30 = statusPassengers (filterByTicketCost("11","30",this.data));
        int[] statusTicketCostOver30 = statusPassengers (filterByTicketCost("31","",this.data));

        int[] statusDeckS = statusPassengers (filterByEmbarked(Constants.S_OPTION_INDEX,this.data));
        int[] statusDeckC = statusPassengers (filterByEmbarked(Constants.C_OPTION_INDEX,this.data));
        int[] statusDeckQ = statusPassengers (filterByEmbarked(Constants.Q_OPTION_INDEX,this.data));

        String result = "Survival percentages in the Class: \n";
        result+="First class: " + ((double) statusClass1[Constants.ALIVE_INDEX] / (statusClass1[Constants.ALIVE_INDEX]+ statusClass1[Constants.DEAD_INDEX]))*Constants.CREATE_PRESENT +" % \n";
        result+="Second class: " + ((double) statusClass2[Constants.ALIVE_INDEX] / (statusClass2[Constants.ALIVE_INDEX]+ statusClass2[Constants.DEAD_INDEX]))*Constants.CREATE_PRESENT+"% \n";
        result+="Third class: " +( (double) statusClass3[Constants.ALIVE_INDEX] / (statusClass3[Constants.ALIVE_INDEX]+ statusClass3[Constants.DEAD_INDEX]))*Constants.CREATE_PRESENT+"% \n";
        result+="Survival percentages by Gender: \n Male: " +( (double) statusMale[Constants.ALIVE_INDEX] / (statusMale[Constants.ALIVE_INDEX]+ statusMale[Constants.DEAD_INDEX]))*Constants.CREATE_PRESENT+" % \n";
        result+="Female: " + ((double) statusFemale[Constants.ALIVE_INDEX] / (statusFemale[Constants.ALIVE_INDEX]+ statusFemale[Constants.DEAD_INDEX]))*Constants.CREATE_PRESENT+" % \n";
        result+=calculateAgeStatistics() +"\n";
        result+= calculateStatisticsByFamily() +"\n";
        result+="Survival percentages by Ticket Cost: \n Between 0 To 10: "
                +((double) statusTicketCostBetween0To10[Constants.ALIVE_INDEX] / (statusTicketCostBetween0To10[Constants.ALIVE_INDEX]+ statusTicketCostBetween0To10[Constants.DEAD_INDEX]))*Constants.CREATE_PRESENT+"% \n";
        result+="Between 11 To 30: " +((double) statusTicketCostBetween11To30[Constants.ALIVE_INDEX] / (statusTicketCostBetween11To30[Constants.ALIVE_INDEX]+ statusTicketCostBetween11To30[Constants.DEAD_INDEX]))*Constants.CREATE_PRESENT+"% \n";
        result+="Over 30: " +((double) statusTicketCostOver30[Constants.ALIVE_INDEX] / (statusTicketCostOver30[Constants.ALIVE_INDEX]+ statusTicketCostOver30[Constants.DEAD_INDEX]))*Constants.CREATE_PRESENT+"% \n";
        result+="Survival rate on decks: \n S Deck: " + ((double) statusDeckS[Constants.ALIVE_INDEX] / (statusDeckS[Constants.ALIVE_INDEX]+ statusDeckS[Constants.DEAD_INDEX]) )*Constants.CREATE_PRESENT+" % \n";
        result+="C Deck: " + ((double) statusDeckC[Constants.ALIVE_INDEX] / (statusDeckC[Constants.ALIVE_INDEX]+ statusDeckC[Constants.DEAD_INDEX]))*Constants.CREATE_PRESENT+"% \n";
        result+="Q Deck: " + ((double) statusDeckQ[Constants.ALIVE_INDEX] / (statusDeckQ[Constants.ALIVE_INDEX]+ statusDeckQ[Constants.DEAD_INDEX]))*Constants.CREATE_PRESENT+"% \n";

       return result;

    }

    private String calculateStatisticsByFamily()
    {
        double countAliveHaveFamily = 0;
        double countHaveFamily = 0;
        double countAliveNotHaveFamily = 0;
        for (Passenger passenger: this.data)
        {
            if (passenger.isAlive() && passenger.isHaveFamily())
            {
                countAliveHaveFamily++;
            } else if (passenger.isAlive() && !passenger.isHaveFamily()) {
                countAliveNotHaveFamily++;
            }
            if (passenger.isHaveFamily()) {
                countHaveFamily++;
            }
        }

        return "Survival percentages of those who have a family: " + (countAliveHaveFamily /countHaveFamily )*Constants.CREATE_PRESENT
                +" % \n" + "Survival percentages of those who not have a family: " +
                (countAliveNotHaveFamily/ (this.data.size() -countHaveFamily) )*Constants.CREATE_PRESENT + " % ";

    }

    private String calculateAgeStatistics()
    {
        double countAliveBetween0T010 = 0;
        double countAliveBetween11T020 = 0;
        double countAliveBetween21T030 = 0;
        double countAliveBetween31T040 = 0;
        double countAliveBetween41T050 = 0;
        double countAliveOver50 = 0;
        double countBetween0T010 = 0;
        double countBetween11T020 = 0;
        double countBetween21T030 = 0;
        double countBetween31T040 = 0;
        double countBetween41T050 = 0;
        double countOver50 = 0;
        for (Passenger passenger: this.data)
        {
            if (passenger.isAlive() && passenger.isAgeBetween0T010())
            {
                countAliveBetween0T010++;
            } else if (passenger.isAlive() && passenger.isAgeBetween11T020()) {
                countAliveBetween11T020++;
            } else if (passenger.isAlive() && passenger.isAgeBetween21T030()) {
                countAliveBetween21T030++;
            } else if (passenger.isAlive() && passenger.isAgeBetween31T040()) {
                countAliveBetween31T040++;
            } else if (passenger.isAlive() && passenger.isAgeBetween41T050()) {
                countAliveBetween41T050++;
            }else if (passenger.isAlive() && passenger.isAgeOver50()){
                countAliveOver50++;
            }

            if (passenger.isAgeBetween0T010()) {
                countBetween0T010++;
            } else if (passenger.isAgeBetween11T020()) {
                countBetween11T020++;
            } else if (passenger.isAgeBetween21T030()) {
                countBetween21T030++;
            } else if (passenger.isAgeBetween31T040()) {
                countBetween31T040++;
            } else if (passenger.isAgeBetween41T050()) {
                countBetween41T050++;
            } else if (passenger.isAgeOver50()) {
                countOver50++;
            }
        }

        return "Survival rates by age: \n Between 0 T0 10: " + (countAliveBetween0T010/countBetween0T010)*Constants.CREATE_PRESENT
                +" % \n Between 11 T0 20: " + (countAliveBetween11T020/countBetween11T020)*Constants.CREATE_PRESENT +
                " % \n Between 21 T0 30: " + (countAliveBetween21T030/countBetween21T030)*Constants.CREATE_PRESENT +
                " % \n Between 31 T0 40: " + (countAliveBetween31T040/countBetween31T040)*Constants.CREATE_PRESENT+
                " % \n Between 41 T0 50: "+ (countAliveBetween41T050/countBetween41T050)*Constants.CREATE_PRESENT+
                " % \n Over 50 : " + (countAliveOver50/countOver50)*Constants.CREATE_PRESENT +" %";
    }


    private int[] statusPassengers(List<Passenger> passengers )
    {
        int[] result = new int[2];
        int countAlive = 0;
        for (Passenger passenger: passengers)
        {
            if (passenger.isAlive())
            {
                countAlive++;
            }
        }
        result[Constants.ALIVE_INDEX] = countAlive;
        result[Constants.DEAD_INDEX] = passengers.size() - countAlive;
        return result;
    }

    private List<Passenger> filterByParameters(JComboBox<String> classComboBox
            ,JComboBox<String> genderComboBox,JComboBox<String> embarkedComboBox,JTextField minPassengerIdText,
            JTextField maxPassengerIdText,JTextField minTicketCostText,JTextField maxTicketCostText ,
            JTextField nameText, JTextField sibSpText,JTextField parchText,JTextField ticketNumText
            ,JTextField cabinNumText)
    {
        List<Passenger> filterData = filterByClass(classComboBox.getSelectedIndex(),this.data);
        filterData = filterById(minPassengerIdText,maxPassengerIdText,filterData);
        filterData = filterByName(nameText,filterData);
        filterData = filterByGender(genderComboBox.getSelectedIndex(),filterData);
        filterData = filterBySibSp(sibSpText,filterData);
        filterData = filterByParch(parchText,filterData);
        filterData = filterByTicketNum(ticketNumText,filterData);
        filterData = filterByTicketCost(minTicketCostText.getText(),maxTicketCostText.getText(),filterData);
        filterData = filterByCabinNum(cabinNumText,filterData);
        filterData = filterByEmbarked(embarkedComboBox.getSelectedIndex(),filterData);

        return filterData;
    }

    private List<Passenger> filterByClass (int selectedIndex ,List<Passenger> filterData )
    {
        if (selectedIndex != Constants.ALL_INDEX)
        {
            filterData = filterData.stream().filter(passenger -> passenger.isEqualPclass(selectedIndex)).collect(Collectors.toList());
        }
        return filterData;
    }

    private List<Passenger> filterById (JTextField minPassengerIdText,
                                        JTextField maxPassengerIdText ,List<Passenger> filterData )
    {
        if (!minPassengerIdText.getText().equals(Constants.EMPTY_TEXT_FIELD) && !maxPassengerIdText.getText().equals(Constants.EMPTY_TEXT_FIELD))
        {
            filterData = filterData.stream().filter(passenger ->
                    passenger.isIdBetween(Integer.parseInt(minPassengerIdText.getText()),Integer.parseInt(maxPassengerIdText.getText()))).collect(Collectors.toList());
        } else if (minPassengerIdText.getText().equals(Constants.EMPTY_TEXT_FIELD) && !maxPassengerIdText.getText().equals(Constants.EMPTY_TEXT_FIELD)) {
            filterData = filterData.stream().filter(passenger ->
                    passenger.isIdBetween(Constants.MIN_PASSENGER,Integer.parseInt(maxPassengerIdText.getText()))).collect(Collectors.toList());
        }else if (!minPassengerIdText.getText().equals(Constants.EMPTY_TEXT_FIELD) &&maxPassengerIdText.getText().equals(Constants.EMPTY_TEXT_FIELD)){

            filterData = filterData.stream().filter(passenger ->
                    passenger.isIdBetween(Integer.parseInt(minPassengerIdText.getText()),Constants.MAX_PASSENGER)).collect(Collectors.toList());
        }
        return filterData;
    }

    private List<Passenger> filterByName (JTextField nameText ,List<Passenger> filterData )
    {
        if (!nameText.getText().equals(Constants.EMPTY_TEXT_FIELD))
        {
            filterData = filterData.stream().filter(passenger -> passenger.isContainsInName(nameText.getText())).collect(Collectors.toList());
        }
        return filterData;
    }

    private List<Passenger> filterByGender (int selectedIndex ,List<Passenger> filterData )
    {
        if (selectedIndex!=Constants.ALL_INDEX)
        {
            filterData = filterData.stream().filter(passenger -> passenger.isEqualSex(selectedIndex)).collect(Collectors.toList());
        }
        return filterData;
    }

    private List<Passenger> filterBySibSp (JTextField sibSpText ,List<Passenger> filterData )
    {
        if (!sibSpText.getText().equals(Constants.EMPTY_TEXT_FIELD))
        {
            filterData = filterData.stream().filter(passenger -> passenger.isEqualSibSp(Integer.parseInt(sibSpText.getText()))).collect(Collectors.toList());
        }
        return filterData;
    }

    private List<Passenger> filterByParch (JTextField parchText ,List<Passenger> filterData )
    {
        if (!parchText.getText().equals(Constants.EMPTY_TEXT_FIELD))
        {
            filterData = filterData.stream().filter(passenger -> passenger.isEqualParch(Integer.parseInt(parchText.getText()))).collect(Collectors.toList());
        }
        return filterData;
    }

    private List<Passenger> filterByTicketNum (JTextField ticketNumText,List<Passenger> filterData )
    {
        if (!ticketNumText.getText().equals(Constants.EMPTY_TEXT_FIELD))
        {
            filterData = filterData.stream().filter(passenger -> passenger.isContainsTicketNum(ticketNumText.getText())).collect(Collectors.toList());
        }
        return filterData;
    }

    private List<Passenger> filterByTicketCost (String minTicketCostText,String maxTicketCostText ,List<Passenger> filterData )
    {
        if (!minTicketCostText.equals(Constants.EMPTY_TEXT_FIELD) && !maxTicketCostText.equals(Constants.EMPTY_TEXT_FIELD))
        {
            filterData = filterData.stream().filter(passenger ->
                    passenger.isTicketCostBetween(Double.parseDouble(minTicketCostText),Double.parseDouble(maxTicketCostText))).collect(Collectors.toList());
        } else if (minTicketCostText.equals(Constants.EMPTY_TEXT_FIELD) && !maxTicketCostText.equals(Constants.EMPTY_TEXT_FIELD)) {

            filterData = filterData.stream().filter(passenger ->
                    passenger.isTicketCostBetween(Constants.MIN_FOR_FILTER,Double.parseDouble(maxTicketCostText))).collect(Collectors.toList());
        }else if (!minTicketCostText.equals(Constants.EMPTY_TEXT_FIELD) && maxTicketCostText.equals(Constants.EMPTY_TEXT_FIELD)){
           double highestTicketCost = getHighestTicketCost(filterData);
            filterData = filterData.stream().filter(passenger ->
                    passenger.isTicketCostBetween(Double.parseDouble(minTicketCostText),highestTicketCost )).collect(Collectors.toList());
        }
        return filterData;
    }

    private List<Passenger> filterByCabinNum (JTextField cabinNumText,List<Passenger> filterData )
    {
        if (!cabinNumText.getText().equals(Constants.EMPTY_TEXT_FIELD))
        {
            filterData = filterData.stream().filter(passenger -> passenger.isContainsCabinNum(cabinNumText.getText())).collect(Collectors.toList());
        }
        return filterData;
    }

    private List<Passenger> filterByEmbarked (int selectedIndex ,List<Passenger> filterData )
    {
        if (selectedIndex != Constants.ALL_INDEX)
        {
            filterData = filterData.stream().filter(passenger -> passenger.isEqualEmbarked(selectedIndex)).collect(Collectors.toList());
        }
        return filterData;
    }

    private double getHighestTicketCost(List<Passenger> data)
    {
        double result = 0;
        for (Passenger passenger: data)
        {
            if (passenger.getTicketCost() != null)
            {
                if (passenger.getTicketCost() > result)
                {
                    result = passenger.getTicketCost();
                }
            }

        }
        return result;
    }

    private List<Passenger> linesToPassenger(List<String> list)
    {
        List<Passenger> passengers= new ArrayList<>();
        for (String line : list )
        {
            Passenger passenger = new Passenger(line);
            passengers.add(passenger);
        }
        return passengers;
    }

    private List<Passenger> readDataToPassenger(File file)
    {
        List<String> lines = readFromFile(file);
        return linesToPassenger(lines);
    }

    private List<String> readFromFile(File file) {
        List<String> lines = new ArrayList<>();
        if (file != null && file.exists())
        {
            BufferedReader bufferedReader = null;
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                String line = bufferedReader.readLine();
                while (line != null)
                {
                    lines.add(line);
                    line = bufferedReader.readLine();
                }
                lines.remove(0);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();
                    if (fileReader != null)
                        fileReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }
        return lines;
    }

}
