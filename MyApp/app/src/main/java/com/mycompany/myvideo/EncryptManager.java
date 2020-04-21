package com.mycompany.myvideo;
import com.szcx.lib.encrypt.utils.MD5Util;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptManager 
{
    private static volatile EncryptManager instance;
    private String appKey = "scb37537f85scxpcm59f7e318b9epa51";
    private Cipher cipher;
    private String encryptKey;
    private boolean isDebug = false;
    private byte[][] key_iv;
    private SecretKeySpec skeySpec;
    
    private EncryptManager() {
        try {
            this.cipher = Cipher.getInstance("AES/CFB/NoPadding");
        } catch (Exception e) {
            e.printStackTrace();
            this.cipher = null;
        }
    }
    
    public static EncryptManager getInstance() {
        if (instance == null) {
            synchronized (EncryptManager.class) {
                if (instance == null) {
                    instance = new EncryptManager();
                }
            }
        }
        return instance;
    }
    
    public void init(String str, String str2) {
        this.encryptKey = str;
        this.appKey = str2;
        try {
            this.key_iv = EVP_BytesToKey(32, 16, (byte[]) null, str.getBytes("UTF-8"), 0);
            this.skeySpec = new SecretKeySpec(this.key_iv[0], "AES");
        } catch (Exception e) {
            e.printStackTrace();
            this.skeySpec = null;
        }
    }
    
    public String encrypt(String str) {
        if (str.isEmpty() || this.cipher == null || this.skeySpec == null) {
            return null;
        }
        try {
            this.cipher.init(1, this.skeySpec);
            try {
                return byte2hex(byteMerger(this.cipher.getIV(), this.cipher.doFinal(str.getBytes("UTF-8"))));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            return null;
        }
    }
    
    public String decrypt(String str) {
        if (str.isEmpty() || this.cipher == null || this.skeySpec == null) {
            return null;
        }
        byte[] hex2byte = hex2byte(str);
        byte[] copyOfRange = Arrays.copyOfRange(hex2byte, 0, 16);
        byte[] copyOfRange2 = Arrays.copyOfRange(hex2byte, 16, hex2byte.length);
        try {
            this.cipher.init(2, this.skeySpec, new IvParameterSpec(copyOfRange));
            String str2 = new String(this.cipher.doFinal(copyOfRange2), "UTF-8");
            return str2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static byte[] hex2byte(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length % 2 == 1) {
            return null;
        }
        int i = length / 2;
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 != i; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) Integer.parseInt(str.substring(i3, i3 + 2), 16);
        }
        return bArr;
    }
    
    public static byte[][] EVP_BytesToKey(int i, int i2, byte[] bArr, byte[] bArr2, int i3) throws Exception {
        byte[] digest;
        int i4;
        byte[] bArr3 = bArr;
        byte[] bArr4 = bArr2;
        MessageDigest instance = MessageDigest.getInstance("md5");
        int i5 = i;
        byte[] bArr5 = new byte[i5];
        int i6 = i2;
        byte[] bArr6 = new byte[i6];
        int i7 = 1;
        byte[][] bArr7 = {bArr5, bArr6};
        if (bArr4 == null) {
            return bArr7;
        }
        byte[] bArr8 = null;
        int i8 = i6;
        int i9 = 0;
        int i10 = 0;
        int i11 = i5;
        int i12 = 0;
        while (true) {
            instance.reset();
            int i13 = i12 + 1;
            if (i12 > 0) {
                instance.update(bArr8);
            }
            instance.update(bArr4);
            if (bArr3 != null) {
                instance.update(bArr3, 0, 8);
            }
            digest = instance.digest();
            int i14 = i3;
            for (int i15 = i7; i15 < i14; i15++) {
                instance.reset();
                instance.update(digest);
                digest = instance.digest();
            }
            if (i11 > 0) {
                i4 = 0;
                while (i11 != 0 && i4 != digest.length) {
                    bArr5[i9] = digest[i4];
                    i11--;
                    i4++;
                    i9++;
                }
            } else {
                i4 = 0;
            }
            if (i8 > 0 && i4 != digest.length) {
                while (i8 != 0 && i4 != digest.length) {
                    bArr6[i10] = digest[i4];
                    i8--;
                    i4++;
                    i10++;
                }
            }
            if (i11 == 0 && i8 == 0) {
                break;
            }
            i12 = i13;
            bArr8 = digest;
            i7 = 1;
        }
        for (int i16 = 0; i16 < digest.length; i16++) {
            digest[i16] = 0;
        }
        return bArr7;
    }
    
    public boolean isCanEncrypt() {
        return !this.encryptKey.isEmpty() && !this.appKey.isEmpty();
    }
    
    public static String byte2hex(byte[] bArr) {
        String str = "";
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                str = str + "0" + hexString;
            } else {
                str = str + hexString;
            }
        }
        return str.toUpperCase();
    }
    
    public static byte[] byteMerger(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[(bArr.length + bArr2.length)];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }
}
