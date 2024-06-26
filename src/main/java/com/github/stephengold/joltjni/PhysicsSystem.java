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
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class PhysicsSystem extends NonCopyable {
    // *************************************************************************
    // fields

    final private BodyInterface bodyInterface;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a physics system with the specified limits.
     *
     * @param maxBodies the desired maximum number of rigid bodies that can be
     * added
     * @param numBodyMutexes the desired number of mutexes to allocate, or 0 for
     * the default number
     * @param maxBodyPairs the desired maximum number of body pairs that can be
     * queued at a time
     * @param maxContactConstraints the desired capacity of the
     * contact-constraint buffer
     * @param map (not null)
     * @param ovbFilter (not null)
     * @param ovoFilter (not null)
     */
    public PhysicsSystem(int maxBodies, int numBodyMutexes, int maxBodyPairs,
            int maxContactConstraints, MapObj2Bp map, ObjVsBpFilter ovbFilter,
            ObjVsObjFilter ovoFilter) {
        long mapVa = map.va();
        long ovbFilterVa = ovbFilter.va();
        long ovoFilterVa = ovoFilter.va();
        long systemVa = createPhysicsSystem(
                maxBodies, numBodyMutexes, maxBodyPairs, maxContactConstraints,
                mapVa, ovbFilterVa, ovoFilterVa);
        setVirtualAddress(systemVa, true);

        long bodyInterfaceVa = getBodyInterface(systemVa);
        this.bodyInterface = new BodyInterface(bodyInterfaceVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Access the system's {@code BodyInterface}.
     *
     * @return the pre-existing instance (not null)
     */
    public BodyInterface getBodyInterface() {
        assert bodyInterface != null;
        return bodyInterface;
    }

    /**
     * Improve the performance of future collision detections.
     */
    public void optimizeBroadPhase() {
        optimizeBroadPhase(va());
    }

    /**
     * Advance the simulation by the specified amount.
     *
     * @param deltaTime the total time to advance (in seconds)
     * @param collisionSteps the number of simulation steps to perform
     * @param tempAllocator the allocator to use (not null)
     * @param jobSystem the job system to use (not null)
     * @return a bitmask of error conditions, or-ed together
     *
     * @see com.github.stephengold.joltjni.EPhysicsUpdateError
     */
    public int update(float deltaTime, int collisionSteps,
            TempAllocatorImpl tempAllocator, JobSystemThreadPool jobSystem) {
        long allocatorVa = tempAllocator.va();
        long jobSystemVa = jobSystem.va();
        int result = update(
                va(), deltaTime, collisionSteps, allocatorVa, jobSystemVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static long createPhysicsSystem(int maxBodies,
            int numBodyMutexes, int maxBodyPairs, int maxContactConstraints,
            long mapVa, long ovbFilterVa, long ovoFilterVa);

    native private static long getBodyInterface(long systemVa);

    native private static void optimizeBroadPhase(long systemVa);

    native private static int update(long physicsSystemVa, float deltaTime,
            int collisionSteps, long allocatorVa, long jobSystemVa);
}