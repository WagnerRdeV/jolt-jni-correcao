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

/*
 * Author: Stephen Gold
 */
#include "Jolt/Jolt.h"
#include "Jolt/Skeleton/Skeleton.h"

#include "auto/com_github_stephengold_joltjni_Skeleton.h"
#include "auto/com_github_stephengold_joltjni_SkeletonRef.h"
#include "glue/glue.h"

using namespace JPH;

IMPLEMENT_REF(Skeleton,
  Java_com_github_stephengold_joltjni_SkeletonRef_copy,
  Java_com_github_stephengold_joltjni_SkeletonRef_createEmpty,
  Java_com_github_stephengold_joltjni_SkeletonRef_free,
  Java_com_github_stephengold_joltjni_SkeletonRef_getPtr)

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    calculateParentJointIndices
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Skeleton_calculateParentJointIndices
  (JNIEnv *, jclass, jlong skeletonVa) {
    Skeleton * const pSkeleton = reinterpret_cast<Skeleton *> (skeletonVa);
    pSkeleton->CalculateParentJointIndices();
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    getRefCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_github_stephengold_joltjni_Skeleton_getRefCount
  (JNIEnv *, jclass, jlong skeletonVa) {
    const Skeleton * const pSkeleton = reinterpret_cast<Skeleton *> (skeletonVa);
    const uint32 result = pSkeleton->GetRefCount();
    return result;
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    setEmbedded
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_Skeleton_setEmbedded
  (JNIEnv *, jclass, jlong skeletonVa) {
    Skeleton * const pSkeleton = reinterpret_cast<Skeleton *> (skeletonVa);
    pSkeleton->SetEmbedded();
}

/*
 * Class:     com_github_stephengold_joltjni_Skeleton
 * Method:    toRef
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_Skeleton_toRef
  (JNIEnv *, jclass, jlong skeletonVa) {
    Skeleton * const pSkeleton = reinterpret_cast<Skeleton *> (skeletonVa);
    Ref<Skeleton> * const pResult = new Ref<Skeleton>(pSkeleton);
    TRACE_NEW("Ref<Skeleton>", pResult)
    return reinterpret_cast<jlong> (pResult);
}