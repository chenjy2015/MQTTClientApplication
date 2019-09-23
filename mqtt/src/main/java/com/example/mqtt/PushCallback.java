package com.example.mqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.example.mqtt.MQTTManager.TAG;

/**
 * 发布和订阅消息的回调
 */
public class PushCallback implements MqttCallback {
    int count = 0;

    private MQTTManager managers;
    private MessageHandlerCallBack callBack;


    public PushCallback(MQTTManager managers) {
        this.managers = managers;
    }

    public PushCallback setCallBack(MessageHandlerCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.e(TAG, "connectionLost: " + cause);
        if (count < 5) {
            count++;//5次重连
            Log.d(TAG, "断开连接，重新连接" + count + "次" + cause);
            managers.reConnect();
        }
    }

    /**
     * 发布消息的回调
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //publish后会执行到这里
        Log.d(TAG, "发布消息成功的回调" + token.isComplete());
    }

    /**
     * 接收消息的回调方法
     */
    @Override
    public void messageArrived(final String topicName, final MqttMessage message)
            throws Exception {
        //subscribe后得到的消息会执行到这里面
        Log.d(TAG, "接收消息==" + new String(message.getPayload()));
        if (callBack != null) {
            callBack.messageSuccess(topicName, new String(message.getPayload()));
        }
    }
}
