/*
 * Copyright (c) 2020-2022 AVI-SPL Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.core;

import com.avispl.symphony.api.dal.dto.control.ConnectionState;
import com.avispl.symphony.api.dal.error.CommandFailureException;
import com.avispl.symphony.dal.BaseDevice;
import com.avispl.symphony.dal.communicator.Communicator;
import com.avispl.symphony.dal.communicator.ConnectionStatus;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Base class for TCP socket-based clients. <br>
 *
 * TCP Socket communicator
 * @author Jonathan.Gillot
 *
 * */
public class SocketCommunicator extends BaseDevice implements Communicator {
    /** Socket instance used for communication */
    private Socket socket;
    /** Port number definition */
    private int port;
    /** List of error responses expected */
    private List<String> commandErrorList;
    /** List of success responses expected */
    private List<String> commandSuccessList;
    /** Mutual exclusion lock */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    /** Device connection status */
    private final ConnectionStatus status = new ConnectionStatus();
    /** Socket timeout value */
    private final int socketTimeout = 30000;
    /** Device API login */
    protected String login;
    /** Device API password */
    protected String password;
    /** Array of hex values used for converting values */
    protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Empty constructor
     */
    public SocketCommunicator() {
    }

    /**
     * This method returns the device TCP/IP port
     * @return int This returns the current TCP/IP port.
     */
    public int getPort() {
        return this.port;
    }

    /**
     * This method is used set the device TCP/IP port
     * @param port This is the TCP/IP port to set
     */
    public void setPort(int port) {
        if (this.isInitialized()) {
            throw new IllegalStateException("Cannot change properties after init() was called");
        } else {
            this.port = port;
        }
    }

    /**
     * Retrieve device login value
     * @return String login
     */
    public String getLogin() {
        return this.login;
    }

    /***
     * Set login value
     * @param login String value
     */
    public void setLogin(String login) {
        if (this.isInitialized()) {
            throw new IllegalStateException("Cannot change properties after init() was called");
        } else {
            this.login = login;
        }
    }

    /**
     * Retrieve device password value
     * @return String password value
     */
    public String getPassword() {
        return this.password;
    }

    /***
     * Set device password value
     * @param password String value
     * @throws IllegalStateException when trying to set the password after init() method has been called
     */
    public void setPassword(String password) {
        if (this.isInitialized()) {
            throw new IllegalStateException("Cannot change properties after init() was called");
        } else {
            this.password = password;
        }
    }

    /***
     * Retrieve list of error command responses
     * @return {@link List} of {@link String} error responses
     */
    public List<String> getCommandErrorList() {
        return this.commandErrorList;
    }

    /***
     * Set list of error command responses
     * @param commandErrorList list of String response values
     * @throws IllegalStateException when trying to set the error responses list after init() method has been called
     */
    protected void setCommandErrorList(List<String> commandErrorList) {
        if (this.isInitialized()) {
            throw new IllegalStateException("Cannot change properties after init() was called");
        } else {
            this.commandErrorList = commandErrorList;
        }
    }

    /***
     * Retrieve list of successful command responses
     * @return {@link List} of {@link String} error responses
     */
    public List<String> getCommandSuccessList() {
        return this.commandSuccessList;
    }

    /***
     * Set list of successful command responses
     * @param commandSuccessList list of String response values
     * @throws IllegalStateException when trying to set the successful responses list after init() method has been called
     */
    protected void setCommandSuccessList(List<String> commandSuccessList) {
        if (this.isInitialized()) {
            throw new IllegalStateException("Cannot change properties after init() was called");
        } else {
            this.commandSuccessList = commandSuccessList;
        }
    }

