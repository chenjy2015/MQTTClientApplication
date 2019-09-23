package com.example.mqtt;

import androidx.annotation.NonNull;

public class MQTTBean {

    private String topic = "toclient/125";
    private String clientId = "client125";
    private String userName = "admin";
    private String password = "password";

    private MQTTBean(){}

    public MQTTBean(@NonNull String topic, @NonNull String clientId, @NonNull String userName, @NonNull String password) {
        this.topic = topic;
        this.clientId = clientId;
        this.userName = userName;
        this.password = password;
    }

    public String getTopic() {
        return topic;
    }

    public String getClientId() {
        return clientId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
