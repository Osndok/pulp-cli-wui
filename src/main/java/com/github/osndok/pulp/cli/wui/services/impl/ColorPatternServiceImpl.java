package com.github.osndok.pulp.cli.wui.services.impl;

import com.github.osndok.pulp.cli.wui.services.ColorPatternService;

public
class ColorPatternServiceImpl implements ColorPatternService
{
    @Override
    public
    String getCssColorFor(final String string)
    {
        if (string.equals("pulp"))
        {
            // Might as well use a color from pulp's official color pallet for the top level.
            // The green of the leaf: 04ae19
            //return "#04ae19";
            // The blue of the rind/text: 347dbe
            return "#347dbe";
            // The juicy orange center: ff9507
            //return "#ff9507";
        }

        // For everything else, we will try to come up with a pale psuedo-random color.
        // It needs to be stable for the same input across multiple calls to promote
        // mental-muscle-memory, it should be lighter than gray (so that the black text
        // is readable on top of it), and it each of the entries should have a distinct
        // color (to help differentiate the varios commands beyond just text).
        return intToReadableNearGrayHsl(string.hashCode());
    }

    // 64-bit mixer adapted from SplitMix (good avalanche for integer seeds)
    private static long mix64(long z) {
        z += 0x9E3779B97F4A7C15L;
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        return z ^ (z >>> 31);
    }

    /**
     * Optional helper: returns an HSL string with low saturation and high lightness.
     * That lets you have slightly tinted (but still near-gray) backgrounds.
     * Example: "hsl(210 8% 88%)"
     */
    public static String intToReadableNearGrayHsl(int id) {
        long mixed = mix64(id);
        int hue = Math.floorMod((int)(mixed & 0xFFFF), 360); // 0..359
        final int SAT_MIN = 0, SAT_MAX = 100;
        final int LIGHT_MIN = 30, LIGHT_MAX = 94;
        final int MIN_READABLE_LIGHT = 60; // lightweight heuristic to keep backgrounds readable with black text

        int sat = SAT_MIN + Math.floorMod((int)((mixed >>> 16) & 0xFFFF), (SAT_MAX - SAT_MIN + 1));
        int light = LIGHT_MIN + Math.floorMod((int)((mixed >>> 32) & 0xFFFF), (LIGHT_MAX - LIGHT_MIN + 1));

        if (light < MIN_READABLE_LIGHT) {
            // deterministic "nudge" upward to the readable floor
            light = MIN_READABLE_LIGHT + Math.floorMod((int)((mixed >>> 48) & 0xFFFF), (LIGHT_MAX - MIN_READABLE_LIGHT + 1));
            if (light > LIGHT_MAX) light = LIGHT_MAX;
        }

        return String.format("hsl(%d %d%% %d%%)", hue, sat, light);
    }
}