    /**
     * This method is used to create a connection
     * @throws IllegalStateException when is alled before init() method has been called
     * @throws InterruptedException if the communication was interrupted
     */
    @Override
    public void connect() throws Exception {
        if (!this.isInitialized()) {
            throw new IllegalStateException("ShellCommunicator cannot be used before init() is called");
        } else {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Connecting to: " + this.host + " port: " + this.port);
            }

            Lock writeLock = this.lock.writeLock();
            writeLock.lock();

            try {
                if (!this.isChannelConnected()) {
                    this.createChannel();
                    this.status.setLastTimestamp(System.currentTimeMillis());
                    this.status.setConnectionState(ConnectionState.Connected);
                    this.status.setLastError(null);
                }
            } catch (Exception var6) {
                if (var6 instanceof InterruptedException) {
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug("Interrupted while connecting to: " + this.host + " port: " + this.port);
                    }
                } else if (this.logger.isErrorEnabled()) {
                    this.logger.error("Error connecting to: " + this.host + " port: " + this.port, var6);
                }

                this.status.setLastError(var6);
                this.status.setConnectionState(ConnectionState.Failed);
                this.destroyChannel();
                throw var6;
            } finally {
                writeLock.unlock();
            }
        }
    }

    /**
     * This method is used to disconnect from the device
     */
    @Override
    public void disconnect() throws Exception {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Disconnecting from: " + this.host + " port: " + this.port);
        }

        Lock writeLock = this.lock.writeLock();
        writeLock.lock();

        try {
            this.destroyChannel();
            this.status.setConnectionState(ConnectionState.Disconnected);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ConnectionStatus getConnectionStatus() {
        Lock readLock = this.lock.readLock();
        readLock.lock();

        ConnectionStatus var2;
        try {
            var2 = this.status.copyOf();
        } finally {
            readLock.unlock();
        }

        return var2;
    }

    /**
     * This method is used to create a channel actually create a socket
     * @throws UnknownHostException if not able to connect to target host due to it's unavailability
     * @throws Exception if any errors occur
     */
    private void createChannel() throws Exception {
        if (this.socket == null || this.socket.isClosed() || !this.socket.isConnected()) {
            this.socket = new Socket(this.host, this.port);
            this.socket.setSoTimeout(socketTimeout);
        }
    }

    /**
     * Destroy TCP Socket communication channel
     */
    public void destroyChannel() {
        if (null != this.socket) {
            try {
                if (this.socket.isConnected()) {
                    this.socket.close();
                }
            } catch (Exception var2) {
                if (this.logger.isWarnEnabled()) {
                    this.logger.warn("error seen on destroyChannel", var2);
                }
            }

            this.socket = null;
        }

    }

    /**
     * Check whether Socket channel is connected to a target host
     */
    private boolean isChannelConnected() {
        Socket client = this.socket;
        return null != client && client.isConnected();
    }

    /**
     * This method is used to send a command to a device
     * @param data This is the data to be sent
     * @return byte[] This returns the reply received from the device.
     * @throws IllegalStateException if called before the init() call
     * @throws IllegalArgumentException if data is null
     * @throws Exception if general error during sending data occures, not related to the state of a communication
     *                   during the call or data payload
     */
    protected byte[] send(byte[] data) throws Exception {
        if (!this.isInitialized()) {
            throw new IllegalStateException("ShellCommunicator cannot be used before init() is called");
        } else if (null == data) {
            throw new IllegalArgumentException("Send data is null");
        } else {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Sending command: " + data + " to: " + this.host + " port: " + this.port);
            }

            Lock writeLock = this.lock.writeLock();
            writeLock.lock();

            byte[] var3;
            try {
                var3 = this.send(data, true);
            } finally {
                writeLock.unlock();
            }
            return var3;
        }
    }

    /**
     * This method is used to generate a string from a byte array
     *
     * @param bytes This is the byte array to convert to a String
     * @return String This returns the generated String.
     * @throws IOException if there are issues occured with decoding byte[] to string
     */
    public static String getHexByteString(byte[] bytes) throws IOException {
        return getHexByteString((CharSequence)null, ",", (CharSequence)null, bytes);
    }

    /**
     * This method is used to generate a string from a byte array
     *
     * @param prefix value that must precede the string
     * @param separator string separator
     * @param suffix value that must complete the string
     * @param bytes This is the byte array to convert to a String
     * @return String This returns the generated String.
     * @throws IOException if there are issues occured with decoding byte[] to string
     */
    public static String getHexByteString(CharSequence prefix, CharSequence separator, CharSequence suffix, byte[] bytes) throws IOException {
        byte[] data = bytes;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < data.length; ++i) {
            if (i > 0) {
                sb.append(separator);
            }

            int v = data[i] & 255;
            if (prefix != null) {
                sb.append(prefix);
            }

            sb.append(hexArray[v >> 4]);
            sb.append(hexArray[v & 15]);
            if (suffix != null) {
                sb.append(suffix);
            }
        }

        return sb.toString();
    }

    private byte[] send(byte[] data, boolean retryOnError) throws Exception {
        try {
            if (!this.isChannelConnected()) {
                this.createChannel();
                this.status.setLastTimestamp(System.currentTimeMillis());
                this.status.setConnectionState(ConnectionState.Connected);
                this.status.setLastError(null);
            }

            if(this.logger.isDebugEnabled()) {
                this.logger.debug("Sending: " + getHexByteString(data) + " to: " + this.host + " port: " + this.port);
            }

            byte response[] = this.internalSend(data);

            if(this.logger.isDebugEnabled()) {
                this.logger.debug("Received response: " + getHexByteString(response) + " from: " + this.host + " port: " + this.port);
            }

            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Received response: " + response + " from: " + this.host + " port: " + this.port);
            }

            this.status.setLastTimestamp(System.currentTimeMillis());
            return response;
        } catch (CommandFailureException var4) {
            if (this.logger.isErrorEnabled()) {
                this.logger.error("Command failed " + data + " to: " + this.host + " port: " + this.port + " connection state: " + this.status.getConnectionState(), var4);
            }

            this.status.setLastTimestamp(System.currentTimeMillis());
            throw var4;
        }catch(SocketTimeoutException ex){
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Time out while sending command: " + data + " to: " + this.host + " port: " + this.port + " connection state: " + this.status.getConnectionState() + " error: ", ex);
            }

            //throw ex;
            this.status.setLastError(ex);
            this.status.setConnectionState(ConnectionState.Unknown);
            this.destroyChannel();
            if (retryOnError) {
                return this.send(data, false);
            } else {
                throw ex;
            }
        } catch(Exception var5) {
            if (var5 instanceof InterruptedException) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Interrupted while sending command: " + data + " to: " + this.host + " port: " + this.port + " connection state: " + this.status.getConnectionState() + " error: ", var5);
                }
            } else if (this.logger.isErrorEnabled()) {
                this.logger.error("Error sending command: " + data + " to: " + this.host + " port: " + this.port + " connection state: " + this.status.getConnectionState() + " error: ", var5);
            }

            this.status.setLastError(var5);
            this.status.setConnectionState(ConnectionState.Failed);
            this.destroyChannel();
            if (retryOnError) {
                return this.send(data, false);
            } else {
                throw var5;
            }
        }
    }

    private byte[] internalSend(byte[] outputData) throws Exception {
        this.write(outputData);
        return this.read(outputData, this.socket.getInputStream());
    }

    private void write(byte[] outputData) throws Exception {
        if(this.socket == null) {
            throw new IllegalStateException("Socket connection was not established. Please check target host availability and credentials.");
        }
        OutputStream os = this.socket.getOutputStream();
        os.write(outputData);
        os.flush();
    }

    private byte[] read(byte[] command, InputStream in) throws Exception {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("DEBUG - Socket Communicator reading after command text \"" + getHexByteString(command) + "\" was sent to host " + this.host);
        }

        BufferedInputStream reader = new BufferedInputStream(in);

        List<Byte> bytes = new ArrayList<>();

        do{
            bytes.add((byte)reader.read());
        }while (reader.available()>0);

        byte byteArray[] = new byte[bytes.size()];

        for(int i = 0;i<bytes.size();i++)
        {
            byteArray[i] = bytes.get(i);
        }

        return byteArray;
    }

    /**
     * Method checks for {@link #commandErrorList} and {@link #commandSuccessList} and provides
     * response, whether the reading was consider finished or not.
     *
     * @param command command that is being executed
     * @param response command response
     * @throws CommandFailureException if operation fails
     * @return true or false, indicating whether the read operation was finished or not
     * */
    protected boolean doneReading(String command, String response) throws CommandFailureException {
        if(logger.isDebugEnabled()){
            logger.debug("Done reading with a response: " + response);
        }
        Iterator var3 = this.commandErrorList.iterator();

        String string;
        do {
            if (!var3.hasNext()) {
                var3 = this.commandSuccessList.iterator();

                do {
                    if (!var3.hasNext()) {
                        return false;
                    }

                    string = (String)var3.next();
                } while(!response.endsWith(string));

                if (this.logger.isTraceEnabled()) {
                    this.logger.trace("Done reading, found success string: " + string + " from: " + this.host + " port: " + this.port);
                }
                return true;
            }

            string = (String)var3.next();
        } while(!response.endsWith(string));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Done reading, found error string: " + string + " from: " + this.host + " port: " + this.port);
        }

        throw new CommandFailureException(this.host, command, response);
    }

    protected void internalDestroy() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Destroying communication channel to: " + this.host + " port: " + this.port);
        }

        this.destroyChannel();
        this.status.setConnectionState(ConnectionState.Disconnected);
        super.internalDestroy();
    }

    protected void internalInit() throws Exception {
        super.internalInit();

        if (null != this.socket) {
            this.destroyChannel();
        }

        if (this.port <= 0) {
            // Should not throw exception here, since port number will not be provided, if no right management protocol
            // is set. However, if device is using basic monitoring, it's unlikely that it has one set in the first place
            logger.error("Invalid port property: " + this.port + " (must be positive number)");
        }
        if (null != this.commandSuccessList && !this.commandSuccessList.isEmpty()) {
            if (null == this.commandErrorList || this.commandErrorList.isEmpty()) {
                throw new IllegalStateException("Invalid commandErrorList property (must be non-empty list)");
            }
        } else {
            throw new IllegalStateException("Invalid commandSuccessList property (must be non-empty list)");
        }
    }
}
