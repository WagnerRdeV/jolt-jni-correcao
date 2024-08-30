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
#include <Jolt/Jolt.h>
#include <Jolt/Physics/Collision/Shape/Shape.h>
#include "auto/com_github_stephengold_joltjni_ShapeSettingsRef.h"
#include "glue/glue.h"

using namespace JPH;

/*
 * Class:     com_github_stephengold_joltjni_ShapeSettingsRef
 * Method:    copy
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ShapeSettingsRef_copy
  (JNIEnv *, jclass, jlong refVa) {
    Ref<ShapeSettings> * const pRef = reinterpret_cast<Ref<ShapeSettings> *> (refVa);
    Ref<ShapeSettings> * const pResult = new Ref<ShapeSettings>(*pRef);
    TRACE_NEW("Ref<ShapeSettings>", pResult)
    return reinterpret_cast<jlong> (pResult);
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeSettingsRef
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_github_stephengold_joltjni_ShapeSettingsRef_free
  (JNIEnv *, jclass, jlong refVa) {
    Ref<ShapeSettings> * const pRef = reinterpret_cast<Ref<ShapeSettings> *> (refVa);
    TRACE_DELETE("Ref<ShapeSettings>", pRef)
    delete pRef;
}

/*
 * Class:     com_github_stephengold_joltjni_ShapeSettingsRef
 * Method:    getPtr
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_stephengold_joltjni_ShapeSettingsRef_getPtr
  (JNIEnv *, jclass, jlong refVa) {
    Ref<ShapeSettings> * const pRef
            = reinterpret_cast<Ref<ShapeSettings> *> (refVa);
    const ShapeSettings * const pResult = pRef->GetPtr();
    return reinterpret_cast<jlong> (pResult);
}