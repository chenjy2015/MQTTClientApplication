package com.example.mqttclientapplication;

import android.content.Context;
import android.util.Log;

import com.example.baselibrary.BaseViewModel;
import com.example.mqtt.MQTTBean;
import com.example.mqtt.MQTTContacts;
import com.example.mqtt.MQTTManager;
import com.example.mqtt.MQTTProxy;
import com.example.mqtt.MessageHandlerCallBack;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;

public class LoginViewModel implements BaseViewModel {

    public void login() {
        Log.d("login", "login !!!");
    }

    public void init(Context context, MQTTBean mqttBean) {
        MQTTProxy.getInstance().init(context, mqttBean);
    }

    public void connect(IMqttActionListener actionListener) {
        MQTTProxy.getInstance().connect(actionListener);
    }

    public void send(String topic, String msg) {
        MQTTProxy.getInstance().publish(topic, msg, true, MQTTContacts.QoS.QoSForOnce.type);
    }

    public void register(String topic, int qos) {
        MQTTProxy.getInstance().subscribeMsg(topic, qos);
    }

    public void register(String[] topic, int[] qos) {
        MQTTProxy.getInstance().subscribeMsg(topic, qos);
    }

    public void setMessageHandlerCallBack(MessageHandlerCallBack callBack){
        MQTTProxy.getInstance().setMessageHandlerCallBack(callBack);
    }

    @Override
    public void destroy() {
        MQTTManager.getInstance().release();
    }
}
