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
import java.util.logging.Logger;

/**
 * A customizable {@code CharacterContactListener}.
 *
 * @author Stephen Gold sgold@sonic.net
 */
public class CustomCharacterContactListener
        extends JoltPhysicsObject
        implements CharacterContactListener {
        private static final Logger logger = Logger.getLogger(CustomCharacterContactListener.class.getName());
    // *************************************************************************
    // constructors

    /**
     * Instantiate a customizable listener.
     */
    public CustomCharacterContactListener() {
        long listenerVa = createDefault();
        setVirtualAddress(listenerVa, () -> free(listenerVa));
    }
    // *************************************************************************
    // CharacterContactListener methods

    /**
     * Callback invoked (by native code) to adjust the velocity of the specified
     * body as seen by the specified character.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} (not zero)
     * @param body2Va the virtual address of the {@code ConstBody} (not zero)
     * @param velocities the components of the linear and angular velocities
     * (length&ge;6, may be modified)
     */
    @Override
    public void onAdjustBodyVelocity(
            long characterVa, long body2Va, float[] velocities) {
    }

    /**
     * Callback invoked (by native code) whenever 2 characters collide.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param otherCharacterVa the virtual address of the other
     * {@code ConstCharacterVirtual} (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is being hit (not zero)
     * @param contactLocationX the X component of the contact location (in
     * system coordinates)
     * @param contactLocationY the Y component of the contact location (in
     * system coordinates)
     * @param contactLocationZ the Z component of the contact location (in
     * system coordinates)
     * @param contactNormalX the X component of the contact normal (in system
     * coordinates)
     * @param contactNormalY the Y component of the contact normal (in system
     * coordinates)
     * @param contactNormalZ the Z component of the contact normal (in system
     * coordinates)
     * @param settingsVa the virtual address of the
     * {@code CharacterContactSettings} for storing the desired behavior
     */
    @Override
    public void onCharacterContactAdded(long characterVa, long otherCharacterVa,
            long subShapeId2Va, double contactLocationX,
            double contactLocationY, double contactLocationZ,
            float contactNormalX, float contactNormalY, float contactNormalZ,
            long settingsVa) {
    }

    /**
     * Callback invoked (by native code) whenever a character-versus-character
     * contact is being solved.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param otherCharacterVa the virtual address of the other
     * {@code ConstCharacterVirtual} (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is being hit (not zero)
     * @param contactLocationX the X component of the contact location (in
     * system coordinates)
     * @param contactLocationY the Y component of the contact location (in
     * system coordinates)
     * @param contactLocationZ the Z component of the contact location (in
     * system coordinates)
     * @param contactNormalX the X component of the contact normal (in system
     * coordinates)
     * @param contactNormalY the Y component of the contact normal (in system
     * coordinates)
     * @param contactNormalZ the Z component of the contact normal (in system
     * coordinates)
     * @param contactVelocityX the X component of the velocity of the contact
     * point (meters per second in system coordinates)
     * @param contactVelocityY the Y component of the velocity of the contact
     * point (meters per second in system coordinates)
     * @param contactVelocityZ the Z component of the velocity of the contact
     * point (meters per second in system coordinates)
     * @param materialVa the virtual address of the {@code ConstPhysicsMaterial}
     * at the contact point (not zero)
     * @param characterVelocityX the X component of the character's prior
     * velocity (in system coordinates)
     * @param characterVelocityY the Y component of the character's prior
     * velocity (in system coordinates)
     * @param characterVelocityZ the Z component of the character's prior
     * velocity (in system coordinates)
     * @param newCharacterVelocity storage for the new velocity vector (in
     * system coordinates, length&ge;3)
     */
    @Override
    public void onCharacterContactSolve(long characterVa, long otherCharacterVa,
            long subShapeId2Va, double contactLocationX,
            double contactLocationY, double contactLocationZ,
            float contactNormalX, float contactNormalY, float contactNormalZ,
            float contactVelocityX, float contactVelocityY,
            float contactVelocityZ, long materialVa, float characterVelocityX,
            float characterVelocityY, float characterVelocityZ,
            float[] newCharacterVelocity) {
            logger.info("onCharacterContactSolve called with characterVa: " + characterVa + ", otherCharacterVa: " + otherCharacterVa);
    }

    /**
     * Callback invoked (by native code) to test whether the specified
     * characters can collide.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param otherCharacterVa the virtual address of the other
     * {@code ConstCharacterVirtual} (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is being hit (not zero)
     * @return {@code true} if the contact is valid, otherwise {@code false}
     */
    @Override
    public boolean onCharacterContactValidate(
            long characterVa, long otherCharacterVa, long subShapeId2Va) {
        return true;
    }

    /**
     * Callback invoked (by native code) whenever a character collides with a
     * body.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param bodyId2Va the virtual address of the {@code ConstBody} being
     * solved (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is being hit (not zero)
     * @param contactLocationX the X component of the contact location (in
     * system coordinates)
     * @param contactLocationY the Y component of the contact location (in
     * system coordinates)
     * @param contactLocationZ the Z component of the contact location (in
     * system coordinates)
     * @param contactNormalX the X component of the contact normal (in system
     * coordinates)
     * @param contactNormalY the Y component of the contact normal (in system
     * coordinates)
     * @param contactNormalZ the Z component of the contact normal (in system
     * coordinates)
     * @param settingsVa the virtual address of the
     * {@code CharacterContactSettings} for storing the desired behavior
     */
    @Override
    public void onContactAdded(long characterVa, long bodyId2Va,
            long subShapeId2Va, double contactLocationX,
            double contactLocationY, double contactLocationZ,
            float contactNormalX, float contactNormalY, float contactNormalZ,
            long settingsVa) {
            logger.info("onContactAdded called with characterVa: " + characterVa + ", bodyId2Va: " + bodyId2Va);
    }

    /**
     * Callback invoked (by native code) whenever a character-versus-body
     * contact is being solved.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param bodyId2Va the virtual address of the {@code ConstBody} being
     * solved (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is being hit (not zero)
     * @param contactLocationX the X component of the contact location (in
     * system coordinates)
     * @param contactLocationY the Y component of the contact location (in
     * system coordinates)
     * @param contactLocationZ the Z component of the contact location (in
     * system coordinates)
     * @param contactNormalX the X component of the contact normal (in system
     * coordinates)
     * @param contactNormalY the Y component of the contact normal (in system
     * coordinates)
     * @param contactNormalZ the Z component of the contact normal (in system
     * coordinates)
     * @param contactVelocityX the X component of the velocity of the contact
     * point (meters per second in system coordinates)
     * @param contactVelocityY the Y component of the velocity of the contact
     * point (meters per second in system coordinates)
     * @param contactVelocityZ the Z component of the velocity of the contact
     * point (meters per second in system coordinates)
     * @param materialVa the virtual address of the {@code ConstPhysicsMaterial}
     * at the contact point (not zero)
     * @param characterVelocityX the X component of the character's prior
     * velocity (meters per second in system coordinates)
     * @param characterVelocityY the Y component of the character's prior
     * velocity (meters per second in system coordinates)
     * @param characterVelocityZ the Z component of the character's prior
     * velocity (meters per second in system coordinates)
     * @param newCharacterVelocity storage for the new velocity vector (in
     * system coordinates, length&ge;3)
     */
    @Override
    public void onContactSolve(long characterVa, long bodyId2Va,
            long subShapeId2Va, double contactLocationX,
            double contactLocationY, double contactLocationZ,
            float contactNormalX, float contactNormalY, float contactNormalZ,
            float contactVelocityX, float contactVelocityY,
            float contactVelocityZ, long materialVa, float characterVelocityX,
            float characterVelocityY, float characterVelocityZ,
            float[] newCharacterVelocity) {
    }

    /**
     * Callback invoked (by native code) to test whether the specified character
     * can collide with the specified body.
     *
     * @param characterVa the virtual address of the
     * {@code ConstCharacterVirtual} being solved (not zero)
     * @param bodyId2Va the virtual address of the {@code ConstBody} being
     * solved (not zero)
     * @param subShapeId2Va the virtual address of the {@code ConstSubShapeId}
     * of the shape that is being hit (not zero)
     * @return {@code true} if the contact is valid, otherwise {@code false}
     */
    @Override
    public boolean onContactValidate(
            long characterVa, long bodyId2Va, long subShapeId2Va) {
        return true;
    }
    // *************************************************************************
    // native private methods

    native private long createDefault();

    native private void free(long listenerVa);
}
