package in.krharsh17.barview;

public class BarModel {
    String label, value, color;
    Float fillRatio;

    public BarModel(String label, String value, String color, Float fillRatio) {
        this.label = label;
        this.value = value;
        this.color = color;
        this.fillRatio = fillRatio;
    }
}
