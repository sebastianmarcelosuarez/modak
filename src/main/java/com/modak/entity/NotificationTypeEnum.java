package com.modak.entity;

import java.util.Optional;

public enum NotificationTypeEnum {
    STATUS("status"),
    NEWS("news"),
    MARKETING("marketing"),
    UPDATE("update");

    public final String label;

    NotificationTypeEnum(String label) {
        this.label = label;
    }

    public static Optional<NotificationTypeEnum> getNotificationType(String type) {
        try{
            return Optional.of(NotificationTypeEnum.valueOf(type.toUpperCase()));
        } catch (Exception e) {
            System.out.println("Error on NotificationTypeEnum: " + e.getMessage());
            return Optional.empty();
        }
    }
}
