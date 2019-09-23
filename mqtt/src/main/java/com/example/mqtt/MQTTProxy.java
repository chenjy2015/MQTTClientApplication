package com.example.mqtt;

import android.content.Context;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;

import javax.net.ssl.SSLContext;

public class MQTTProxy implements IMQTTProxy<MQTTProxy> {

    private MQTTManager managers;

    private MQTTProxy() {
        if (managers == null) {
            managers = MQTTManager.getInstance();
        }
    }

    public static MQTTProxy getInstance() {
        return SingleTon.proxy;
    }

    private static class SingleTon {
        private static MQTTProxy proxy = new MQTTProxy();
    }

    @Override
    public MQTTProxy init(Context context, MQTTBean mqttBean) {
        managers.init(context, mqttBean);
        return this;
    }

    @Override
    public void connect(IMqttActionListener actionListener) {
        managers.connect(actionListener);
    }

    @Override
    public void disconnect() {
        managers.disconnect();
    }

    @Override
    public void release() {
        managers.release();
    }

    @Override
    public boolean isConnected() {
        return managers.isConnected();
    }

    @Override
    public void subscribeMsg(String topic, int qos) {
        managers.subscribeMsg(topic, qos);
    }

    @Override
    public void subscribeMsg(String[] topic, int[] qos) {
        managers.subscribeMsg(topic, qos);
    }

    @Override
    public void publish(String topic, String msg, boolean isRetained, int qos) {
        managers.publish(topic, msg, isRetained, qos);
    }

    @Override
    public void setMessageHandlerCallBack(MessageHandlerCallBack callBack) {
        managers.setMessageHandlerCallBack(callBack);
    }

    @Override
    public SSLContext sslContextFromStream(String assetName) {
        return null;
    }
}
