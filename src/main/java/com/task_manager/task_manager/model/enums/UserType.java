package com.task_manager.task_manager.model.enums;

public enum UserType {
    USER("Standart Kullanıcı"),
    ADMIN("Yönetici");

    private final String description;

    UserType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}