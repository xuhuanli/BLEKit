package com.ehooworld.blekit;

import java.util.UUID;

/**
 * Created by xuhuanli on 2017/12/5.
 */

public class UUIDUtils {
    public static final String UUID_FORMAT = "0000%04x-0000-1000-8000-00805f9b34fb";

    public static UUID makeUUID(int value) {
        return UUID.fromString(String.format(UUID_FORMAT, value));
    }

    public static int getValue(UUID uuid) {
        return (int) (uuid.getMostSignificantBits() >>> 32);
    }
   /* public static void main(String[] args){

        System.out.println(UUIDUtils.makeUUID(0xf221));
        }*/
}
