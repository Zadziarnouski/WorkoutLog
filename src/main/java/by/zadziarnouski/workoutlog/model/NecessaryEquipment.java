package by.zadziarnouski.workoutlog.model;

public enum NecessaryEquipment {
    NOEQUIPMENT("No equipment"),
    ELASTICBAND("Elastic band"),
    HORIZONTALBAR("Horizontal bar"),
    KETTLEBELL("Kettlebel"),
    MAT("Mat");

    private final String displayValue;

    private NecessaryEquipment(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
