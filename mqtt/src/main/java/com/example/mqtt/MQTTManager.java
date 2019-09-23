package com.example.mqtt;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class MQTTManager {

    public static final String TAG = "MQTTManager";
    private MQTTManager mTTManager;
    private PushCallback pushCallback;

    private MqttAndroidClient mClient;
    private MqttConnectOptions mOptions;
    private IMqttActionListener mActionListener;

    private MQTTManager() {
        this.pushCallback = new PushCallback(this);
    }

    private static class SingleTon {
        private static MQTTManager instance = new MQTTManager();
    }

    public static MQTTManager getInstance() {
        return SingleTon.instance;
    }

    public void init(Context context, MQTTBean mqttBean) {
        /**
         * host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，
         * MemoryPersistence设置clientid的保存形式，默认为以内存保存
         */
        mClient = new MqttAndroidClient(context, MQTTContacts.SERVICE_HOST, mqttBean.getClientId());
        /**
         *  MQTT的连接设置
         */
        mOptions = new MqttConnectOptions();

        /**
         * 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
         * 这里设置为true表示每次连接到服务器都以新的身份连接
         */
        mOptions.setCleanSession(true);
        /**
         * 设置用户名
         */
        mOptions.setUserName(mqttBean.getUserName());
        /**
         * 设置密码
         */
        mOptions.setPassword(mqttBean.getPassword().toCharArray());
        /**
         * 设置超时时间 单位秒
         */
        mOptions.setConnectionTimeout(15);
        /**
         * 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，
         * 但这个方法并没有重连的机制
         */
        mOptions.setKeepAliveInterval(30);
        /**
         * 设置回调
         */
//            MqttTopic topic = mClient.getTopic(mTopic);
        /**
         * setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
         */
        mOptions.setWill(mqttBean.getTopic(), "dissect".getBytes(), MQTTContacts.QoS.QoSForOnce.type, true);
        /**
         * SSL 证书设置
         */
//            SSLSocketFactory sslSocketFactory = null;
//            try {
//                sslSocketFactory = sslContextFromStream(mContext.getAssets().open("server.pem")).getSocketFactory();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            mOptions.setSocketFactory(sslSocketFactory);
        /**
         * 设置消息回调
         */
        mClient.setCallback(pushCallback);
//
    }

    /**
     * 开始连接服务器
     */
    public void connect(IMqttActionListener actionListener) {
        mActionListener = actionListener;
        try {
            mClient.connect(mOptions, null, actionListener);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "ClientId=" + mClient.getClientId());
    }

    /**
     * 重连服务器
     */
    public void reConnect() {
        if (mClient != null) {
            mClient.close();
            connect(mActionListener);
        }
    }


    /**
     * 订阅消息
     *
     * @param topic 消息主题
     * @param qos
     */
    public void subscribeMsg(String topic, int qos) {
        try {
            mClient.subscribe(new String[]{topic}, new int[]{qos});
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅消息
     *
     * @param topic 消息主题
     * @param qos
     */
    public void subscribeMsg(String[] topic, int[] qos) {
        try {
            mClient.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布消息
     *
     * @param topic      发布消息主题
     * @param msg        消息体
     * @param isRetained 是否为保留消息
     * @param qos
     */
    public void publish(String topic, String msg, boolean isRetained, int qos) {
        try {
            if (mClient != null) {
                MqttMessage message = new MqttMessage();
                message.setQos(qos);
                message.setRetained(isRetained);
                message.setPayload(msg.getBytes());
                mClient.publish(topic, message);
                Log.d(TAG, "topic=" + topic + "--msg=" + msg + "--isRetained" + isRetained);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置接收消息的回调方法
     *
     * @param callBack
     */
    public void setMessageHandlerCallBack(MessageHandlerCallBack callBack) {
        this.pushCallback.setCallBack(callBack);
    }


    /**
     * 断开链接
     */
    public void disconnect() {
        if (mClient != null && mClient.isConnected()) {
            try {
                mClient.disconnect();
                mTTManager = null;
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mTTManager != null) {
            mTTManager = null;
        }
        if (mActionListener != null) {
            mActionListener = null;
        }
    }

    /**
     * 判断服务是否连接
     *
     * @return
     */
    public boolean isConnected() {
        if (mClient != null) {
            return mClient.isConnected();
        }
        return false;
    }

    public SSLContext sslContextFromStream(InputStream inputStream) throws Exception {

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(inputStream);

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", certificate);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        return sslContext;
    }

}
