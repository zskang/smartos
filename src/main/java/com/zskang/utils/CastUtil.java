package com.zskang.utils;

public class CastUtil {
    public static String caseString(Object obj) {
        return CastUtil.caseString(obj, "");
    }

    public static String caseString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = caseString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    public static long castLong(Object obj, long defaultValue) {
        long doubleValue = defaultValue;
        if (obj != null) {
            String strValue = caseString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    public static int castInt(Object obj, int defaultValue) {
        int doubleValue = defaultValue;
        if (obj != null) {
            String strValue = caseString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean doubleValue = defaultValue;
        if (obj != null) {
            String strValue = caseString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Boolean.parseBoolean(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

}
