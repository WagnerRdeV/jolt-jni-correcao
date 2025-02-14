/*
Copyright (c) 2024 Stephen Gold

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.github.stephengold.joltjni;

import com.github.stephengold.joltjni.enumerate.ESupportMode;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * A type of {@code Shape} that inherently possesses the convex property.
 *
 * @author Stephen Gold sgold@sonic.net
 */
abstract public class ConvexShape extends Shape {
    // *************************************************************************
    // constructors

    /**
     * Instantiate a shape with no native object assigned.
     */
    ConvexShape() {
    }

    /**
     * Instantiate a shape with the specified native object assigned but not
     * owned.
     *
     * @param shapeVa the virtual address of the native object to assign (not
     * zero)
     */
    ConvexShape(long shapeVa) {
        super(shapeVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the density.
     *
     * @return the density
     */
    public float getDensity() {
        long shapeVa = va();
        float result = getDensity(shapeVa);

        return result;
    }

    /**
     * Access the shape's support function.
     *
     * @param supportMode how to handle convex radius (not null)
     * @param buffer buffer storage (not null)
     * @param scale scale factors to apply (in local coordinates, not null,
     * unaffected)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public Support getSupportFunction(
            ESupportMode supportMode, SupportBuffer buffer, Vec3Arg scale) {
        long shapeVa = va();
        int ordinal = supportMode.ordinal();
        long bufferVa = buffer.va();
        float sx = scale.getX();
        float sy = scale.getY();
        float sz = scale.getZ();
        long supportVa
                = getSupportFunction(shapeVa, ordinal, bufferVa, sx, sy, sz);
        Support result = new Support(this, supportVa);

        return result;
    }

    /**
     * Alter the density.
     *
     * @param density the desired density
     */
    public void setDensity(float density) {
        long shapeVa = va();
        setDensity(shapeVa, density);
    }
    // *************************************************************************
    // native private methods

    native private static float getDensity(long shapeVa);

    native private static long getSupportFunction(long shapeVa, int ordinal,
            long bufferVa, float sx, float sy, float sz);

    native private static void setDensity(long shapeVa, float density);
}
