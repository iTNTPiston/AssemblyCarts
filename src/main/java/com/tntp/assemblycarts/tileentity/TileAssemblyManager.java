package com.tntp.assemblycarts.tileentity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.tntp.assemblycarts.api.AssemblyProcess;
import com.tntp.assemblycarts.api.IRequester;
import com.tntp.assemblycarts.api.RequestManager;
import com.tntp.assemblycarts.init.ACItems;
import com.tntp.assemblycarts.item.ItemProcessBook;
import com.tntp.assemblycarts.util.ItemUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileAssemblyManager extends STileInventory implements IRequester {
    private RequestManager requestManager;
    private boolean formed;
    private boolean bypassInsertionCheck;
    private boolean isStructureCached;
    private List<IAssemblyStructure> cachedStructure;

    public TileAssemblyManager() {
        super(36);// 0-8 for process 9-35 for storage
        requestManager = new RequestManager(this, 9, 35);
        bypassInsertionCheck = false;
        cachedStructure = new ArrayList<IAssemblyStructure>();
    }

    public void updateEntity() {
        super.updateEntity();
        isStructureCached = false;
        if (worldObj != null && !worldObj.isRemote) {
            if (!formed) {
                setFormed(detectStructure());
            } else {
                supplyToPorts();
            }
        }
    }

    public boolean detectStructure() {
        if (this.isInvalid())
            return false;
        if (isStructureCached)
            return true;
        cachedStructure.clear();

        int minY = yCoord;
        int maxY = yCoord;
        int minX = xCoord;
        int maxX = xCoord;
        int minZ = zCoord;
        int maxZ = zCoord;

        while (worldObj.getTileEntity(xCoord, minY - 1, zCoord) instanceof IAssemblyStructure)
            minY--;
        while (worldObj.getTileEntity(xCoord, maxY + 1, zCoord) instanceof IAssemblyStructure)
            maxY++;
        if (maxY - minY > 4)
            return false;
        while (worldObj.getTileEntity(minX - 1, yCoord, zCoord) instanceof IAssemblyStructure)
            minX--;
        while (worldObj.getTileEntity(maxX + 1, yCoord, zCoord) instanceof IAssemblyStructure)
            maxX++;
        if (maxX - minX > 4)
            return false;
        while (worldObj.getTileEntity(xCoord, yCoord, minZ - 1) instanceof IAssemblyStructure)
            minZ--;
        while (worldObj.getTileEntity(xCoord, yCoord, maxZ + 1) instanceof IAssemblyStructure)
            maxZ++;
        if (maxZ - minZ > 4)
            return false;
        for (int xx = minX; xx <= maxX; xx++) {
            for (int yy = minY; yy <= maxY; yy++) {
                for (int zz = minZ; zz <= maxZ; zz++) {
                    TileEntity tile = worldObj.getTileEntity(xx, yy, zz);
                    if (tile == this)
                        continue;

                    if (!(tile instanceof IAssemblyStructure))
                        return false;
                    else {
                        ((IAssemblyStructure) tile).setManager(this);
                        cachedStructure.add((IAssemblyStructure) tile);
                    }
                }
            }
        }
        isStructureCached = true;
        return true;

    }

    public void setFormed(boolean f) {
        if (formed != f) {
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, f ? 8 : 0, 3);
            worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
            formed = f;
        }
    }

    public boolean isFormed() {
        return formed;
    }

    public AssemblyProcess getProcessBySlot(int slot) {
        if (slot < 0 || slot >= 9) {
            return null;
        }
        ItemStack book = getStackInSlot(slot);
        if (book != null && book.getItem() == ACItems.process_book) {
            if (ItemProcessBook.hasProcess(book)) {
                AssemblyProcess process = ItemProcessBook.getProcessFromStack(book);
                if (process.getMainOutput() != null)
                    return process;
            }
        }
        return null;
    }

    public void startProcess(int processBookSlotID, int multiplier) {
        AssemblyProcess process = getProcessBySlot(processBookSlotID);
        if (process != null) {
            initRequest(process, multiplier);
            markDirty();
        }
    }

    public void cancelProcess() {
        requestManager.cancelRequest();
        if (detectStructure()) {
            for (IAssemblyStructure t : cachedStructure) {
                if (t instanceof TileAssemblyRequester) {
                    ((TileAssemblyRequester) t).getRequestManager().cancelRequest();
                }
            }
        }
    }

    private boolean initRequest(AssemblyProcess p, int multiplier) {
        if (detectStructure()) {
            System.out.println("Init Request");
            // init request
            requestManager.initRequestWithProcess(p, multiplier);

            // Init requesters
            ItemStack target = requestManager.getCraftingTarget();
            LinkedList<ItemStack> needCopied = new LinkedList<ItemStack>();
            for (ItemStack s : requestManager.getNeed()) {
                needCopied.add(ItemStack.copyItemStack(s));
            }

            // Find all requesters
            ArrayList<TileAssemblyRequester> requesters = new ArrayList<TileAssemblyRequester>();
            for (IAssemblyStructure t : cachedStructure) {
                if (t instanceof TileAssemblyRequester) {
                    requesters.add(((TileAssemblyRequester) t));
                }
            }

            System.out.println("Find " + requesters.size() + " requesters");
            // Create a list for each requester
            ArrayList<ArrayList<ItemStack>> needList = new ArrayList<ArrayList<ItemStack>>(requesters.size());
            for (int i = 0; i < requesters.size(); i++) {
                needList.add(new ArrayList<ItemStack>());
            }

            // Distribute the request to all requesters
            ArrayList<Integer> selectedRequesters = new ArrayList<Integer>();
            while (!needCopied.isEmpty()) {
                selectedRequesters.clear();
                ItemStack toRequest = needCopied.removeFirst();
                // Search marked requesters first
                for (int i = 0; i < requesters.size(); i++) {
                    if (requesters.get(i).getMarkManager().isMarked(toRequest)) {
                        selectedRequesters.add(i);
                    }
                }
                // if there is no marked requesters, select all unmarked
                if (selectedRequesters.isEmpty()) {
                    for (int i = 0; i < requesters.size(); i++) {
                        if (!requesters.get(i).getMarkManager().hasMark()) {
                            selectedRequesters.add(i);
                        }
                    }
                }
                // If there is no requesters, cancel the request
                if (selectedRequesters.isEmpty()) {
                    requestManager.cancelRequest();
                    System.out.println("No requester available, cancel!");
                    return false;
                }
                int distributionAmount = toRequest.stackSize / selectedRequesters.size();
                int remainder = toRequest.stackSize % selectedRequesters.size();
                for (int i = 0; i < selectedRequesters.size(); i++) {
                    int r = selectedRequesters.get(i);
                    ArrayList<ItemStack> need = needList.get(r);
                    int amount = i < remainder ? distributionAmount + 1 : distributionAmount;
                    boolean added = false;
                    for (ItemStack s : need) {
                        if (ItemUtil.areItemAndTagEqual(s, toRequest)) {
                            s.stackSize += amount;
                            added = true;
                        }
                    }
                    if (!added) {
                        ItemStack add = ItemStack.copyItemStack(toRequest);
                        add.stackSize = amount;
                        need.add(add);
                    }
                }

            }
            System.out.println("Sending Request to Requesters");
            // Add the request to the requesters
            for (int i = 0; i < requesters.size(); i++) {
                ArrayList<ItemStack> need = needList.get(i);
                if (!need.isEmpty()) {
                    requesters.get(i).getRequestManager().initRequestDirectly(target, need);
                    requesters.get(i).markDirty();
                }
            }
            return true;
        }
        // incomplete structure
        return false;
    }

    @Override
    public RequestManager getRequestManager() {
        return requestManager;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return bypassInsertionCheck;
    }

    public void supplyToPorts() {
        if (detectStructure()) {
            ArrayList<TileAssemblyPort> allMarkedPorts = new ArrayList<>();
            ArrayList<TileAssemblyPort> otherPorts = new ArrayList<>();
            for (IAssemblyStructure t : cachedStructure) {
                if (t instanceof TileAssemblyPort) {
                    if (((TileAssemblyPort) t).getMarkManager().hasMark())
                        allMarkedPorts.add(((TileAssemblyPort) t));
                    else
                        otherPorts.add((TileAssemblyPort) t);
                }
            }
            for (int i = 9; i < getSizeInventory(); i++) {
                ItemStack takenOut = getStackInSlot(i);
                if (takenOut != null) {
                    for (TileAssemblyPort t : allMarkedPorts) {
                        t.bypassInsertionSide = true;
                        ItemUtil.addToInventory(takenOut, t, 0, t.getSizeInventory(), -1);
                        t.bypassInsertionSide = false;
                        if (takenOut.stackSize <= 0) {
                            takenOut = null;
                            setInventorySlotContents(i, null);
                            break;
                        }
                        setInventorySlotContents(i, takenOut);
                    }
                }
            }
            for (int i = 9; i < getSizeInventory(); i++) {
                ItemStack takenOut = getStackInSlot(i);
                if (takenOut != null) {
                    for (TileAssemblyPort t : otherPorts) {
                        t.bypassInsertionSide = true;
                        ItemUtil.addToInventory(takenOut, t, 0, t.getSizeInventory(), -1);
                        t.bypassInsertionSide = false;

                        if (takenOut.stackSize <= 0) {
                            takenOut = null;
                            setInventorySlotContents(i, null);
                            break;
                        }
                        setInventorySlotContents(i, takenOut);
                    }
                }
            }
        }
    }

    public void supplyFromRequester(TileAssemblyRequester requester) {
        bypassInsertionCheck = true;
        requester.getProvideManager().tryProvide(getRequestManager(), -1);
        bypassInsertionCheck = false;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        requestManager.writeToNBT(tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        requestManager.readFromNBT(tag);
    }

}
