package com.example.mqtt;

import android.content.Context;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;

import javax.net.ssl.SSLContext;

public interface IMQTTProxy<T extends IMQTTProxy> {

    T init(Context context, MQTTBean mqttBean);

    void connect(IMqttActionListener actionListener);

    void disconnect();

    void release();

    boolean isConnected();

    void subscribeMsg(String topic, int qos);

    void subscribeMsg(String[] topic, int[] qos);

    void publish(String topic, String msg, boolean isRetained, int qos);

    void setMessageHandlerCallBack(MessageHandlerCallBack callBack);

    SSLContext sslContextFromStream(String assetName);
}
