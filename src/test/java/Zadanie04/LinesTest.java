package Zadanie04;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LinesTest {

    private LinesInterface lines;

    static private Set<LinesInterface.Point> points;
    static private Set<LinesInterface.Segment> segments;

    static private final LinesInterface.Point a = new TestPoint("A");
    static private final LinesInterface.Point b = new TestPoint("B");
    static private final LinesInterface.Point c = new TestPoint("C");
    static private final LinesInterface.Point d = new TestPoint("D");
    static private final LinesInterface.Point e = new TestPoint("E");
    static private final LinesInterface.Point f = new TestPoint("F");
    static private final LinesInterface.Point g = new TestPoint("G");

    @BeforeAll
    static void config() {
        points = Set.of(
                a,
                b,
                c,
                d,
                e,
                f,
                g
        );


        segments = Set.of(
                new TestSegment(a, b),
                new TestSegment(b, c),
                new TestSegment(a, c),
                new TestSegment(c, d),
                new TestSegment(d, e),
                new TestSegment(f, g)
        );


    }

    @BeforeEach
    void setUp() {
        lines = new Lines();
        lines.addPoints(points);
        lines.addSegments(segments);
    }

    @Test
    void getMapEndpointsTest() {
        Map<LinesInterface.Point, Set<LinesInterface.Segment>> expected = new HashMap<>();

        points.forEach( node -> expected.put(node, new HashSet<>()));

        for (LinesInterface.Segment segment : segments) {
            LinesInterface.Point endpoint1 = segment.getEndpoint1();
            LinesInterface.Point endpoint2 = segment.getEndpoint2();

            expected.get(endpoint1).add(segment);
            expected.get(endpoint2).add(segment);
        }

        Map<LinesInterface.Point, Set<LinesInterface.Segment>> actual = lines.getMapEndpointToSegments();

        assertEquals(expected, actual);
    }

}

class TestPoint implements LinesInterface.Point {
    private final String name;

    public TestPoint(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

class TestSegment implements LinesInterface.Segment {
    private final LinesInterface.Point point1;
    private final LinesInterface.Point point2;

    public TestSegment(LinesInterface.Point point1, LinesInterface.Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    @Override
    public LinesInterface.Point getEndpoint1() {
        return point1;
    }

    @Override
    public LinesInterface.Point getEndpoint2() {
        return point2;
    }


}