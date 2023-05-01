import java.awt.*;

public class Constants {
    public static final String PATH_TO_DATA_FILE = "src/data/titanic.csv" ,PATH_TO_STATISTICS_FILE = "src/data/statistics.txt" ;

    public static final String PATH_TO_FILTER_FILE = "src/data/" , CSV_END_PATH = ".csv";
    public static final Font myFont = new Font("Gisha", Font.BOLD,18);
    public static final int WINDOW_WIDTH = 1200, WINDOW_HEIGHT = 650;
    public static final String[] PASSENGER_CLASS_OPTIONS = { "All", "1st", "2nd", "3rd"};
    public static final String[] GENDER_OPTIONS = { "All", "male", "female"};

    public static final int FIRST_CLASS_OPTION_INDEX = 1, SECOND_CLASS_OPTION_INDEX = 2, THIRD_CLASS_OPTION_INDEX = 3;
    public static final int MALE_OPTION_INDEX = 1, FEMALE_OPTION_INDEX = 2;
    public static final String[] EMBARKED_OPTIONS = { "All", "S", "C", "Q"};

    public static final int S_OPTION_INDEX = 1, C_OPTION_INDEX = 2, Q_OPTION_INDEX = 3, ALL_INDEX = 0;
    public static final String EMPTY_TEXT_FIELD = "";
    public static final int MARGIN_FROM_TOP = 20;
    public static final int MARGIN_FROM_LEFT = 15;
    public static final int LABEL_WIDTH = 160, LABEL_HEIGHT = 30;
    public static final int TEXT_FILED_WIDTH = 50, TEXT_FILED_HEIGHT = 25;
    public static final int COMBO_BOX_WIDTH = 80, COMBO_BOX_HEIGHT = 30;

    public static final int BUTTON_WIDTH = 175, BUTTON_HEIGHT = 30;
    public static final int ALIVE_PASSENGER = 1;
    public static final String STRING_MISSED_DETAIL = null;
    public static final Integer INTEGER_MISSED_DETAIL = null;
    public static final Double DOUBLE_MISSED_DETAIL = null;

    public static final int MIN_FOR_FILTER = 0;

    public static final String FILTER_LINE = ",", CHAR_BEFORE_NAME = ".";
    public static final int SPACE_CHAR_FROM_NAME = 2,START_INDEX_OF_LAST_NAME = 0;
    public static final int ALIVE_INDEX = 0, DEAD_INDEX = 1;

    public static final int ID_KIND = 1, TICKET_COST_KIND = 2;
    public static final int REGULAR_LABEL = 1, ANSWER_LABEL = 2;
    public static final int SIB_SP_KIND = 1, PARCH_KIND = 2,CABIN_KIND = 3,TICKET_NUM_KIND = 4  ;

    public static final int CREATE_PRESENT = 100;
    public static final int ID_INDEX = 0, SURVIVED_INDEX = 1 , CLASS_INDEX = 2,LAST_NAME_INDEX = 3
            ,FIRST_NAME_INDEX = 4 ,SEX_INDEX = 5,ADE_INDEX = 6, SIB_SP_INDEX = 7, PARCH_INDEX = 8,
            TICKET_NUM_INDEX = 9, TICKET_COST_INDEX = 10, CABIN_INDEX = 11, EMBARKED_INDEX = 12;

    public static final int MAX_PASSENGER = 891 , MIN_PASSENGER = 1;






}
