package fightingpit.foodtracker.CustomLists;

/**
 * Created by AG on 29-Sep-15.
 */
public class ListSymptomsInAddMeal {
    private String name;
    private int valueFromSeekbar;
    private String displayValueInTextbar;

    public ListSymptomsInAddMeal(String symptomName) {
        this.name = symptomName;
        this.valueFromSeekbar=0;
        this.displayValueInTextbar="0";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValueFromSeekbar() {
        return valueFromSeekbar;
    }

    public void setValueFromSeekbar(int valueFromSeekbar) {
        this.valueFromSeekbar = valueFromSeekbar;
    }

    public String getDisplayValueInTextbar() {
        return displayValueInTextbar;
    }

    public void setDisplayValueInTextbar(String displayValueInTextbar) {
        this.displayValueInTextbar = displayValueInTextbar;
    }
}
