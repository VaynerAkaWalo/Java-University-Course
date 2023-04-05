package Zadanie03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MeetingTest {

    MeetingInterface meeting;

    @BeforeEach
    void setUp() {
        meeting = new Meeting();
    }

    @Test
    void singlePawnShouldReachMeetingPoint() {
        List<PawnPosition> pawns = List.of(new PawnPosition2D(1, 4, 4));
        Position meetingPoint = new Position2D(8, 8);

        meeting.addPawns(pawns);
        meeting.addMeetingPoint(meetingPoint);

        meeting.move();

        PawnPosition expected = new PawnPosition2D(1, meetingPoint.x(), meetingPoint.y());

        Optional<PawnPosition> actual = meeting.getAllPawns().stream().findFirst();

        assertEquals(Optional.of(expected), actual);
    }

    @Test
    void secondPawnShouldntReachMeetingPoint() {
        List<PawnPosition> pawns = List.of(
                new PawnPosition2D(1, 4, 4),
                new PawnPosition2D(2, 0, 0)
        );
        Position meetingPoint = new Position2D(8, 8);

        meeting.addPawns(pawns);
        meeting.addMeetingPoint(meetingPoint);

        meeting.move();

        Optional<PawnPosition> firstPawn = meeting.getAllPawns().stream().filter(x -> x.pawnId() == 1).findFirst();
        Optional<PawnPosition> secondPawn = meeting.getAllPawns().stream().filter(x -> x.pawnId() == 2).findFirst();

        if (firstPawn.isPresent() && secondPawn.isPresent()) {
            PawnPosition first = firstPawn.get();
            PawnPosition second = secondPawn.get();
            assertFalse(first.x() == second.x() && first.y() == second.y());
        }
        else {
            fail();
        }
    }

}