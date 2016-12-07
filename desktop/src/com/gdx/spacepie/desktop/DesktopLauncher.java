package com.gdx.spacepie.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdx.spacepie.SpacePie;
import com.gdx.spacepie.GameScreen;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import gnu.io.*;

public class DesktopLauncher {

	public static void main (String[] arg) throws Exception {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = SpacePie.screenWidth;
		config.height = SpacePie.screenHeight;
		new LwjglApplication(new SpacePie(), config);
        String dev = "/dev/rfcomm0";
 
        System.out.println("Monitoring serial stream on " + dev);
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(dev);
        
        if (portIdentifier.isCurrentlyOwned()) 
        {
            System.out.println( "Error: Port is currently in use" );
        }
        else
        {
            int timeout = 2000;
            CommPort commPort = portIdentifier.open(dev,timeout);
            System.out.println("connect complete");
            if (commPort instanceof SerialPort) 
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
                serialPort.setInputBufferSize(1);
//                serialPort.setOutputBufferSize(1);
                
//                serialPort.getOutputStream().write(1);;
                GameScreen.serialPort = serialPort;
                InputStream in = GameScreen.serialPort.getInputStream();
                
            	GameScreen.reader = new BufferedReader(new InputStreamReader(in));
            	
          
                System.out.println("init read");
//                OutputStream out = serialPort.
//                SpacePie.in = in;
//                System.out.println("444");
            }
        }
	}
}
