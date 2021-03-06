/* Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tests.api.java.nio.charset;

import dalvik.annotation.TestTargetClass;
import dalvik.annotation.TestTargets;
import dalvik.annotation.TestTargetNew;
import dalvik.annotation.TestLevel;

import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

@TestTargetClass(java.nio.charset.CharsetEncoder.class)
/**
 * test case specific activity of gb18030 charset encoder
 */
public class GBCharsetEncoderTest extends AbstractCharsetEncoderTestCase {

    /*
     * @see CharsetEncoderTest#setUp()
     */
    protected void setUp() throws Exception {
        // charset for gb180303
        cs = Charset.forName("gb18030");;
        super.setUp();
    }

    /*
     * @see CharsetEncoderTest#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @TestTargetNew(
        level = TestLevel.PARTIAL,
        notes = "IllegalStateException checking missed.",
        method = "canEncode",
        args = {char.class}
    )
    public void testCanEncodechar() throws CharacterCodingException {
        // normal case for utfCS
        assertTrue(encoder.canEncode('\u0077'));
        assertTrue(encoder.canEncode('\uc2a3'));

        // for non-mapped char
        assertTrue(encoder.canEncode('\uc2c0'));
    }

    /*
     * Class under test for boolean canEncode(CharSequence)
     */
    @TestTargetNew(
        level = TestLevel.PARTIAL,
        notes = "IllegalStateException checking missed.",
        method = "canEncode",
        args = {java.lang.CharSequence.class}
    )
    public void testCanEncodeCharSequence() {
        assertTrue(encoder.canEncode(""));
        // surrogate char

        // valid surrogate pair
        assertTrue(encoder.canEncode("\ud800\udc00"));
        // invalid surrogate pair
        assertFalse(encoder.canEncode("\ud800\udb00"));
        assertFalse(encoder.canEncode("\ud800"));
    }

    @TestTargets({
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "",
            method = "averageBytesPerChar",
            args = {}
        ),
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "",
            method = "maxBytesPerChar",
            args = {}
        )
    })
    public void testSpecificDefaultValue() {
        // FIXME: different here!
        assertEquals(4.0, encoder.maxBytesPerChar(), 0.0);
        assertEquals(2.5, encoder.averageBytesPerChar(), 0.0);

        // assertTrue(encoder.averageBytesPerChar() == 3);
        // assertTrue(encoder.maxBytesPerChar() == 2);

    }

    CharBuffer getMalformedCharBuffer() {
        return CharBuffer.wrap("\ud800 buffer");
    }

    CharBuffer getUnmapCharBuffer() {
        return null;
    }

    CharBuffer getExceptionCharBuffer() {
        return null;
    }

    protected byte[] getIllegalByteArray() {
        return new byte[] { (byte) 0xd8, (byte) 0x00 };
    }

}
