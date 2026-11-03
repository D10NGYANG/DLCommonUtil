import com.d10ng.common.base.*;
import com.d10ng.common.calculate.ColorUtilsKt;
import com.d10ng.common.coordinate.*;
import com.d10ng.common.transform.CharsetUtils_jvmKt;
import org.junit.Test;
import static org.junit.Assert.*;

public class DocumentationExamplesTest {
    @Test
    public void currentJavaEntryPointsAndParameterTypes() {
        assertEquals("3.14", DoubleUtilsKt.toStringWithMaxDecimal(3.1415926, 2));
        assertEquals("3.14", FloatUtilsKt.toStringWithMaxDecimal(3.1415926f, 2));
        assertEquals("#a290b5", ColorUtilsKt.getNextLevelColor("#45216B", 0.5));
        assertEquals("#a290b5", ColorUtilsKt.getMiddleColor("#45216B", "#FFFFFF", 0.5f));
        Integer[] rgb = ColorUtilsKt.getRgbValueArrayFromHexColorString("#45216B");
        assertEquals("#45216b", ColorUtilsKt.getHexColorStringFromRgbValueArray(rgb));
        assertArrayEquals(new byte[]{(byte) 0xf1, (byte) 0xff}, IntUtilsKt.toByteArray(0xF1FF, null));
        assertArrayEquals(new byte[]{(byte) 0xf1, (byte) 0xff}, LongUtilsKt.toByteArray(0xF1FFL, null));
        assertArrayEquals(new byte[]{0x34}, ShortUtilsKt.toByteArray((short) 0x1234, 1));
        assertEquals(0x1234L, LongUtilsKt.toLong(new byte[]{0x12, 0x34}));
        assertEquals("测试", CharsetUtils_jvmKt.decodeGBK(CharsetUtils_jvmKt.encodeGBK("测试")));
        java.nio.ByteBuffer nio = java.nio.ByteBuffer.wrap(new byte[]{0x53});
        assertArrayEquals(new byte[]{0x09}, ByteBufferUtils_jvmKt.getBitRange(nio, 3, 4));
        Coordinate[] line = {new Coordinate(0, 0), new Coordinate(0, 0.001)};
        assertTrue(TrackUtilsKt.getTotalDistance(line) > 100);
        Integer[] parts = TrackUtilsKt.getNearPartsOnLine(line[0], line, 10f);
        assertArrayEquals(new Integer[]{0}, parts);
    }
}
