package ru.asvistunov.imagemem;

public enum TextVerticalPosition {
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM("bottom");

    private final String position;

    TextVerticalPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
