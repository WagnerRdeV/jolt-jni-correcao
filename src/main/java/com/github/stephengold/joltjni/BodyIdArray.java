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

import com.github.stephengold.joltjni.readonly.ConstBodyId;

/**
 * A fixed-length array of body IDs. (native type: {@code BodyID[]})
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class BodyIdArray extends JoltPhysicsObject {
    // *************************************************************************
    // constructors

    /**
     * Instantiate an array with the specified length.
     *
     * @param length the desired number of IDs (&ge;0)
     */
    public BodyIdArray(int length) {
        long arrayVa = create(length);
        setVirtualAddress(arrayVa, () -> free(arrayVa));
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Copy the ID at the specified index.
     *
     * @param elementIndex the index from which to get the ID (&ge;0)
     * @return a new JVM object with the pre-existing native object assigned
     */
    public ConstBodyId get(int elementIndex) {
        long vectorVa = va();
        long idVa = getId(vectorVa, elementIndex);
        BodyId result = new BodyId(idVa, true);

        return result;
    }

    /**
     * Alter the specified ID at the specified index.
     *
     * @param elementIndex the index at which to put the ID (&ge;0)
     * @param id the ID to put (not null)
     */
    public void set(int elementIndex, BodyId id) {
        long vectorId = va();
        long idVa = id.va();
        setId(vectorId, elementIndex, idVa);
    }
    // *************************************************************************
    // native private methods

    native private static long create(int length);

    native private static void free(long vectorVa);

    native private static long getId(long vectorVa, int elementIndex);

    native private static void setId(
            long vectorVa, int elementIndex, long idVa);
}
