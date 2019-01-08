package com.tntp.assemblycarts.tileentity;

import com.tntp.assemblycarts.entity.EntityMinecartAssemblyWorker;
import com.tntp.assemblycarts.util.DirUtil;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

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
    }

    public boolean canDock(EntityMinecart cart) {
        if (worldObj == null)
            return false;
        for (int d : DirUtil.ALL_DIR) {
            int[] off = DirUtil.OFFSETS[d];
            TileEntity tile = worldObj.getTileEntity(xCoord + off[0], yCoord + off[1], zCoord + off[2]);
            if (tile instanceof ICartStation) {
                return ((ICartStation) tile).canDock(cart, d ^ 1);
            }
        }
        return false;
    }

    public EntityMinecart getDockedCart() {
        return dockedCart;
    }

    public void setDockedCart(EntityMinecart dockedCart) {
        this.dockedCart = dockedCart;
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
