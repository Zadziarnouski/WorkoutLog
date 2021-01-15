package by.zadziarnouski.workoutlog.model;

public enum Role {
    ROLE_USER("User"),
    ROLE_ADMIN("Admin");

    private final String displayValue;

    Role(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

}
