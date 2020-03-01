package in.krharsh17.barview;

public class BarModel {
    String label, value, color;
    Float fillRatio;

    public String getLabel() {
    	return this.label;
    }

    public void setLabel(String label) {
    	this.label = label;
    }

     public String getValue() {
    	return this.value;
    }

    public void setValue(String value) {
    	this.value = value;
    }

     public String getColor() {
    	return this.color;
    }

    public void setColor(String color) {
    	this.color = color;
    }

     public Float getFillRatio() {
    	return this.fillRatio;
    }

    public void setFillRatio(Float fillRatio) {
    	this.fillRatio = fillRatio;
    }

    public BarModel(String label, String value, String color, Float fillRatio) {
        this.label = label;
        this.value = value;
        this.color = color;
        this.fillRatio = fillRatio;
    }
}
