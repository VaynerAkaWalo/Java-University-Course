package Zadanie03;

import java.util.*;

public class Meeting implements MeetingInterface{

    List<PawnPosition> pawnPositions = new ArrayList<>();
    Position meetingPoint = null;

    @Override
    public void addPawns(List<PawnPosition> positions) {
        pawnPositions.addAll(positions);
    }

    @Override
    public void addMeetingPoint(Position meetingPointPosition) {
        meetingPoint = meetingPointPosition;
    }

    @Override
    public void move() {
        if(meetingPoint == null)
            return;


        while(turn());

    }

    @Override
    public Set<PawnPosition> getAllPawns() {
        return new HashSet<>(pawnPositions);
    }

    @Override
    public Set<PawnPosition> getNeighbours(int pawnId) {
        HashSet<PawnPosition> result = new HashSet<>();
        PawnPosition pawn = null;

        for(PawnPosition pos : pawnPositions) {
            if(pos.pawnId() == pawnId)
                pawn = pos;
        }

        if(pawn == null)
            return result;

        for(PawnPosition pos : pawnPositions) {
            if(pos.pawnId() != pawnId && (Math.abs(pos.x() - pawn.x()) <= 1 && Math.abs(pos.y() - pawn.y()) <= 1))
                result.add(pos);
        }

        return result;
    }

    private boolean turn()
    {
        boolean moved = false;

        for(int i = 0; i < pawnPositions.size(); i++)
        {
            PawnPosition next = nextMove(pawnPositions.get(i));
            if(next != null)
            {
                moved = true;
                pawnPositions.set(i, next);
            }
        }

        Collections.reverse(pawnPositions);

        return moved;
    }

    private PawnPosition nextMove(PawnPosition pawnPosition)
    {
        int diffX = meetingPoint.x() - pawnPosition.x();
        int diffY = meetingPoint.y() - pawnPosition.y();

        if(diffX == diffY && diffX == 0) {
            return null;
        } else if (Math.abs(diffX) > Math.abs(diffY)) {
            int newX = pawnPosition.x() > meetingPoint.x() ? pawnPosition.x() - 1 : pawnPosition.x() + 1;
            if(checkIfFree(newX, pawnPosition.y()))
                return new PawnPosition2D(pawnPosition.pawnId(), newX, pawnPosition.y());
        }
        else {
            int newY = pawnPosition.y() > meetingPoint.y() ? pawnPosition.y() - 1 : pawnPosition.y() + 1;
            if(checkIfFree(pawnPosition.x(), newY))
                return new PawnPosition2D(pawnPosition.pawnId(), pawnPosition.x(), newY);
        }

        return null;
    }


    private boolean checkIfFree(int x, int y) {
        for(PawnPosition pos : pawnPositions)
        {
            if(pos.x() == x && pos.y() == y)
                return false;
        }
        return true;
    }



}
