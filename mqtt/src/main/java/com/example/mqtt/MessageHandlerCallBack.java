package com.example.mqtt;

public interface MessageHandlerCallBack {
    void messageSuccess(String topicName, String topicMsg);
}
