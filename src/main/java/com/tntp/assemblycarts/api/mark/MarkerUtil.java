package com.tntp.assemblycarts.api.mark;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;

public class MarkerUtil {
    private static final HashMap<String, Integer> markTypeToId = new HashMap<>();
    private static final ArrayList<IMarkItem> idToMarkType = new ArrayList<>();

    public static void register(Class<? extends IMarkItem> clazz) {
        markTypeToId.put(clazz.getCanonicalName(), idToMarkType.size());
        try {
            idToMarkType.add(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("AssemblyCarts cannot register a mark type!");
        }
    }

    public static void writeToNBT(NBTTagCompound tag, IMarkItem mark) {
        NBTTagCompound wrapped = new NBTTagCompound();
        mark.writeToNBT(wrapped);
        tag.setInteger("markType", markTypeToId.get(mark.getClass().getCanonicalName()));
        tag.setTag("wrapped", wrapped);
    }

    public static IMarkItem readFromNBT(NBTTagCompound tag) {
        NBTTagCompound wrapped = tag.getCompoundTag("wrapped");
        IMarkItem template = idToMarkType.get(tag.getInteger("markType"));
        return template.readFromNBT(wrapped);
    }
}
