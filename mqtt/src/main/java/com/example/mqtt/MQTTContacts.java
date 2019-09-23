package com.example.mqtt;

public class MQTTContacts {

    public static final String SERVICE_HOST = "tcp://192.168.10.48:61613";
    public static final String TOPIC = "toclient/124";
    public static final String TOPIC125 = "toclient/125";
    public static String CLIENT_ID = "client125";
    public static String USERNAME = "admin";
    public static String PASSWORD = "password";

    public enum QoS {
        /**
         * 最多一次
         * 消息发布完全依赖底层 TCP/IP 网络。分发的消息可能丢失或重复。
         * 例如，这个等级可用于环境传感器数据，单次的数据丢失没关系，因为不久后还会有第二次发送。
         */
        QoSAtMostOnce(0),

        /**
         * 至少一次
         * 确保消息可以到达，但消息可能会重复
         */
        QoSAtLeastOnce(1),

        /**
         * 只有一次
         * 确保消息只到达一次。例如，这个等级可用在一个计费系统中，这里如果消息重复或丢失会导致不正确的收费
         * 适用于收银场景
         */
        QoSForOnce(2);


        public int type;

        QoS(int type) {
            this.type = type;
        }
    }
}
