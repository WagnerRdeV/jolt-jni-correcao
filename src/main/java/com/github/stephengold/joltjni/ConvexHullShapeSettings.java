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

import java.nio.FloatBuffer;

/**
 * Settings used to construct a {@code ConvexHullShape}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class ConvexHullShapeSettings extends ConvexShapeSettings {
    // *************************************************************************
    // constants

    /**
     * number of floats in a vertex
     */
    final private static int numAxes = 3;
    // *************************************************************************
    // constructors

    /**
     * Instantiate settings for the specified points.
     *
     * @param points an array of point locations (not null)
     */
    public ConvexHullShapeSettings(Vec3Arg... points) {
        int numPoints = points.length;
        int numFloats = numAxes * numPoints;
        FloatBuffer buffer = Jolt.newDirectFloatBuffer(numFloats);
        for (Vec3Arg point : points) {
            buffer.put(point.getX());
            buffer.put(point.getY());
            buffer.put(point.getZ());
        }
        long settingsVa = createConvexHullShapeSettings(numPoints, buffer);
        setVirtualAddress(settingsVa, true);
    }

    /**
     * Instantiate settings for the specified points.
     *
     * @param numPoints the number of points
     * @param points a direct buffer containing point locations
     */
    public ConvexHullShapeSettings(int numPoints, FloatBuffer points) {
        long settingsVa = createConvexHullShapeSettings(numPoints, points);
        setVirtualAddress(settingsVa, true);
    }
    // *************************************************************************
    // ShapeSettings methods

    /**
     * Generate a shape from these settings.
     *
     * @return a new instance
     */
    @Override
    public Shape createShape() {
        long settingsVa = va();
        long shapeVa = createConvexHullShape(settingsVa);
        assert shapeVa != 0L;
        ConvexHullShape result = new ConvexHullShape(shapeVa);
        assert result.getSubType() == EShapeSubType.ConvexHull :
                result.getSubType();

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createConvexHullShape(long settingsVa);

    native private static long createConvexHullShapeSettings(
            int numPoints, FloatBuffer points);
}