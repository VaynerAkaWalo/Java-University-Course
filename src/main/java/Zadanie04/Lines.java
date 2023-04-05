package Zadanie04;

import java.util.*;

public class Lines implements LinesInterface{

    private final HashMap<Point, Set<Segment>> map = new HashMap<>();
    private final HashSet<Point> nodes = new HashSet<>();


    @Override
    public void addPoints(Set<Point> points) {nodes.addAll(points);}

    @Override
    public void addSegments(Set<Segment> segments) {
        nodes.forEach( (n) -> {map.put(n, new HashSet<>());});
        segments.forEach( (e) -> {
            Point n1 = e.getEndpoint1();
            Point n2 = e.getEndpoint2();

            map.get(n1).add(e);
            map.get(n2).add(e);
        });

    }


    @Override
    public List<Segment> findConnection(Point start, Point end) {
        HashMap<Point, Segment> path = new HashMap<>();

        ArrayList<Segment> result = new ArrayList<>();

        bfs(start, path);

        if(path.containsKey(end))
            calcPath(start, end, path, result);

        return result;
    }

    @Override
    public Map<Point, Set<Segment>> getMapEndpointToSegments() {return map;}

    @Override
    public Map<Point, Map<Integer, Set<Point>>> getReachableEndpoints() {
        Map<Point, Map<Integer, Set<Point>>> result = new HashMap<>();

        for(Point node : nodes) {
            result.put(node, pathsToPoints(buildPathsForNode(node), node));
        }

        return result;
    }


    private void bfs(Point start, HashMap<Point, Segment> path) {
        Set<Point> visited = new HashSet<>();
        LinkedList<Point> queue = new LinkedList<>();
        queue.add(start);

        while(!queue.isEmpty()) {
            Point current = queue.remove();
            visited.add(current);

            for(Segment segment : map.get(current)) {
                Point other = segment.getEndpoint1() == current ? segment.getEndpoint2() : segment.getEndpoint1();
                if(!visited.contains(other) && !queue.contains(other)) {
                    queue.add(other);
                    path.put(other,segment);
                }
            }

        }
    }

    public Map<Integer, Set<ArrayList<Segment>>> buildPathsForNode(Point node) {
        Map<Integer, Set<ArrayList<Segment>>> result = new HashMap<>();

        for (int i = 1; i < 5; i++) {
            result.put(i, new HashSet<>());
        }

        result.get(1).addAll(pathAddSegment(node, new ArrayList<>()));

        for (int i = 2; i < 5; i++) {
            for(ArrayList<Segment> path : result.get(i - 1))
                result.get(i).addAll(pathAddSegment(node, path));
        }


        return result;
    }

    private Map<Integer, Set<Point>> pathsToPoints(Map<Integer, Set<ArrayList<Segment>>> paths, Point start) {
        Map<Integer, Set<Point>> result = new HashMap<>();

        for(int i = 1; i < 5; i++) {
            result.put(i, new HashSet<>());
            for(ArrayList<Segment> path : paths.get(i)) {
                result.get(i).add(lastPoint(start, path));
            }
        }

        return result;
    }


    private Set<ArrayList<Segment>> pathAddSegment(Point start, ArrayList<Segment> path) {
        Set<ArrayList<Segment>> result = new HashSet<>();
        Set<Point> visited = getVisitedPoints(path);
        Point current = lastPoint(start, path);

        for(Segment segment : map.get(current)) {
            Point other = segment.getEndpoint1() == current ? segment.getEndpoint2() : segment.getEndpoint1();
            if(!visited.contains(other)) {
                ArrayList<Segment> newPath = new ArrayList<>(path);
                newPath.add(segment);
                result.add(newPath);
            }
        }
        return result;
    }

    private Point otherPoint(Segment segment, Point node) {
        return segment.getEndpoint1() == node ? segment.getEndpoint2() : segment.getEndpoint1();
    }

    private Point lastPoint(Point start, ArrayList<Segment> path) {
        if(path.size() == 0)
            return start;
        else if (path.size() == 1) {
            return otherPoint(path.get(0), start);
        }
        else {
            Point point = path.get(path.size() - 1).getEndpoint1();
            if(point == path.get(path.size() - 2).getEndpoint1() || point == path.get(path.size() - 2).getEndpoint2())
                return path.get(path.size() - 1).getEndpoint2();
            return point;
        }
    }

    private Set<Point> getVisitedPoints(ArrayList<Segment> path) {
        Set<Point> result = new HashSet<>();

        for(Segment segment : path) {
            result.add(segment.getEndpoint1());
            result.add(segment.getEndpoint2());
        }
        return result;
    }


    private void calcPath(Point start, Point end, HashMap<Point, Segment> path, ArrayList<Segment> result) {
        Point current = end;

        while(current != start) {
            result.add(path.get(current));

            current = path.get(current).getEndpoint1() == current ?
                    path.get(current).getEndpoint2() : path.get(current).getEndpoint1();
        }

        Collections.reverse(result);
    }

}
