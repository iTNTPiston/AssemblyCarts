package com.tntp.minecraftmodapi.network;

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
    IMessageRegisterFactory injectTo(Class<?> channelHolder);

    public @interface ChannelHolder {

    }

}
