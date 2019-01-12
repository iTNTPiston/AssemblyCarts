package com.tntp.minecraftmodapi.network;

import com.tntp.assemblycarts.network.SMessage;

public interface IMessageRegisterFactory {
    /**
     * Start the registration of a message
     * 
     * @param clazz
     * @return
     */
    <REQ extends SMessage<REQ>> IMessageRegister of(Class<REQ> clazz);

    /**
     * Inject the network channel to a static field in that class with ChannelHolder
     * annotation
     * 
     * @param channelHolder
     */
    void injectTo(Class<?> channelHolder);

    public @interface ChannelHolder {

    }

}
