package in.krharsh17.barview;

/**
 * This class is represents a BarView entity and contains all the attributes
 * required to instantiate a BarGroup
 */
public class BarModel {
    private String label;
    private String value;
    private String color;
    private Float fillRatio;
    private int elevation;
    private int radius;

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * getters and setters
     */
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

    /**
     * parameterized constructor
     *
     * @param label for the barGroup instance
     * @param value for approximating the length of Bargroup instance
     * @param color hex color value for the fill of barGroup instance
     * @param fillRatio for determining the percentage of the bar to be filled
     */
    public BarModel(String label, String value, String color, Float fillRatio, int elevation, int radius) {
        this.label = label;
        this.value = value;
        this.color = color;
        this.fillRatio = fillRatio;
        this.elevation = elevation;
        this.radius = radius;
    }
}
