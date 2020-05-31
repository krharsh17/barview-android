package `in`.krharsh17.barview

/**
 * This class is represents a BarView entity and contains all the attributes
 * required to instantiate a BarGroup
 */
class BarModel
/**
 * parameterized constructor
 *
 * @param label for the barGroup instance
 * @param value for approximating the length of Bargroup instance
 * @param color hex color value for the fill of barGroup instance
 * @param fillRatio for determining the percentage of the bar to be filled
 */(
        /**
         * getters and setters
         */
        var label: String, var value: String, var color: String, var fillRatio: Float, var elevation: Int, var radius: Int)