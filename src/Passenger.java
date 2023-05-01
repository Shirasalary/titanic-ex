public class Passenger{

    private String passengerId;
    private Integer survived;
    private Integer pClass;
    private String name;
    private String sex;
    private Double age;
    private Integer sibSp;
    private Integer parch;
    private String ticketNum;
    private Double ticketCost;
    private String cabinNum;
    private String embarked;

    public Passenger(String line)
    {
        if (line != null)
        {
            String[] details = line.split(Constants.FILTER_LINE);
            if (details[Constants.ID_INDEX].equals(Constants.EMPTY_TEXT_FIELD))
            {
                this.passengerId = Constants.STRING_MISSED_DETAIL;
            }else {
                this.passengerId = details[Constants.ID_INDEX];
            }

            if ( details[Constants.SURVIVED_INDEX].equals(Constants.EMPTY_TEXT_FIELD))
            {       this.survived = Constants.INTEGER_MISSED_DETAIL;
            }else {
                this.survived = Integer.parseInt(details[Constants.SURVIVED_INDEX]);
            }

            if (details[Constants.CLASS_INDEX].equals(Constants.EMPTY_TEXT_FIELD))
            {
                this.pClass = Constants.INTEGER_MISSED_DETAIL;
            }else {
                this.pClass = Integer.parseInt(details[Constants.CLASS_INDEX]);
            }

            if (details[Constants.LAST_NAME_INDEX].equals(Constants.EMPTY_TEXT_FIELD))
            {
                this.name = Constants.STRING_MISSED_DETAIL;
                setStateKnowName(false, details);

            }else {
                this.name = details[Constants.LAST_NAME_INDEX] +Constants.FILTER_LINE + details[Constants.FIRST_NAME_INDEX];
                setStateKnowName(true, details);
            }
        }


    }

    private void setStateKnowName(boolean isNameExist , String[] details)
    {
        if (details[Constants.SEX_INDEX].equals(Constants.EMPTY_TEXT_FIELD))
        {
            this.sex = Constants.STRING_MISSED_DETAIL;
        }else if (isNameExist){
            this.sex = details[Constants.SEX_INDEX];
        }else{
            this.sex = details[Constants.SEX_INDEX -1];
        }

        if (details[Constants.ADE_INDEX].equals(Constants.EMPTY_TEXT_FIELD))
        {
            this.age = Constants.DOUBLE_MISSED_DETAIL;
        }else if (isNameExist){
            this.age = Double.parseDouble(details[Constants.ADE_INDEX]);
        }else {
            this.age = Double.parseDouble(details[Constants.ADE_INDEX -1 ]);
        }

        if (details[Constants.SIB_SP_INDEX].equals(Constants.EMPTY_TEXT_FIELD))
        {
            this.sibSp = Constants.INTEGER_MISSED_DETAIL;
        }else  if (isNameExist) {
            this.sibSp = Integer.parseInt(details[Constants.SIB_SP_INDEX]);
        }else {
            this.sibSp = Integer.parseInt(details[Constants.SIB_SP_INDEX -1]);
        }

        if (details[Constants.PARCH_INDEX].equals(Constants.EMPTY_TEXT_FIELD))
        {
            this.parch = Constants.INTEGER_MISSED_DETAIL;
        }else if (isNameExist){
            this.parch = Integer.parseInt(details[Constants.PARCH_INDEX]);
        }else {
            this.parch = Integer.parseInt(details[Constants.PARCH_INDEX -1 ]);
        }

        if (details[Constants.TICKET_NUM_INDEX].equals(Constants.EMPTY_TEXT_FIELD))
        {
            this.ticketNum = Constants.STRING_MISSED_DETAIL;
        }else if (isNameExist){
            this.ticketNum = details[Constants.TICKET_NUM_INDEX];
        }else {
            this.ticketNum = details[Constants.TICKET_NUM_INDEX -1];
        }

        if (details[Constants.TICKET_COST_INDEX].equals(Constants.EMPTY_TEXT_FIELD))
        {
            this.ticketCost = Constants.DOUBLE_MISSED_DETAIL;
        }else if (isNameExist){
            this.ticketCost = Double.parseDouble(details[Constants.TICKET_COST_INDEX]);
        }else {
            this.ticketCost = Double.parseDouble(details[Constants.TICKET_COST_INDEX -1]);
        }

        if (details[Constants.CABIN_INDEX].equals(Constants.EMPTY_TEXT_FIELD))
        {
            this.cabinNum = Constants.STRING_MISSED_DETAIL;
        }else if (isNameExist){
            this.cabinNum = details[Constants.CABIN_INDEX];
        }else {
            this.cabinNum = details[Constants.CABIN_INDEX -1];
        }


        if (details.length == Constants.EMBARKED_INDEX  +1)
        {
             if (isNameExist){
                this.embarked = details[Constants.EMBARKED_INDEX];
            }else {
                this.embarked = details[Constants.EMBARKED_INDEX -1];
            }
        }else {
            this.embarked = Constants.STRING_MISSED_DETAIL;
        }

    }

    public String getFormattedName()
    {
        String name = null;
        if (this.name!= null) {
            name = this.name.substring(this.name.indexOf(Constants.CHAR_BEFORE_NAME) + Constants.SPACE_CHAR_FROM_NAME) + " ";
            name += this.name.substring(Constants.START_INDEX_OF_LAST_NAME, this.name.indexOf(Constants.FILTER_LINE));
            name = name.replace('"',' ');
            name = name.replace(")","");
            name = name.replace("(","");

        }
        return name;
    }


    public boolean isAlive()
    {
        boolean result = false;
        if (this.survived != null)
        {
            if (this.survived == Constants.ALIVE_PASSENGER)
                result = true;
        }

        return result;
    }

    public boolean isEqualPclass (int toEqual)
    {
        boolean result = false;
        if (this.pClass != null)
        {
            result = this.pClass == toEqual;
        }
        return result ;
    }

    public boolean isIdBetween (int min , int max)
    {
        boolean result = false;
        if (this.passengerId!=null)
        {
            result = Integer.parseInt(this.passengerId)>= min && Integer.parseInt(this.passengerId)<= max;
        }
        return result ;
    }

    public Double getTicketCost()
    {
        return this.ticketCost;
    }


    public boolean isContainsInName (String toCheck)
    {
        boolean result = false;
        if (this.name != null)
        {
            result = this.name.contains(toCheck);
        }
        return result;
    }

    public boolean isEqualSex(int toCheck)
    {
        boolean isEqualSex = false;
        if (this.sex !=null)
        {
            if (toCheck == Constants.MALE_OPTION_INDEX)
            {
                isEqualSex = this.sex.equals(Constants.GENDER_OPTIONS[Constants.MALE_OPTION_INDEX]);
            }else if (toCheck == Constants.FEMALE_OPTION_INDEX)
            {
                isEqualSex = this.sex.equals(Constants.GENDER_OPTIONS[Constants.FEMALE_OPTION_INDEX]);
            }
        }
        
        return isEqualSex;
    }

    public boolean isEqualSibSp (int toEqual)
    {
        boolean result = false;
        if (this.sibSp != null)
        {
            result = this.sibSp == toEqual;
        }
        return result;
    }

    public boolean isEqualParch (int toEqual)
    {
        boolean result = false;
        if (this.parch != null)
        {
            result = this.parch == toEqual;
        }
        return result;
    }

    public boolean isContainsTicketNum (String toCheck)
    {
        boolean result = false;
        if (this.ticketNum != null)
        {
            result = this.ticketNum.contains(toCheck);
        }
        return result;
    }

    public boolean isTicketCostBetween (double min , double max)
    {
        boolean result = false;
        if (this.ticketCost != null)
        {
            result = this.ticketCost>= min && this.ticketCost<= max;
        }
        return result;
    }


    public boolean isContainsCabinNum (String toCheck)
    {
        boolean result = false;
        if (this.cabinNum != null)
        {
            result = this.cabinNum.contains(toCheck);
        }
        return result;
    }

    public boolean isEqualEmbarked(int toCheck)
    {
        boolean isEqualEmbarked = false;
        if (this.embarked != null)
        {
            if (toCheck == Constants.S_OPTION_INDEX)
            {
                isEqualEmbarked = this.embarked.equals(Constants.EMBARKED_OPTIONS[Constants.S_OPTION_INDEX]);
            }else if (toCheck == Constants.C_OPTION_INDEX)
            {
                isEqualEmbarked = this.embarked.equals(Constants.EMBARKED_OPTIONS[Constants.C_OPTION_INDEX]);
            } else if (toCheck == Constants.Q_OPTION_INDEX) {
                isEqualEmbarked = this.embarked.equals(Constants.EMBARKED_OPTIONS[Constants.Q_OPTION_INDEX]);
            }
        }

        return isEqualEmbarked;
    }

    public boolean isAgeBetween0T010()
    {
        boolean result = false;
        if (this.age != null)
        {
            result = this.age>=0 && this.age<=10;
        }
        return result;
    }

    public boolean isAgeBetween11T020()
    {
        boolean result = false;
        if (this.age != null)
        {
            result = this.age>=11 && this.age<=20;
        }
        return result;
    }
    public boolean isAgeBetween21T030()
    {
        boolean result = false;
        if (this.age != null)
        {
            result = this.age>=21 && this.age<=30;
        }
        return result;
    }
    public boolean isAgeBetween31T040()
    {
        boolean result = false;
        if (this.age != null)
        {
            result = this.age>=31 && this.age<=40;
        }
        return result;
    }
    public boolean isAgeBetween41T050()
    {
        boolean result = false;
        if (this.age != null)
        {
            result = this.age>=41 && this.age<=50;
        }
        return result;
    }

    public boolean isAgeOver50()
    {
        boolean result = false;
        if (this.age != null)
        {
            result = this.age>=51;
        }
        return result;
    }

    public boolean isHaveFamily()
    {
        boolean result = false;
        int sum =0;
        if (this.sibSp != null)
        {
            sum+=this.sibSp;
            result = sum>0;
        }

        if (this.parch != null)
        {
            sum+=this.parch;
            result = sum>0;
        }
        return result;
    }

    public String convertToCSV()
    {
        return this.passengerId + "," + this.survived+ "," +this.pClass+ "," +this.name+ "," +this.sex
                + "," +this.age+ "," +this.sibSp+ "," +this.parch+ "," +this.ticketNum+ "," + this.ticketCost
                + "," + this.cabinNum+ "," +this.embarked;
    }
}
