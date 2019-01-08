package com.tntp.assemblycarts.tileentity;

import com.tntp.assemblycarts.entity.EntityMinecartAssembly;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;

public class TileDockingTrack extends STile {
    private boolean reversed;
    private boolean occupied;
    private EntityMinecart dockedCart;

    public TileDockingTrack() {
    }

    @Override
    public void updateEntity() {
        if (dockedCart != null && dockedCart.isDead) {
            dockedCart = null;
        }
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
        if (worldObj != null && !worldObj.isRemote)
            this.worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), EVENT_TRACK_REVERSE, reversed ? 1 : 0);
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setBoolean("reversed", reversed);
        tag.setBoolean("occupied", occupied);
        // tag.setBoolean("hasCart", hasCartInThisTick);
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        setReversed(tag.getBoolean("reversed"));
        occupied = tag.getBoolean("occupied");
        // hasCartInThisTick = tag.getBoolean("hasCart");
    }

    @SuppressWarnings("unchecked")
    public <T extends EntityMinecart> T getDockedCart(Class<T> cartType) {
        if (dockedCart == null)
            return null;
        if (cartType.isAssignableFrom(dockedCart.getClass()))
            return (T) dockedCart;
        return null;
    }

    public EntityMinecart getDockedCart() {
        return dockedCart;
    }

    public void setDockedCart(EntityMinecartAssembly dockedCart) {
        this.dockedCart = dockedCart;
    }

    public boolean canCartDock(EntityMinecart cart) {
        return true;
    }

    @Override
    public boolean receiveClientEvent(int event, int param) {
        if (super.receiveClientEvent(event, param))
            return true;
        if (event == EVENT_TRACK_REVERSE) {
            reversed = param == 1;
            worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
            return true;
        }
        return false;
    }
}
