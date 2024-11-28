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

import com.github.stephengold.joltjni.enumerate.EBodyType;
import com.github.stephengold.joltjni.enumerate.EMotionType;
import com.github.stephengold.joltjni.readonly.ConstAaBox;
import com.github.stephengold.joltjni.readonly.ConstBody;
import com.github.stephengold.joltjni.readonly.ConstShape;
import com.github.stephengold.joltjni.readonly.QuatArg;
import com.github.stephengold.joltjni.readonly.RVec3Arg;
import com.github.stephengold.joltjni.readonly.Vec3Arg;

/**
 * An object with mass, position, and shape that can be added to a
 * {@code PhysicsSystem}. Bodies may be dynamic, kinematic, or static.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class Body extends NonCopyable implements ConstBody {
    // *************************************************************************
    // fields

    /**
     * protect the collision group from garbage collection
     */
    private CollisionGroup group;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a body with the specified native object assigned but not
     * owned.
     * <p>
     * For use in custom contact listeners.
     *
     * @param bodyVa the virtual address of the native object to assign (not
     * zero)
     */
    public Body(long bodyVa) {
        super(bodyVa);
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Apply the specified angular impulse to the body.
     *
     * @param impulse the impulse vector (Newton.meter.seconds in system
     * coordinates, not null, unaffected)
     */
    public void addAngularImpulse(Vec3Arg impulse) {
        long bodyVa = va();
        float x = impulse.getX();
        float y = impulse.getY();
        float z = impulse.getZ();
        addAngularImpulse(bodyVa, x, y, z);
    }

    /**
     * Apply the specified force to the body's center of mass.
     *
     * @param force the force vector (Newtons in system coordinates, not null,
     * unaffected)
     */
    public void addForce(Vec3Arg force) {
        long bodyVa = va();
        float fx = force.getX();
        float fy = force.getY();
        float fz = force.getZ();
        addForce(bodyVa, fx, fy, fz);
    }

    /**
     * Apply the specified force at the specified location.
     *
     * @param force the force vector (not null, unaffected)
     * @param location where to apply the force (Newtons in system coordinates
     * not null, unaffected)
     */
    public void addForce(Vec3Arg force, RVec3Arg location) {
        long bodyVa = va();
        float fx = force.getX();
        float fy = force.getY();
        float fz = force.getZ();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        addForce(bodyVa, fx, fy, fz, locX, locY, locZ);
    }

    /**
     * Apply the specified impulse to the body's center of mass.
     *
     * @param impulse the impulse vector (kilogram.meters per second in system
     * coordinates, not null, unaffected)
     */
    public void addImpulse(Vec3Arg impulse) {
        long bodyVa = va();
        float jx = impulse.getX();
        float jy = impulse.getY();
        float jz = impulse.getZ();
        addImpulse(bodyVa, jx, jy, jz);
    }

    /**
     * Apply the specified impulse at the specified location.
     *
     * @param impulse the impulse vector (kilogram.meters per second in system
     * coordinates, not null, unaffected)
     * @param location where to apply the impulse (not null, unaffected)
     */
    public void addImpulse(Vec3Arg impulse, RVec3Arg location) {
        long bodyVa = va();
        float jx = impulse.getX();
        float jy = impulse.getY();
        float jz = impulse.getZ();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        addImpulse(bodyVa, jx, jy, jz, locX, locY, locZ);
    }

    /**
     * Apply the specified torque to the body.
     *
     * @param torque the torque vector (Newton.meters in system coordinates, not
     * null, unaffected)
     */
    public void addTorque(Vec3Arg torque) {
        long bodyVa = va();
        float x = torque.getX();
        float y = torque.getY();
        float z = torque.getZ();
        addTorque(bodyVa, x, y, z);
    }

    /**
     * Apply an impulse that simulates buoyancy and drag.
     *
     * @param surfacePosition the location of the fluid's surface (in system
     * coordinates, not null, unaffected)
     * @param surfaceNormal the upward normal direction of the fluid's surface
     * (in system coordinates, not null, unaffected)
     * @param buoyancy the mass of the displaced fluid divided by the body's
     * mass (1&rarr;neutral buoyancy)
     * @param linearDrag the drag factor for linear motion
     * @param angularDrag the drag factor for angular motion
     * @param fluidVelocity the velocity of the fluid (meters per second in
     * system coordinates, not null, unaffected)
     * @param gravity the gravity vector (in system coordinates, not null,
     * unaffected)
     * @param deltaTime the duration of the simulation step (in seconds)
     * @return {@code true} if an impulse was applied, {@code false} if not in
     * the fluid
     */
    public boolean applyBuoyancyImpulse(
            RVec3Arg surfacePosition, Vec3Arg surfaceNormal, float buoyancy,
            float linearDrag, float angularDrag, Vec3Arg fluidVelocity,
            Vec3Arg gravity, float deltaTime) {
        long bodyVa = va();
        double surfaceX = surfacePosition.xx();
        double surfaceY = surfacePosition.yy();
        double surfaceZ = surfacePosition.zz();
        float nx = surfaceNormal.getX();
        float ny = surfaceNormal.getY();
        float nz = surfaceNormal.getZ();
        float vx = fluidVelocity.getX();
        float vy = fluidVelocity.getY();
        float vz = fluidVelocity.getZ();
        float gravityX = fluidVelocity.getX();
        float gravityY = fluidVelocity.getY();
        float gravityZ = fluidVelocity.getZ();
        boolean result = applyBuoyancyImpulse(bodyVa, surfaceX, surfaceY,
                surfaceZ, nx, ny, nz, buoyancy, linearDrag, angularDrag,
                vx, vy, vz, gravityX, gravityY, gravityZ, deltaTime);

        return result;
    }

    /**
     * Access the body's collision group.
     *
     * @return the pre-existing group, or {@code null} if none
     */
    public CollisionGroup getCollisionGroup() {
        return group;
    }

    /**
     * Access the body's motion properties.
     *
     * @return a new JVM object with the pre-existing native object assigned, or
     * {@code null} if none
     */
    public MotionProperties getMotionProperties() {
        MotionProperties result;
        long bodyVa = va();
        if (isStatic(bodyVa)) {
            result = null;
        } else {
            long propertiesVa = getMotionProperties(bodyVa);
            result = new MotionProperties(propertiesVa);
        }

        return result;
    }

    /**
     * Reposition the body, assuming it's kinematic.
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected)
     * @param orientation the desired orientation (relative to the system axes,
     * not null, unaffected)
     * @param deltaTime time until the desired position is reached (in seconds,
     * &gt;0)
     */
    public void moveKinematic(
            RVec3Arg location, QuatArg orientation, float deltaTime) {
        long bodyVa = va();
        double xx = location.xx();
        double yy = location.yy();
        double zz = location.zz();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        moveKinematic(bodyVa, xx, yy, zz, qx, qy, qz, qw, deltaTime);
    }

    /**
     * Reset the body's sleep timer. This does not activate the body, but allows
     * resetting the system that detects sleeping bodies.
     */
    public void resetSleepTimer() {
        long bodyVa = va();
        resetSleepTimer(bodyVa);
    }

    /**
     * Alter whether the body is allowed to fall asleep.
     *
     * @param allow {@code true} to allow, {@code false} to inhibit
     */
    public void setAllowSleeping(boolean allow) {
        long bodyVa = va();
        setAllowSleeping(bodyVa, allow);
    }

    /**
     * Directly alter the body's angular velocity.
     *
     * @param omega the desired angular velocity (not null, unaffected,
     * default=(0,0,0))
     */
    public void setAngularVelocity(Vec3Arg omega) {
        long bodyVa = va();
        float wx = omega.getX();
        float wy = omega.getY();
        float wz = omega.getZ();
        setAngularVelocity(bodyVa, wx, wy, wz);
    }

    /**
     * Alter the body's angular velocity within limits.
     *
     * @param omega the desired angular velocity (not null, unaffected,
     * default=(0,0,0))
     */
    public void setAngularVelocityClamped(Vec3Arg omega) {
        long bodyVa = va();
        float wx = omega.getX();
        float wy = omega.getY();
        float wz = omega.getZ();
        setAngularVelocityClamped(bodyVa, wx, wy, wz);
    }

    /**
     * Assign the body to the specified collision group.
     *
     * @param group the group to assign (not null, alias created)
     */
    public void setCollisionGroup(CollisionGroup group) {
        this.group = group;
        long bodyVa = va();
        long groupVa = group.va();
        setCollisionGroup(bodyVa, groupVa);
    }

    /**
     * Alter whether extra effort should be made to remove ghost contacts.
     *
     * @param enhance {@code true} for extra effort, {@code false} for ordinary
     * effort (default=false)
     */
    public void setEnhancedInternalEdgeRemoval(boolean enhance) {
        long bodySettingsVa = va();
        setEnhancedInternalEdgeRemoval(bodySettingsVa, enhance);
    }

    /**
     * Alter the body's friction ratio.
     *
     * @param friction the desired ratio (typically &ge;0 and &le;1,
     * default=0.2)
     */
    public void setFriction(float friction) {
        long bodyVa = va();
        setFriction(bodyVa, friction);
    }

    /**
     * Alter whether the body is a sensor.
     *
     * @param setting {@code true} to make it a sensor, {@code false} for a
     * regular body (default=false)
     */
    public void setIsSensor(boolean setting) {
        long bodyVa = va();
        setIsSensor(bodyVa, setting);
    }

    /**
     * Directly alter the body's linear velocity.
     *
     * @param velocity the desired linear velocity (in meters per second, not
     * null, unaffected, default=(0,0,0))
     */
    public void setLinearVelocity(Vec3Arg velocity) {
        long bodyVa = va();
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setLinearVelocity(bodyVa, vx, vy, vz);
    }

    /**
     * Alter the body's linear velocity within limits.
     *
     * @param velocity the desired linear velocity (in meters per second, not
     * null, unaffected, default=(0,0,0))
     */
    public void setLinearVelocityClamped(Vec3Arg velocity) {
        long bodyVa = va();
        float vx = velocity.getX();
        float vy = velocity.getY();
        float vz = velocity.getZ();
        setLinearVelocityClamped(bodyVa, vx, vy, vz);
    }

    /**
     * Alter the body's motion type.
     *
     * @param motionType the desired value (not null)
     */
    public void setMotionType(EMotionType motionType) {
        long bodyVa = va();
        int ordinal = motionType.ordinal();
        setMotionType(bodyVa, ordinal);
    }

    /**
     * Re-position the body and reset its sleep timer.
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected)
     * @param orientation the desired orientation (relative to the system axes,
     * not null, unaffected)
     */
    public void setPositionAndRotationInternal(
            RVec3Arg location, QuatArg orientation) {
        setPositionAndRotationInternal(location, orientation, true);
    }

    /**
     * Re-position the body.
     *
     * @param location the desired location (in system coordinates, not null,
     * unaffected)
     * @param orientation the desired orientation (relative to the system axes,
     * not null, unaffected)
     * @param resetSleepTimer {@code true} to reset the body's sleep timer,
     * {@code false} to leave the timer unchanged
     */
    public void setPositionAndRotationInternal(
            RVec3Arg location, QuatArg orientation, boolean resetSleepTimer) {
        long bodyVa = va();
        double locX = location.xx();
        double locY = location.yy();
        double locZ = location.zz();
        float qw = orientation.getW();
        float qx = orientation.getX();
        float qy = orientation.getY();
        float qz = orientation.getZ();
        setPositionAndRotationInternal(
                bodyVa, locX, locY, locZ, qx, qy, qz, qw, resetSleepTimer);
    }

    /**
     * Alter the body's restitution ratio.
     *
     * @param restitution the desired ratio (typically &ge;0 and &le;1,
     * default=0)
     */
    public void setRestitution(float restitution) {
        long bodyVa = va();
        setRestitution(bodyVa, restitution);
    }

    /**
     * Alter the body's user data.
     *
     * @param value the desired value (default=0)
     */
    public void setUserData(long value) {
        long bodyVa = va();
        setUserData(bodyVa, value);
    }

    /**
     * Create a dummy body that can be used to attach a constraint to the world.
     *
     * @return a new JVM object with the pre-existing native object assigned
     */
    public static Body sFixedToWorld() {
        long bodyVa = createFixedToWorld();
        Body result = new Body(bodyVa);

        return result;
    }
    // *************************************************************************
    // ConstBody methods

    /**
     * Test whether the body could be made kinematic or dynamic. The body is
     * unaffected.
     *
     * @return {@code true} if possible, otherwise {@code false}
     */
    @Override
    public boolean canBeKinematicOrDynamic() {
        long bodyVa = va();
        boolean result = canBeKinematicOrDynamic(bodyVa);

        return result;
    }

    /**
     * Return the net force acting on the body. The body is unaffected.
     *
     * @return a new force vector (Newtons in system coordinates)
     */
    @Override
    public Vec3 getAccumulatedForce() {
        long bodyVa = va();
        float x = getAccumulatedForceX(bodyVa);
        float y = getAccumulatedForceY(bodyVa);
        float z = getAccumulatedForceZ(bodyVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Return the net torque acting on the body. The body is unaffected.
     *
     * @return a new torque vector (Newton.meters in system coordinates)
     */
    @Override
    public Vec3 getAccumulatedTorque() {
        long bodyVa = va();
        float x = getAccumulatedTorqueX(bodyVa);
        float y = getAccumulatedTorqueY(bodyVa);
        float z = getAccumulatedTorqueZ(bodyVa);
        Vec3 result = new Vec3(x, y, z);

        return result;
    }

    /**
     * Test whether the body is allowed to fall asleep. The body is unaffected.
     *
     * @return {@code true} if allowed, otherwise {@code false}
     */
    @Override
    public boolean getAllowSleeping() {
        long bodyVa = va();
        boolean result = getAllowSleeping(bodyVa);

        return result;
    }

    /**
     * Return the body's angular velocity. The body is unaffected.
     *
     * @return a new velocity vector (radians per second in system coordinates)
     */
    @Override
    public Vec3 getAngularVelocity() {
        long bodyVa = va();
        float wx = getAngularVelocityX(bodyVa);
        float wy = getAngularVelocityY(bodyVa);
        float wz = getAngularVelocityZ(bodyVa);
        Vec3 result = new Vec3(wx, wy, wz);

        return result;
    }

    /**
     * Convert the body to a {@code BodyCreationSettings} object. The body is
     * unaffected.
     *
     * @return a new object
     */
    @Override
    public BodyCreationSettings getBodyCreationSettings() {
        long bodyVa = va();
        long bodySettingsVa = getBodyCreationSettings(bodyVa);
        BodyCreationSettings result
                = new BodyCreationSettings(bodySettingsVa, true);

        return result;
    }

    /**
     * Return the body type (rigid or soft). The body is unaffected.
     *
     * @return an enum value (not null)
     */
    @Override
    public EBodyType getBodyType() {
        long bodyVa = va();
        int ordinal = getBodyType(bodyVa);
        EBodyType result = EBodyType.values()[ordinal];

        return result;
    }

    /**
     * Return the broadphase layer. The body is unaffected.
     *
     * @return the layer ID
     */
    @Override
    public int getBroadPhaseLayer() {
        long bodyVa = va();
        int result = getBroadPhaseLayer(bodyVa);

        return result;
    }

    /**
     * Copy the location of the body's center of mass (which might not coincide
     * with its origin). The body is unaffected.
     *
     * @return a new location vector (in system coordinates, all components
     * finite)
     */
    @Override
    public RVec3 getCenterOfMassPosition() {
        long bodyVa = va();

        double xx = getCenterOfMassPositionX(bodyVa);
        assert Double.isFinite(xx) : "xx = " + xx;

        double yy = getCenterOfMassPositionY(bodyVa);
        assert Double.isFinite(yy) : "yy = " + yy;

        double zz = getCenterOfMassPositionZ(bodyVa);
        assert Double.isFinite(zz) : "zz = " + zz;

        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

    /**
     * Copy the coordinate transform of the body's center of mass. The body is
     * unaffected.
     *
     * @return a new transform matrix (relative to system coordinates)
     */
    @Override
    public RMat44 getCenterOfMassTransform() {
        long bodyVa = va();
        long matrixVa = getCenterOfMassTransform(bodyVa);
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }

    /**
     * Test whether extra effort should be made to remove ghost contacts. The
     * body is unaffected.
     *
     * @return {@code true} for extra effort, otherwise {@code false}
     */
    @Override
    public boolean getEnhancedInternalEdgeRemoval() {
        long bodySettingsVa = va();
        boolean result = getEnhancedInternalEdgeRemoval(bodySettingsVa);

        return result;
    }

    /**
     * Return the body's friction ratio. The body is unaffected.
     *
     * @return the ratio
     */
    @Override
    public float getFriction() {
        long bodyVa = va();
        float result = getFriction(bodyVa);
        return result;
    }

    /**
     * Copy the body's ID for use with {@code BodyInterface}. The body is
     * unaffected.
     *
     * @return a new object
     */
    @Override
    public BodyId getId() {
        long bodyVa = va();
        long bodyIdVa = getId(bodyVa);
        BodyId result = new BodyId(bodyIdVa, true);

        return result;
    }

    /**
     * Return the body's linear velocity. The body is unaffected.
     *
     * @return a new velocity vector (meters per second in system coordinates)
     */
    @Override
    public Vec3 getLinearVelocity() {
        long bodyVa = va();
        float vx = getLinearVelocityX(bodyVa);
        float vy = getLinearVelocityY(bodyVa);
        float vz = getLinearVelocityZ(bodyVa);
        Vec3 result = new Vec3(vx, vy, vz);

        return result;
    }

    /**
     * Return the body's motion type. The body is unaffected.
     *
     * @return an enum value (not null)
     */
    @Override
    public EMotionType getMotionType() {
        long bodyVa = va();
        int ordinal = getMotionType(bodyVa);
        EMotionType result = EMotionType.values()[ordinal];

        return result;
    }

    /**
     * Return the body's object layer. The body is unaffected.
     *
     * @return a layer index (&ge;0)
     */
    @Override
    public int getObjectLayer() {
        long bodyVa = va();
        int result = getObjectLayer(bodyVa);

        return result;
    }

    /**
     * Return the location of the body's origin (which might not coincide with
     * its center of mass). The body is unaffected.
     *
     * @return a new location vector (in system coordinates, all components
     * finite)
     */
    @Override
    public RVec3 getPosition() {
        long bodyVa = va();

        double xx = getPositionX(bodyVa);
        assert Double.isFinite(xx) : "xx = " + xx;

        double yy = getPositionY(bodyVa);
        assert Double.isFinite(yy) : "yy = " + yy;

        double zz = getPositionZ(bodyVa);
        assert Double.isFinite(zz) : "zz = " + zz;

        RVec3 result = new RVec3(xx, yy, zz);

        return result;
    }

    /**
     * Return the body's restitution ratio. The body is unaffected.
     *
     * @return the value (typically &ge;0 and &le;1)
     */
    @Override
    public float getRestitution() {
        long bodyVa = va();
        float result = getRestitution(bodyVa);

        return result;
    }

    /**
     * Return the body's orientation. The body is unaffected.
     *
     * @return a new rotation quaternion (relative to the system axes)
     */
    @Override
    public Quat getRotation() {
        long bodyVa = va();
        float qx = getRotationX(bodyVa);
        float qy = getRotationY(bodyVa);
        float qz = getRotationZ(bodyVa);
        float qw = getRotationW(bodyVa);
        Quat result = new Quat(qx, qy, qz, qw);

        return result;
    }

    /**
     * Access the body's shape.
     *
     * @return a new immutable JVM object with the pre-existing native object
     * assigned, or {@code null} if none
     */
    @Override
    public ConstShape getShape() {
        long bodyVa = va();
        long shapeVa = getShape(bodyVa);
        ConstShape result = Shape.newShape(shapeVa);

        return result;
    }

    /**
     * Return the body's user data: can be used for anything. The body is
     * unaffected.
     *
     * @return the value
     */
    @Override
    public long getUserData() {
        long bodyVa = va();
        long result = getUserData(bodyVa);

        return result;
    }

    /**
     * Access the body's bounding box. The body is unaffected.
     *
     * @return a new immutable JVM object with the pre-existing native object
     * assigned
     */
    @Override
    public ConstAaBox getWorldSpaceBounds() {
        long bodyVa = va();
        long boxVa = getWorldSpaceBounds(bodyVa);
        JoltPhysicsObject container = getContainingObject();
        ConstAaBox result = new AaBox(container, boxVa);

        return result;
    }

    /**
     * Copy the world transform. The body is unaffected.
     *
     * @return a new matrix relative to system coordinates
     */
    @Override
    public RMat44 getWorldTransform() {
        long bodyVa = va();
        long matrixVa = getWorldTransform(bodyVa);
        RMat44 result = new RMat44(matrixVa, true);

        return result;
    }

    /**
     * Test whether the body is deactivated. The body is unaffected.
     *
     * @return {@code false} if deactivated, otherwise {@code true}
     */
    @Override
    public boolean isActive() {
        long bodyVa = va();
        boolean result = isActive(bodyVa);

        return result;
    }

    /**
     * Test whether the body is dynamic. The body is unaffected.
     *
     * @return {@code true} if dynamic, otherwise {@code false}
     */
    @Override
    public boolean isDynamic() {
        long bodyVa = va();
        boolean result = isDynamic(bodyVa);

        return result;
    }

    /**
     * Test whether the body has been added to its {@code PhysicsSystem}. The
     * body is unaffected.
     *
     * @return {@code true} if added, otherwise {@code false}
     */
    @Override
    public boolean isInBroadPhase() {
        long bodyVa = va();
        boolean result = isInBroadPhase(bodyVa);

        return result;
    }

    /**
     * Test whether the body is kinematic. It is unaffected.
     *
     * @return {@code true} if kinematic, otherwise {@code false}
     */
    @Override
    public boolean isKinematic() {
        long bodyVa = va();
        boolean result = isKinematic(bodyVa);

        return result;
    }

    /**
     * Test whether the body is a rigid body. It is unaffected.
     *
     * @return {@code true} if rigid body, otherwise {@code false}
     */
    @Override
    public boolean isRigidBody() {
        long bodyVa = va();
        boolean result = isRigidBody(bodyVa);

        return result;
    }

    /**
     * Test whether the body is a sensor. The body is unaffected.
     *
     * @return {@code true} if a sensor, otherwise {@code false}
     */
    @Override
    public boolean isSensor() {
        long bodyVa = va();
        boolean result = isSensor(bodyVa);

        return result;
    }

    /**
     * Test whether the body is static (non-moving). It is unaffected.
     *
     * @return {@code true} if static, otherwise {@code false}
     */
    @Override
    public boolean isStatic() {
        long bodyVa = va();
        boolean result = isStatic(bodyVa);

        return result;
    }
    // *************************************************************************
    // native private methods

    native private static void addAngularImpulse(
            long bodyVa, float x, float y, float z);

    native private static void addForce(
            long bodyVa, float fx, float fy, float fz);

    native private static void addForce(long bodyVa, float fx, float fy,
            float fz, double locX, double locY, double locZ);

    native private static void addImpulse(
            long bodyVa, float jx, float jy, float jz);

    native private static void addImpulse(long bodyVa, float jx, float jy,
            float jz, double locX, double locY, double locZ);

    native private static void addTorque(
            long bodyVa, float x, float y, float z);

    native private static boolean applyBuoyancyImpulse(long bodyVa,
            double surfaceX, double surfaceY, double surfaceZ, float nx,
            float ny, float nz, float buoyancy, float linearDrag,
            float angularDrag, float vx, float vy, float vz, float gravityX,
            float gravityY, float gravityZ, float deltaTime);

    private static native boolean canBeKinematicOrDynamic(long bodyVa);
    private static native long createFixedToWorld();
    private static native float getAccumulatedForceX(long bodyVa);
    private static native float getAccumulatedForceY(long bodyVa);
    private static native float getAccumulatedForceZ(long bodyVa);
    private static native float getAccumulatedTorqueX(long bodyVa);
    private static native float getAccumulatedTorqueY(long bodyVa);
    private static native float getAccumulatedTorqueZ(long bodyVa);
    private static native boolean getAllowSleeping(long bodyVa);
    private static native float getAngularVelocityX(long bodyVa);
    private static native float getAngularVelocityY(long bodyVa);
    private static native float getAngularVelocityZ(long bodyVa);
    private static native long getBodyCreationSettings(long bodyVa);
    private static native int getBodyType(long bodyVa);
    private static native int getBroadPhaseLayer(long bodyVa);
    private static native double getCenterOfMassPositionX(long bodyVa);
    private static native double getCenterOfMassPositionY(long bodyVa);
    private static native double getCenterOfMassPositionZ(long bodyVa);
    private static native long getCenterOfMassTransform(long bodyVa);
    private static native boolean getEnhancedInternalEdgeRemoval(long bodyVa);
    private static native float getFriction(long bodyVa);
    private static native long getId(long bodyVa);
    private static native float getLinearVelocityX(long bodyVa);
    private static native float getLinearVelocityY(long bodyVa);
    private static native float getLinearVelocityZ(long bodyVa);
    private static native long getMotionProperties(long bodyVa);
    private static native int getMotionType(long bodyVa);
    private static native int getObjectLayer(long bodyVa);
    private static native double getPositionX(long bodyVa);
    private static native double getPositionY(long bodyVa);
    private static native double getPositionZ(long bodyVa);
    private static native float getRestitution(long bodyVa);
    private static native float getRotationX(long bodyVa);
    private static native float getRotationY(long bodyVa);
    private static native float getRotationZ(long bodyVa);
    private static native float getRotationW(long bodyVa);
    private static native long getShape(long bodyVa);
    private static native long getUserData(long bodyVa);
    private static native long getWorldSpaceBounds(long bodyVa);
    private static native long getWorldTransform(long bodyVa);
    private static native boolean isActive(long bodyVa);
    private static native boolean isDynamic(long bodyVa);
    private static native boolean isInBroadPhase(long bodyVa);
    private static native boolean isKinematic(long bodyVa);
    private static native boolean isRigidBody(long bodyVa);
    private static native boolean isSensor(long bodyVa);
    private static native boolean isStatic(long bodyVa);
    private static native void moveKinematic(long bodyVa, double xx, double yy, double zz, float qx, float qy, float qz, float qw, float deltaTime);
    private static native void resetSleepTimer(long bodyVa);
    private static native void setAllowSleeping(long bodyVa, boolean allow);
    private static native void setAngularVelocity(long bodyVa, float wx, float wy, float wz);
    private static native void setAngularVelocityClamped(long bodyVa, float wx, float wy, float wz);
    private static native void setCollisionGroup(long bodyVa, long groupVa);
    private static native void setEnhancedInternalEdgeRemoval(long bodySettingsVa, boolean enhance);
    private static native void setFriction(long bodyVa, float friction);
    private static native void setIsSensor(long bodyVa, boolean setting);
    private static native void setLinearVelocity(long bodyVa, float vx, float vy, float vz);
    private static native void setLinearVelocityClamped(long bodyVa, float vx, float vy, float vz);
    private static native void setMotionType(long bodyVa, int ordinal);
    private static native void setPositionAndRotationInternal(long bodyVa, double locX, double locY, double locZ, float qx, float qy, float qz, float qw, boolean resetSleepTimer);
    private static native void setRestitution(long bodyVa, float restitution);
    private static native void setUserData(long bodyVa, long value);
}
