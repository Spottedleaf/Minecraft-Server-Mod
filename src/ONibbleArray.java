public class ONibbleArray {
    public final byte[] a;

    public ONibbleArray(int var1) {
        this.a = new byte[var1 >> 1];
    }

    public ONibbleArray(byte[] var1) {
        this.a = var1;
    }

    public int a(int x, int y, int z) { // hMod start - implement Starlight
        int index = x << 11 | z << 7 | y;
        final byte value = this.a[index >>> 1];

        // if we are an even index, we want lower 4 bits
        // if we are an odd index, we want upper 4 bits
        return ((value >>> ((index & 1) << 2)) & 0xF);
        // hMod end - implement Starlight
    }

    public void a(int x, int y, int z, int value) { // hMod start - implement Starlight
        int index = x << 11 | z << 7 | y;
        final int shift = (index & 1) << 2;
        final int i = index >>> 1;

        this.a[i] = (byte)((this.a[i] & (0xF0 >>> shift)) | (value << shift));
        // hMod end - implement Starlight

    }

    public boolean a() {
        return this.a != null;
    }
}
