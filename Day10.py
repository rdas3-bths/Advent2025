from itertools import combinations
import z3

def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    return data


def parse_data():
    file_data = get_file_data("data")

    contents = []

    for line in file_data:
        split_line = line.split(" ")
        for entry in split_line:
            contents.append(entry)

    lights = []
    buttons = []
    cur_buttons = []
    joltages = []
    for entry in contents:

        if entry[0] == '[':
            light = set()
            only_lights = entry[1:len(entry) - 1]
            for index, character in enumerate(only_lights):
                if character == "#":
                    light.add(index)
            lights.append(light)

        elif entry[0] == '(':
            only_buttons = entry[1:len(entry) - 1]
            button = []
            for character in only_buttons.split(","):
                button.append(int(character))
            cur_buttons.append(button)

        elif entry[0] == '{':
            buttons.append(cur_buttons)
            cur_buttons = []
            joltage = []
            only_joltages = entry[1:len(entry) - 1]
            for character in only_joltages.split(","):
                joltage.append(int(character))
            joltages.append(joltage)

    return lights, buttons, cur_buttons, joltages

def do_part_one():

    lights, buttons, cur_buttons, joltages = parse_data()

    part_one_answer = 0
    for ind in range(len(lights)):

        cur_target = lights[ind]
        cur_buttons = buttons[ind]

        found = False
        min_len = 9999999
        for num in range(1, len(cur_buttons)):
            for combo in combinations(cur_buttons, num):
                res = set()

                for button_combo in combo:
                    for button in button_combo:
                        if button in res:
                            res.remove(button)
                        else:
                            res.add(button)

                if cur_target == res:
                    found = True
                    min_len = num
                    break

            if found:
                break

        part_one_answer += min_len

    return part_one_answer


def do_part_two():
    lights, buttons, cur_buttons, joltages = parse_data()

    part_two_answer = 0

    for i in range(len(joltages)):
        current_buttons = buttons[i]
        current_joltage = joltages[i]

        variables = []
        equation = z3.Optimize()
        joltages_var = [0] * len(current_joltage)

        # using an equation solver
        # set up the equation
        for button_number, button in enumerate(current_buttons):
            variable = z3.Int(str(button_number))
            variables.append(variable)

            equation.add(variable >= 0)

            for number in button:
                if joltages_var[number] == 0:
                    joltages_var[number] = variable
                else:
                    joltages_var[number] = joltages_var[number] + variable


        for jolt_number, entry in enumerate(current_joltage):
            if joltages_var[jolt_number] == 0:
                continue
            equation.add(current_joltage[jolt_number] == joltages_var[jolt_number])

        total_presses = equation.minimize(sum(variables))

        if equation.check() == z3.sat:
            part_two_answer += total_presses.value().as_long()

    return part_two_answer


print("Part one answer:", do_part_one())
print("Part two answer:", do_part_two())