package com.tntp.assemblycarts.tileentity;

import java.util.List;

import com.tntp.assemblycarts.entity.EntityMinecartAssemblyWorker;
import com.tntp.minecraftmodapi.tileentity.TileEntityAPIiTNTPiston;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public class TileDetectorAssembly extends TileEntityAPIiTNTPiston {
    private static final float SENSITIVITY = 0.1f;
    private static final int DELAY = 10;
    private static final int UPDATE_INTERVAL = 5;
    private int powerTime = 0;
    private int updateTime = 0;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj != null && !worldObj.isRemote) {
            if (powerTime > 0) {
                powerTime--;
            } else if (updateTime <= 0) {
                updateCarts();
                updateTime = UPDATE_INTERVAL;
            } else {
                updateTime--;
            }
        }
    }

    private void updateCarts() {
        List<EntityMinecartAssemblyWorker> list = worldObj.getEntitiesWithinAABB(EntityMinecartAssemblyWorker.class,
                AxisAlignedBB.getBoundingBox(xCoord - SENSITIVITY, yCoord - SENSITIVITY, zCoord - SENSITIVITY, xCoord + 1 + SENSITIVITY, yCoord + 1 + SENSITIVITY, zCoord + 1 + SENSITIVITY));
        int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        boolean powered = (meta & 8) == 8;
        boolean newPowered = !list.isEmpty();
        if (newPowered != powered) {
            if (newPowered) {
                meta = meta | 8;
            } else {
                meta = meta & 7;
            }
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 2);
        }
    }
}
