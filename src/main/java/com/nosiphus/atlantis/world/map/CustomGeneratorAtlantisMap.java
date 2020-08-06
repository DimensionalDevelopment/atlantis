package com.nosiphus.atlantis.world.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomGeneratorAtlantisMap {

    public CubeAreas cubeAreas = new CubeAreas(new ArrayList<>());


    public static class CubeAreas {

        public final List<Map.Entry<IntAABB, CustomGeneratorAtlantisMap>> map;

        public CubeAreas(List<Map.Entry<IntAABB, CustomGeneratorAtlantisMap>> map) {
            this.map = map;
        }
    }

    public static class IntAABB {

        /**
         * The first x coordinate of a bounding box.
         */
        public int minX;
        /**
         * The first y coordinate of a bounding box.
         */
        public int minY;
        /**
         * The first z coordinate of a bounding box.
         */
        public int minZ;
        /**
         * The second x coordinate of a bounding box.
         */
        public int maxX;
        /**
         * The second y coordinate of a bounding box.
         */
        public int maxY;
        /**
         * The second z coordinate of a bounding box.
         */
        public int maxZ;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            IntAABB intAABB = (IntAABB) o;

            if (minX != intAABB.minX) {
                return false;
            }
            if (minY != intAABB.minY) {
                return false;
            }
            if (minZ != intAABB.minZ) {
                return false;
            }
            if (maxX != intAABB.maxX) {
                return false;
            }
            if (maxY != intAABB.maxY) {
                return false;
            }
            if (maxZ != intAABB.maxZ) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = minX;
            result = 31 * result + minY;
            result = 31 * result + minZ;
            result = 31 * result + maxX;
            result = 31 * result + maxY;
            result = 31 * result + maxZ;
            return result;
        }

        public boolean contains(int x, int y, int z) {
            return x >= minX && x <= maxX && z >= minZ && z <= maxZ && y >= minY && y <= maxY;
        }
    }


}
