package by.zadziarnouski.workoutlog.model;

public enum MuscleGroup {
    CHEST("Chest"),
    BACK("Back"),
    ARMS("Arms"),
    SHOULDERS("Shoulders"),
    LEGS("Legs"),
    ABS("Abs"),
    CALVES("Calves");

    private final String displayValue;

    MuscleGroup(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
