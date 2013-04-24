package de.thomasletsch.enocean.struct;

public class ByteArrayWrapper {

    private byte[] array;

    public ByteArrayWrapper() {
    	clear();
    }

    public ByteArrayWrapper(byte[] array) {
        this.array = array;
    }
    
    public void clear() {
        array = new byte[0];
    }
    

    public void setInt(int i, int pos) {
        array[pos] = (byte) ((i >> 24) & 0xFF);
        array[pos + 1] = (byte) ((i >> 16) & 0xFF);
        array[pos + 2] = (byte) ((i >> 8) & 0xFF);
        array[pos + 3] = (byte) ((i) & 0xFF);
    }

    public void addShort(short i) {
        int oldLength = array.length;
        expandArray(2);
        array[oldLength] = (byte) ((i >> 8) & 0xFF);
        array[oldLength + 1] = (byte) ((i) & 0xFF);
    }

    public void addInt(int i) {
        int oldLength = array.length;
        expandArray(4);
        array[oldLength] = (byte) ((i >> 24) & 0xFF);
        array[oldLength + 1] = (byte) ((i >> 16) & 0xFF);
        array[oldLength + 2] = (byte) ((i >> 8) & 0xFF);
        array[oldLength + 3] = (byte) ((i) & 0xFF);
    }

    public void setByte(byte b, int pos) {
        array[pos] = b;
    }

    public void addBytes(byte[] bytes) {
        int oldLength = array.length;
        expandArray(bytes.length);
        System.arraycopy(bytes, 0, array, oldLength, bytes.length);
    }

    public void addByte(byte b) {
        int oldLength = array.length;
        expandArray(1);
        array[oldLength] = b;
    }

    public byte[] getArray() {
        return array;
    }

    private void expandArray(int additionalBytes) {
        byte[] newArray = new byte[array.length + additionalBytes];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }
}
