package com.mystic.atlantis.util;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Math {
    public static double lerp(final double a, final double min, final double max) {
        return min + a * (max - min);
    }

    // reverse linear interpolation - unlerp(lerp(a, min, max), min, max) == a
    public static double unlerp(final double v, final double min, final double max) {
        return (v - min) / (max - min);
    }

    public static float unlerp(final float v, final float min, final float max) {
        return (v - min) / (max - min);
    }

    public static float unlerp(final long v, final long min, final long max) {
        return (v - min) / (float) (max - min);
    }

    public static float lerp(final float a, final float min, final float max) {
        return min + a * (max - min);
    }

    public static int log2(int n) {
        return (int) (java.lang.Math.log(n) / java.lang.Math.log(2));
    }

    public static int min(int a, int b) {
        return java.lang.Math.min(a, b);
    }

    public static int min(int a, int b, int c) {
        return java.lang.Math.min(java.lang.Math.min(a, b), c);
    }

    public static int min(int a, int b, int c, int d) {
        return java.lang.Math.min(java.lang.Math.min(a, b), java.lang.Math.min(c, d));
    }

    public static int min(int... a) {
        int min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] < min) {
                min = a[i];
            }
        }
        return min;
    }

    public static int max(int a, int b) {
        return java.lang.Math.max(a, b);
    }

    public static int max(int a, int b, int c) {
        return java.lang.Math.max(java.lang.Math.max(a, b), c);
    }

    public static int max(int a, int b, int c, int d) {
        return java.lang.Math.max(java.lang.Math.max(a, b), java.lang.Math.max(c, d));
    }

    public static int max(int... a) {
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
        }
        return max;
    }
}
