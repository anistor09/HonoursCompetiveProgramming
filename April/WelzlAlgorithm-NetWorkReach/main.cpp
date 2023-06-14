#include <algorithm>
#include <assert.h>
#include <iostream>
#include <math.h>
#include <vector>
#include <sstream>
#include<iomanip>

using namespace std;


struct Point {
    double x, y;
};


struct Circle {
    Point centre;
    double radius;
};


double euclideanDistance(const Point& a, const Point& b)
{
    return sqrt(pow(a.x - b.x, 2)
                + pow(a.y - b.y, 2));
    
}

bool inCircle(const Circle& c, const Point& p)
{
    return euclideanDistance(c.centre, p) <= c.radius;
}


Point circleCenter(double Circle1x, double Circle1y,
                   double Circle2x, double Circle2y)
{
    double aux1 = Circle1x * Circle2y - Circle1y * Circle2x;
    
    double aux2 = Circle2x * Circle2x + Circle2y * Circle2y;
    
    double aux3t = Circle1x * Circle1x + Circle1y * Circle1y;
    return {(Circle2y * aux3t - Circle1y * aux2) / (2 * aux1),
            (Circle1x * aux2 - Circle2x * aux3t) / (2 * aux1) };
}

Circle createCircleThree(const Point& point1, const Point& point2,
                         const Point& point3)
{
    Point newPoint = circleCenter(point2.x - point1.x, point2.y - point1.y,
                                  point3.x - point1.x, point3.y - point1.y);


    newPoint.y += point1.y;
    newPoint.x += point1.x;
    return {newPoint, euclideanDistance(newPoint, point1) };
}


Circle createCircleTwo(const Point& point1, const Point& point2)
{
  
    Point newPoint = {(point2.x + point1.x ) / 2.0, (point2.y + point1.y ) / 2.0 };

    return {newPoint, euclideanDistance(point1, point2) / 2.0 };
}


bool circleValidation(const Circle& circle,
                      const vector<Point>& points)
{
    for (const Point& p : points)
        if (!inCircle(circle, p))
            return false;
    return true;
}


Circle basicCircle(vector<Point>& points)
{
 
    if (points.empty()) {
        return { { 0, 0 }, 0 };
    }
    else if (points.size() == 1) {
        return {points[0], 0 };
    }
    else if (points.size() == 2) {
        return createCircleTwo(points[0], points[1]);
    }

  
    for (int i = 0; i < 3; i++) {
        for (int j = i + 1; j < 3; j++) {

            Circle circle = createCircleTwo(points[i], points[j]);
            if (circleValidation(circle, points))
                return circle;
        }
    }
    return createCircleThree(points[0], points[1], points[2]);
}

Circle welzl_helper(vector<Point>& points,
                    vector<Point> res, int nr)
{
 
    if (nr == 0 || res.size() == 3) {
        return basicCircle(res);
    }

  
    int idx = rand() % nr;

    Point p = points[idx];
    
    swap(points[idx], points[nr - 1]);
 
    Circle d = welzl_helper(points, res, nr - 1);

    if (inCircle(d, p)) {
        return d;
    }

    res.push_back(p);

    return welzl_helper(points, res, nr - 1);
}

Circle welzl(const vector<Point>& points)
{
    vector<Point> auxPoints = points;

    random_shuffle(auxPoints.begin(), auxPoints.end());

    return welzl_helper(auxPoints, {}, auxPoints.size());
}
std::string line;
int nr;
std::vector<Point> readInput() {

    std::stringstream stringstream;
    std::vector<Point> points;

    std::getline(std::cin, line);
    stringstream << line;
    stringstream >> nr;

    stringstream.clear();
    int aux = nr;
    double px,py;
    while (aux > 0) {

        std::getline(std::cin, line);
        stringstream << line;
        stringstream >> px;
        stringstream >> py;
        stringstream.clear();
        points.push_back({px,py});

        aux--;
    }

    return points;
}

int main() {
    vector<Point> points = readInput();
    Point centre = welzl(points).centre;
    std::cout << std::fixed << std::setprecision(1) << centre.x << " " << centre.y;
    return 0;
}
