package com.gdx.spacepie;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import gnu.io.*;


public class Bluetooth {
	private CommPortIdentifier portIdentifier = null;
	private SerialPort serialPort = null;
	private String dev = "/dev/rfcomm0";
    private CommPort commPort = null;
    private int TIMEOUT = 2000;
    public InputStream input = null;
	public OutputStream output = null;
	final static int SPACE_ASCII = 32;
    final static int DASH_ASCII = 45;
    final static int NEW_LINE_ASCII = 10;
    public Bluetooth(){
    	
    
        System.out.println("Monitoring serial stream on " + dev);
        try
        {
	        portIdentifier = CommPortIdentifier.getPortIdentifier(dev);
	        
	        if (portIdentifier.isCurrentlyOwned()) 
	        {
	            System.out.println( "Error: Port is currently in use" );
	        }
	        else
	        {
	            commPort = portIdentifier.open(dev,TIMEOUT);
	           
	            if (commPort instanceof SerialPort) 
	            {
	                serialPort = (SerialPort) commPort;
	                serialPort.setInputBufferSize(1);
	                serialPort.setOutputBufferSize(1);
	                
	                input = serialPort.getInputStream();
	                output = serialPort.getOutputStream();
	            }
	        }
        }
        catch (PortInUseException e)
        {
        	System.out.println(" is in use. (" + e.toString() + ")");


        }
        catch (Exception e)
        {
        	System.out.println("Failed to open " + dev + "(" + e.toString() + ")");
        }
	}
	
	

}
