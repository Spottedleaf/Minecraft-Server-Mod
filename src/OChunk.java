import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OChunk {
    public static boolean a;
    public byte[] b;
    public boolean c;
    public OWorld d;
    public ONibbleArray e;
    public ONibbleArray f;
    public ONibbleArray g;
    public byte[] h;
    public int i;
    public final int j;
    public final int k;
    public Map l;
    public List[] m;
    public boolean n;
    public boolean o;
    public boolean p;
    public boolean q;
    public long r;

    public OChunk(OWorld var1, int var2, int var3) {
        this.l = new HashMap();
        this.m = new List[8];
        this.n = false;
        this.o = false;
        this.q = false;
        this.r = 0L;
        this.d = var1;
        this.j = var2;
        this.k = var3;
        this.h = new byte[256];

        for(int var4 = 0; var4 < this.m.length; ++var4) {
            this.m[var4] = new ArrayList();
        }

    }

    public OChunk(OWorld var1, byte[] var2, int var3, int var4) {
        this(var1, var3, var4);
        this.b = var2;
        this.e = new ONibbleArray(var2.length);
        this.f = new ONibbleArray(var2.length);
        this.g = new ONibbleArray(var2.length);
    }

    public boolean a(int var1, int var2) {
        return var1 == this.j && var2 == this.k;
    }

    public int b(int var1, int var2) {
        return this.h[var2 << 4 | var1] & 255;
    }

    public void a() {
    }

    // hMod start - implement starlight
    public void initLighting() {
        if (this.h == null) {
            this.b(); // init heightmaps
        }

        this.d.blockLight.initBlockLight(this.j, this.k);

        if (!this.d.m.e) { // isHell
            this.d.skyLight.initSkylight(this.j, this.k);
        }
    }

    public void updateLight(int localX, int worldY, int localZ) {
        int worldX = localX | (this.j << 4);
        int worldZ = localZ | (this.k << 4);
        // block emittance
        this.d.blockLight.checkBlockEmittance(worldX, worldY, worldZ);
        if (!this.d.m.e) {
            this.d.skyLight.checkSkyEmittance(worldX, worldY, worldZ);
        }
    }
    // hMod end - implement starlight

    public void b() {
        int var1 = 127;

        int var2;
        int var3;
        for(var2 = 0; var2 < 16; ++var2) {
            for(var3 = 0; var3 < 16; ++var3) {
                int var4 = 127;

                int var5;
                for(var5 = var2 << 11 | var3 << 7; var4 > 0 && OBlock.q[this.b[var5 + var4 - 1] & 255] == 0; --var4) {
                }

                this.h[var3 << 4 | var2] = (byte)var4;
                if (var4 < var1) {
                    var1 = var4;
                }

                if (!this.d.m.e) {
                    // hMod - implement starlight
                }
            }
        }

        this.i = var1;

        // hMod - implement starlight

        this.o = true;
    }

    public void c() {
    }

    private void c(int var1, int var2) {
        int var3 = this.b(var1, var2);
        int var4 = this.j * 16 + var1;
        int var5 = this.k * 16 + var2;
        this.f(var4 - 1, var5, var3);
        this.f(var4 + 1, var5, var3);
        this.f(var4, var5 - 1, var3);
        this.f(var4, var5 + 1, var3);
    }

    private void f(int var1, int var2, int var3) {
        int var4 = this.d.d(var1, var2);
        if (var4 > var3) {
            this.d.a(OEnumSkyBlock.a, var1, var3, var2, var1, var4, var2);
            this.o = true;
        } else if (var4 < var3) {
            this.d.a(OEnumSkyBlock.a, var1, var4, var2, var1, var3, var2);
            this.o = true;
        }

    }

    private void g(int var1, int var2, int var3) {
        int var4 = this.h[var3 << 4 | var1] & 255;
        int var5 = var4;
        if (var2 > var4) {
            var5 = var2;
        }

        for(int var6 = var1 << 11 | var3 << 7; var5 > 0 && OBlock.q[this.b[var6 + var5 - 1] & 255] == 0; --var5) {
        }

        if (var5 != var4) {
            this.d.g(var1, var3, var5, var4);
            this.h[var3 << 4 | var1] = (byte)var5;
            int var7;
            int var8;
            int var9;
            if (var5 < this.i) {
                this.i = var5;
            } else {
                var7 = 127;

                for(var8 = 0; var8 < 16; ++var8) {
                    for(var9 = 0; var9 < 16; ++var9) {
                        if ((this.h[var9 << 4 | var8] & 255) < var7) {
                            var7 = this.h[var9 << 4 | var8] & 255;
                        }
                    }
                }

                this.i = var7;
            }

            // hMod - starlight handles light modification

            this.o = true;
        }
    }

    public int a(int var1, int var2, int var3) {
        return this.b[var1 << 11 | var3 << 7 | var2] & 255;
    }

    public boolean a(int var1, int var2, int var3, int var4, int var5) {
        byte var6 = (byte)var4;
        int var7 = this.h[var3 << 4 | var1] & 255;
        int var8 = this.b[var1 << 11 | var3 << 7 | var2] & 255;
        if (var8 == var4 && this.e.a(var1, var2, var3) == var5) {
            return false;
        } else {
            int var9 = this.j * 16 + var1;
            int var10 = this.k * 16 + var3;
            this.b[var1 << 11 | var3 << 7 | var2] = (byte)(var6 & 255);
            if (var8 != 0 && !this.d.t) {
                OBlock.m[var8].b(this.d, var9, var2, var10);
            }

            this.e.a(var1, var2, var3, var5);
            if (!this.d.m.e) {
                if (OBlock.q[var6 & 255] != 0) {
                    if (var2 >= var7) {
                        this.g(var1, var2 + 1, var3);
                    }
                } else if (var2 == var7 - 1) {
                    this.g(var1, var2, var3);
                }

                // hMod - starlight handles light modification
            }

            // hMod start - starlight
            this.updateLight(var1, var2, var3);
            // hMod end - starlight
            this.e.a(var1, var2, var3, var5);
            if (var4 != 0) {
                OBlock.m[var4].e(this.d, var9, var2, var10);
            }

            this.o = true;
            return true;
        }
    }

    public boolean a(int var1, int var2, int var3, int var4) {
        byte var5 = (byte)var4;
        int var6 = this.h[var3 << 4 | var1] & 255;
        int var7 = this.b[var1 << 11 | var3 << 7 | var2] & 255;
        if (var7 == var4) {
            return false;
        } else {
            int var8 = this.j * 16 + var1;
            int var9 = this.k * 16 + var3;
            this.b[var1 << 11 | var3 << 7 | var2] = (byte)(var5 & 255);
            if (var7 != 0) {
                OBlock.m[var7].b(this.d, var8, var2, var9);
            }

            this.e.a(var1, var2, var3, 0);
            if (OBlock.q[var5 & 255] != 0) {
                if (var2 >= var6) {
                    this.g(var1, var2 + 1, var3);
                }
            } else if (var2 == var6 - 1) {
                this.g(var1, var2, var3);
            }

            // hMod start - starlight
            this.updateLight(var1, var2, var3);
            // hMod end - starlight
            if (var4 != 0 && !this.d.t) {
                OBlock.m[var4].e(this.d, var8, var2, var9);
            }

            this.o = true;
            return true;
        }
    }

    public int b(int var1, int var2, int var3) {
        return this.e.a(var1, var2, var3);
    }

    public void b(int var1, int var2, int var3, int var4) {
        this.o = true;
        this.e.a(var1, var2, var3, var4);
    }

    public int a(OEnumSkyBlock var1, int var2, int var3, int var4) {
        if (var1 == OEnumSkyBlock.a) {
            return this.f.a(var2, var3, var4);
        } else {
            return var1 == OEnumSkyBlock.b ? this.g.a(var2, var3, var4) : 0;
        }
    }

    public void a(OEnumSkyBlock var1, int var2, int var3, int var4, int var5) {
        this.o = true;
        if (var1 == OEnumSkyBlock.a) {
            this.f.a(var2, var3, var4, var5);
        } else {
            if (var1 != OEnumSkyBlock.b) {
                return;
            }

            this.g.a(var2, var3, var4, var5);
        }

    }

    public int c(int var1, int var2, int var3, int var4) {
        int var5 = this.f.a(var1, var2, var3);
        if (var5 > 0) {
            a = true;
        }

        var5 -= var4;
        int var6 = this.g.a(var1, var2, var3);
        if (var6 > var5) {
            var5 = var6;
        }

        return var5;
    }

    public void a(OEntity var1) {
        this.q = true;
        int var2 = OMathHelper.b(var1.aK / 16.0D);
        int var3 = OMathHelper.b(var1.aM / 16.0D);
        if (var2 != this.j || var3 != this.k) {
            System.out.println("Wrong location! " + var1);
            Thread.dumpStack();
        }

        int var4 = OMathHelper.b(var1.aL / 16.0D);
        if (var4 < 0) {
            var4 = 0;
        }

        if (var4 >= this.m.length) {
            var4 = this.m.length - 1;
        }

        var1.bA = true;
        var1.bB = this.j;
        var1.bC = var4;
        var1.bD = this.k;
        this.m[var4].add(var1);
    }

    public void b(OEntity var1) {
        this.a(var1, var1.bC);
    }

    public void a(OEntity var1, int var2) {
        if (var2 < 0) {
            var2 = 0;
        }

        if (var2 >= this.m.length) {
            var2 = this.m.length - 1;
        }

        this.m[var2].remove(var1);
    }

    public boolean c(int var1, int var2, int var3) {
        return var2 >= (this.h[var3 << 4 | var1] & 255);
    }

    public OTileEntity d(int var1, int var2, int var3) {
        OChunkPosition var4 = new OChunkPosition(var1, var2, var3);
        OTileEntity var5 = (OTileEntity)this.l.get(var4);
        if (var5 == null) {
            int var6 = this.a(var1, var2, var3);
            if (!OBlock.p[var6]) {
                return null;
            }

            OBlockContainer var7 = (OBlockContainer)OBlock.m[var6];
            var7.e(this.d, this.j * 16 + var1, var2, this.k * 16 + var3);
            var5 = (OTileEntity)this.l.get(var4);
        }

        return var5;
    }

    public void a(OTileEntity var1) {
        int var2 = var1.e - this.j * 16;
        int var3 = var1.f;
        int var4 = var1.g - this.k * 16;
        this.a(var2, var3, var4, var1);
    }

    public void a(int var1, int var2, int var3, OTileEntity var4) {
        OChunkPosition var5 = new OChunkPosition(var1, var2, var3);
        var4.d = this.d;
        var4.e = this.j * 16 + var1;
        var4.f = var2;
        var4.g = this.k * 16 + var3;
        if (this.a(var1, var2, var3) != 0 && OBlock.m[this.a(var1, var2, var3)] instanceof OBlockContainer) {
            if (this.c) {
                if (this.l.get(var5) != null) {
                    this.d.c.remove(this.l.get(var5));
                }

                this.d.c.add(var4);
            }

            this.l.put(var5, var4);
        } else {
            System.out.println("Attempted to place a tile entity where there was no entity tile!");
        }
    }

    public void e(int var1, int var2, int var3) {
        OChunkPosition var4 = new OChunkPosition(var1, var2, var3);
        if (this.c) {
            this.d.c.remove(this.l.remove(var4));
        }

    }

    public void d() {
        this.c = true;
        this.d.c.addAll(this.l.values());

        for(int var1 = 0; var1 < this.m.length; ++var1) {
            this.d.a(this.m[var1]);
        }

    }

    public void e() {
        this.c = false;
        this.d.c.removeAll(this.l.values());

        for(int var1 = 0; var1 < this.m.length; ++var1) {
            this.d.b(this.m[var1]);
        }

    }

    public void f() {
        this.o = true;
    }

    public void a(OEntity var1, OAxisAlignedBB var2, List var3) {
        int var4 = OMathHelper.b((var2.b - 2.0D) / 16.0D);
        int var5 = OMathHelper.b((var2.e + 2.0D) / 16.0D);
        if (var4 < 0) {
            var4 = 0;
        }

        if (var5 >= this.m.length) {
            var5 = this.m.length - 1;
        }

        for(int var6 = var4; var6 <= var5; ++var6) {
            List var7 = this.m[var6];

            for(int var8 = 0; var8 < var7.size(); ++var8) {
                OEntity var9 = (OEntity)var7.get(var8);
                if (var9 != var1 && var9.aU.a(var2)) {
                    var3.add(var9);
                }
            }
        }

    }

    public void a(Class var1, OAxisAlignedBB var2, List var3) {
        int var4 = OMathHelper.b((var2.b - 2.0D) / 16.0D);
        int var5 = OMathHelper.b((var2.e + 2.0D) / 16.0D);
        if (var4 < 0) {
            var4 = 0;
        }

        if (var5 >= this.m.length) {
            var5 = this.m.length - 1;
        }

        for(int var6 = var4; var6 <= var5; ++var6) {
            List var7 = this.m[var6];

            for(int var8 = 0; var8 < var7.size(); ++var8) {
                OEntity var9 = (OEntity)var7.get(var8);
                if (var1.isAssignableFrom(var9.getClass()) && var9.aU.a(var2)) {
                    var3.add(var9);
                }
            }
        }

    }

    public boolean a(boolean var1) {
        if (this.p) {
            return false;
        } else {
            if (var1) {
                if (this.q && this.d.l() != this.r) {
                    return true;
                }
            } else if (this.q && this.d.l() >= this.r + 600L) {
                return true;
            }

            return this.o;
        }
    }

    public int a(byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        int var9;
        int var10;
        int var11;
        int var12;
        for(var9 = var2; var9 < var5; ++var9) {
            for(var10 = var4; var10 < var7; ++var10) {
                var11 = var9 << 11 | var10 << 7 | var3;
                var12 = var6 - var3;
                System.arraycopy(this.b, var11, var1, var8, var12);
                var8 += var12;
            }
        }

        for(var9 = var2; var9 < var5; ++var9) {
            for(var10 = var4; var10 < var7; ++var10) {
                var11 = (var9 << 11 | var10 << 7 | var3) >> 1;
                var12 = (var6 - var3) / 2;
                System.arraycopy(this.e.a, var11, var1, var8, var12);
                var8 += var12;
            }
        }

        for(var9 = var2; var9 < var5; ++var9) {
            for(var10 = var4; var10 < var7; ++var10) {
                var11 = (var9 << 11 | var10 << 7 | var3) >> 1;
                var12 = (var6 - var3) / 2;
                System.arraycopy(this.g.a, var11, var1, var8, var12);
                var8 += var12;
            }
        }

        for(var9 = var2; var9 < var5; ++var9) {
            for(var10 = var4; var10 < var7; ++var10) {
                var11 = (var9 << 11 | var10 << 7 | var3) >> 1;
                var12 = (var6 - var3) / 2;
                System.arraycopy(this.f.a, var11, var1, var8, var12);
                var8 += var12;
            }
        }

        return var8;
    }

    public Random a(long var1) {
        return new Random(this.d.k() + (long)(this.j * this.j * 4987142) + (long)(this.j * 5947611) + (long)(this.k * this.k) * 4392871L + (long)(this.k * 389711) ^ var1);
    }

    public boolean g() {
        return false;
    }

    public void h() {
        for(int var1 = 0; var1 < this.b.length; ++var1) {
            byte var2 = this.b[var1];
            if (var2 != 0 && OBlock.m[var2 & 255] == null) {
                this.b[var1] = 0;
            }
        }

    }
}
