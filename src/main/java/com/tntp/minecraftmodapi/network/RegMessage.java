package com.tntp.minecraftmodapi.network;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.tntp.assemblycarts.core.AssemblyCartsMod;
import com.tntp.assemblycarts.network.SMessage;
import com.tntp.minecraftmodapi.APIiTNTPiston;
import com.tntp.minecraftmodapi.SuperRegister;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class RegMessage extends SuperRegister implements IMessageRegisterFactory {
    private SimpleNetworkWrapper wrapper;
    private int id;

    public RegMessage() {
        wrapper = Ntwk.newChannel(modid);
        id = 0;
    }

    @Override
    public <REQ extends SMessage<REQ>> IMessageRegister of(Class<REQ> clazz) {
        return new Reg<>(clazz);
    }

    private <REQ extends SMessage<REQ>> void registerMessage(Class<REQ> c, Side side) throws Exception {
        try {
            REQ req = c.newInstance();
            wrapper.registerMessage(req, c, id++, side);
        } catch (Exception e) {
            APIiTNTPiston.log.error("[Ntwk Registry] Message Must Have Default Constructor: " + c.getSimpleName());
            throw e;
        }
    }

    private class Reg<REQ extends SMessage<REQ>> implements IMessageRegister {
        Class<REQ> clazz;
        Side s = null;

        Reg(Class<REQ> c) {
            clazz = c;
        }

        @Override
        public void register() {
            if (s == null) {
                APIiTNTPiston.log.error("[Ntwk Registry] Side Not Specified: " + clazz.getCanonicalName());
                throw new RuntimeException("Message Registration Failed!");
            }
            try {
                registerMessage(clazz, s);
            } catch (Exception e) {
                throw new RuntimeException("Message Registration Failed!");
            }
        }

        @Override
        public void side(Side side) {
            s = side;
        }

    }

    @Override
    public void injectTo(Class<?> channelHolder) {
        APIiTNTPiston.log.info("[Ntwk Injector] Injecting Network Wrapper to " + channelHolder.getCanonicalName());
        try {
            Field[] fields = channelHolder.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                Annotation[] annos = f.getDeclaredAnnotations();
                for (Annotation a : annos) {
                    if (a.annotationType() == ChannelHolder.class) {
                        APIiTNTPiston.log.info("[Ntwk Injector] >> " + f.toString());
                        // Remove final modifier
                        Field modifiers = Field.class.getDeclaredField("modifiers");
                        modifiers.setAccessible(true);
                        modifiers.setInt(f, f.getModifiers() & (~Modifier.FINAL));
                        f.set(null, wrapper);
                        // Add final modifier
                        modifiers.setInt(f, f.getModifiers() & Modifier.FINAL);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            APIiTNTPiston.log.error("[Ntwk Injector] Exception: " + e.getClass().getCanonicalName());
            APIiTNTPiston.log.error("[Ntwk Injector] Network Wrapper not injected!");
        } finally {
            APIiTNTPiston.log.info("[Ntwk Injector] Injected Network Wrapper");
        }
    }

}
