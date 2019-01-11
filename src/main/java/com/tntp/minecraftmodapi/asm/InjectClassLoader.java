package com.tntp.minecraftmodapi.asm;

public class InjectClassLoader extends ClassLoader {
    private ClassLoader parent;

    public InjectClassLoader(ClassLoader mcClassLoader) {
        parent = mcClassLoader;
    }

    @Override
    public Class<?> findClass(final String name) throws ClassNotFoundException {
        return null;

    }
}
