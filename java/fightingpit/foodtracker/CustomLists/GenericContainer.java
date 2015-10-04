package fightingpit.foodtracker.CustomLists;

/**
 * Created by AG on 04-Oct-15.
 */
public class GenericContainer {
    private int intParam1;
    private int intParam2;
    private String stringParam1;
    private String stringParam2;
    private String stringParam3;

    public GenericContainer(String stringParam1) {
        this.stringParam1 = stringParam1;
    }

    public GenericContainer(int intParam1) {
        this.intParam1 = intParam1;
    }

    public GenericContainer(int intParam1, int intParam2) {
        this.intParam1 = intParam1;
        this.intParam2 = intParam2;
    }

    public GenericContainer(String stringParam1, String stringParam2) {

        this.stringParam1 = stringParam1;
        this.stringParam2 = stringParam2;
    }

    public GenericContainer(int intParam1, String stringParam1) {
        this.intParam1 = intParam1;
        this.stringParam1 = stringParam1;
    }

    public GenericContainer(int intParam1, String stringParam1, String stringParam2) {
        this.intParam1 = intParam1;
        this.stringParam1 = stringParam1;
        this.stringParam2 = stringParam2;
    }

    public GenericContainer(int intParam1, int intParam2, String stringParam1) {
        this.intParam1 = intParam1;
        this.intParam2 = intParam2;
        this.stringParam1 = stringParam1;
    }

    public GenericContainer(String stringParam1, String stringParam2, String stringParam3) {
        this.stringParam1 = stringParam1;
        this.stringParam2 = stringParam2;
        this.stringParam3 = stringParam3;
    }

    public GenericContainer(int intParam1, String stringParam1, String stringParam2, String stringParam3) {
        this.intParam1 = intParam1;
        this.stringParam1 = stringParam1;
        this.stringParam2 = stringParam2;
        this.stringParam3 = stringParam3;
    }

    public GenericContainer(int intParam1, int intParam2, String stringParam1, String stringParam2) {

        this.intParam1 = intParam1;
        this.intParam2 = intParam2;
        this.stringParam1 = stringParam1;
        this.stringParam2 = stringParam2;
    }

    public GenericContainer(int intParam1, int intParam2, String stringParam1, String stringParam2, String stringParam3) {
        this.intParam1 = intParam1;
        this.intParam2 = intParam2;
        this.stringParam1 = stringParam1;
        this.stringParam2 = stringParam2;
        this.stringParam3 = stringParam3;
    }

    public int getIntParam1() {
        return intParam1;
    }

    public void setIntParam1(int intParam1) {
        this.intParam1 = intParam1;
    }

    public int getIntParam2() {
        return intParam2;
    }

    public void setIntParam2(int intParam2) {
        this.intParam2 = intParam2;
    }

    public String getStringParam1() {
        return stringParam1;
    }

    public void setStringParam1(String stringParam1) {
        this.stringParam1 = stringParam1;
    }

    public String getStringParam2() {
        return stringParam2;
    }

    public void setStringParam2(String stringParam2) {
        this.stringParam2 = stringParam2;
    }

    public String getStringParam3() {
        return stringParam3;
    }

    public void setStringParam3(String stringParam3) {
        this.stringParam3 = stringParam3;
    }
}
