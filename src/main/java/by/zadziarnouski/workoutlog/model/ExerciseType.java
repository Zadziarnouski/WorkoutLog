package by.zadziarnouski.workoutlog.model;

public enum ExerciseType {
    BASIS("Basis"),
    ISOLATION("Isolation"),
    CARDIO("Cardio"),
    STATIC("Static");

    private final String displayValue;

    ExerciseType(String displayValue){
        this.displayValue = displayValue;
    }

    public String getDisplayValue(){
        return displayValue;
    }
}