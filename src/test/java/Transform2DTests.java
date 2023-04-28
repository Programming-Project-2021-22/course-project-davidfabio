import org.davidfabio.utils.Transform2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Transform2DTests {
    final float PI = (float)Math.PI;
    float tolerance = 0.00001f;
    final float HALF_PI = PI / 2;
    float startX = 0;
    float startY = 0;
    float distance = 100f;

    @Test
    void testDegreesToRadians() {
        assertEquals(0, Transform2D.degreesToRadians(0f), tolerance);
        assertEquals(PI / 12, Transform2D.degreesToRadians(15f), tolerance);
        assertEquals(PI / 4, Transform2D.degreesToRadians(45f), tolerance);
        assertEquals(PI / 2, Transform2D.degreesToRadians(90f), tolerance);
        assertEquals(PI, Transform2D.degreesToRadians(180f), tolerance);
        assertEquals(PI * 2, Transform2D.degreesToRadians(360f), tolerance);
    }

    @Test
    void testRadiansToDegrees() {
        assertEquals(0, Transform2D.radiansToDegrees(0f), tolerance);
        assertEquals(15f, Transform2D.radiansToDegrees(PI / 12), tolerance);
        assertEquals(45f, Transform2D.radiansToDegrees(PI / 4), tolerance);
        assertEquals(90f, Transform2D.radiansToDegrees(PI / 2), tolerance);
        assertEquals(180f, Transform2D.radiansToDegrees(PI), tolerance);
        assertEquals(360f, Transform2D.radiansToDegrees(PI * 2), tolerance);
    }

    @Test
    void testZeroToHalfPi() {
        for (float angle = 0f; angle < HALF_PI; angle += 0.1f) {
            float x = Transform2D.translateX(startX, angle, distance);
            float y = Transform2D.translateY(startY, angle, distance);

            assertTrue(x >= 0);
            assertTrue(y >= 0);
        }
    }

    @Test
    void testHalfPiToPi() {
        for (float angle = HALF_PI; angle < PI; angle += 0.1f) {
            float x = Transform2D.translateX(startX, angle, distance);
            float y = Transform2D.translateY(startY, angle, distance);

            assertTrue(x <= 0);
            assertTrue(y >= 0);
        }
    }

    @Test
    void testPiToThreeFourthsPi() {
        for (float angle = PI; angle < PI + HALF_PI; angle += 0.1f) {
            float x = Transform2D.translateX(startX, angle, distance);
            float y = Transform2D.translateY(startY, angle, distance);

            assertTrue(x <= 0);
            assertTrue(y <= 0);
        }
    }

    @Test
    void testThreeFourthsPiToTwoPi() {
        for (float angle = PI + HALF_PI; angle < PI * 2; angle += 0.1f) {
            float x = Transform2D.translateX(startX, angle, distance);
            float y = Transform2D.translateY(startY, angle, distance);

            assertTrue(x >= 0);
            assertTrue(y <= 0);
        }
    }
}
