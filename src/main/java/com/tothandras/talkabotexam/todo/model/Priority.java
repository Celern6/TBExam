package com.tothandras.talkabotexam.todo.model;

public enum Priority {
    HIGH(2),
    MEDIUM(1),
    LOW(0);

    private final int intValue;

    Priority(int i) {
        this.intValue = i;
    }

    public static Priority valueOfLabel(int value) {
        for (Priority p : values()) {
            if (p.intValue == (value)) {
                return p;
            }
        }
        return null;
    }

    public int getValue() {
        return this.intValue;
    }
}
