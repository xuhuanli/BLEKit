package com.ehooworld.blekit;

import java.util.UUID;


/**
 * The type Uuid utils.
 */
public class UUIDUtils {
    /**
     * The constant UUID_FORMAT.
     */
    public static final String UUID_FORMAT = "0000%04x-0000-1000-8000-00805f9b34fb";

    /**
     * Make uuid uuid.
     *
     * @param value the value
     * @return the uuid
     */
    public static UUID makeUUID(int value) {
        return UUID.fromString(String.format(UUID_FORMAT, value));
    }

    /**
     * Gets value.
     *
     * @param uuid the uuid
     * @return the value
     */
    public static int getValue(UUID uuid) {
        return (int) (uuid.getMostSignificantBits() >>> 32);
    }
   /* public static void main(String[] args){

        System.out.println(UUIDUtils.makeUUID(0xf221));
        }*/
}
