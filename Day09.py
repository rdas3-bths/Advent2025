from shapely import Polygon

input_f = open("data", "r")
data = input_f.read().split("\n")

all_points = []
for line in data:
    x = line.split(",")[0]
    y = line.split(",")[1]
    point = (int(x), int(y))
    all_points.append(point)

full_polygon = Polygon(all_points)

def get_highest_area(part_two):
    all_valid_areas = []

    for i in range(0, len(all_points)):
        for j in range(i+1, len(all_points)):
            point1 = all_points[i]
            point2 = all_points[j]
            min_x = min(point1[0], point2[0])
            min_y = min(point1[1], point2[1])
            max_x = max(point1[0], point2[0])
            max_y = max(point1[1], point2[1])
            vertex_one = (min_x, min_y)
            vertex_two = (max_x, min_y)
            vertex_three = (max_x, max_y)
            vertex_four = (min_x, max_y)
            current_rectangle = Polygon((vertex_one, vertex_two, vertex_three, vertex_four))
            area = (abs(point1[0] - point2[0]) + 1) * (abs(point1[1] - point2[1]) + 1)
            if part_two:
                if full_polygon.contains(current_rectangle):
                    all_valid_areas.append(area)
            else:
                all_valid_areas.append(area)

    all_valid_areas.sort()
    return all_valid_areas[-1]

print("Part one answer:", get_highest_area(False))
print("Part two answer:", get_highest_area(True))