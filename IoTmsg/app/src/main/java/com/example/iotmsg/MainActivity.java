package com.example.iotmsg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class MainActivity extends AppCompatActivity {
    static String MQTTHOST = "tcp://m24.cloudmqtt.com:13398";
    static String USERNAME = "hxdzapjd";
    static String PASSWORD = "bCtuyFO3kKn5";
    //String topicStr = "OBJ";
    MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this,"conectado",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this,"não conectado",Toast.LENGTH_LONG).show();
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void ligar1(View v){
        String topic = "LUZ";
        String mensage = "on";

        try {
            client.publish(topic, mensage.getBytes(), 0, false);
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void desligar1(View v){
        String topic = "LUZ";
        String mensage = "off";

        try {
            client.publish(topic, mensage.getBytes(), 0, false);
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void motordir(View v){
        String topic = "MOTOR";
        String mensage = "dir";

        try {
            client.publish(topic, mensage.getBytes(), 0, false);
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void motoresq(View v){
        String topic = "MOTOR";
        String mensage = "esq";

        try {
            client.publish(topic, mensage.getBytes(), 0, false);
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
