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

/**
 * A vector composed of 3 double-precision components, used to represent
 * locations in 3-dimensional space.
 *
 * @author Stephen Gold sgold@sonic.net
 */
final public class RVec3 {
    // *************************************************************************
    // fields

    /**
     * the first (X) component
     */
    private double xx;
    /**
     * the 2nd (Y) component
     */
    private double yy;
    /**
     * the 3rd (Z) component
     */
    private double zz;
    // *************************************************************************
    // constructors

    /**
     * Instantiate an all-zero vector (0,0,0).
     */
    public RVec3() {
        this.xx = 0.;
        this.yy = 0.;
        this.zz = 0.;
    }

    /**
     * Instantiate a vector with specified components.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     */
    public RVec3(double x, double y, double z) {
        this.xx = x;
        this.yy = y;
        this.zz = z;
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Return the first (X) component at positional precision.
     *
     * @return the component value
     */
    public Object getX() {
        Object result;
        if (Jolt.isDoublePrecision()) {
            result = xx;
        } else {
            result = (float) xx;
        }

        return result;
    }

    /**
     * Return the 2nd (Y) component at positional precision.
     *
     * @return the component value
     */
    public Object getY() {
        Object result;
        if (Jolt.isDoublePrecision()) {
            result = yy;
        } else {
            result = (float) yy;
        }

        return result;
    }

    /**
     * Return the 3rd (Z) component at positional precision.
     *
     * @return the component value
     */
    public Object getZ() {
        Object result;
        if (Jolt.isDoublePrecision()) {
            result = zz;
        } else {
            result = (float) zz;
        }

        return result;
    }

    /**
     * Set all 3 components to specified values.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     */
    public void set(double x, double y, double z) {
        this.xx = x;
        this.yy = y;
        this.zz = z;
    }

    /**
     * Return the first (X) component in single precision.
     *
     * @return the component value
     */
    public float x() {
        return (float) xx;
    }

    /**
     * Return the first (X) component in double precision.
     *
     * @return the component value
     */
    public double xx() {
        return xx;
    }

    /**
     * Return the 2nd (Y) component in single precision.
     *
     * @return the component value
     */
    public float y() {
        return (float) yy;
    }

    /**
     * Return the 2nd (Y) component in double precision.
     *
     * @return the component value
     */
    public double yy() {
        return yy;
    }

    /**
     * Return the 3rd (Z) component in single precision.
     *
     * @return the component value
     */
    public float z() {
        return (float) zz;
    }

    /**
     * Return the 3rd (Z) component in double precision.
     *
     * @return the component value
     */
    public double zz() {
        return zz;
    }
    // *************************************************************************
    // Object methods

    @Override
    public String toString() {
        String result = "RVec3(" + getX() + " " + getY() + " " + getZ() + ")";
        return result;
    }
}