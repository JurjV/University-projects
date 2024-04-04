#
# This is the program's UI module. The user interface and all interaction with the user (print and input statements) are found here
#

from functions import create_participant, set_p1, set_p2, set_p3, \
    sort_list, list_conditions, remove_conditions, top_scores_conditions, clear_scores, get_id, get_p1, get_p2, get_p3


def print_options(commands):
    print("Print a command down bellow:")
    print(*list(commands.keys()), "undo", "exit", sep="\n")


def print_all(all_participants, args):
    if len(args) > 2:
        print("Too many values!")
    else:
        try:
            if len(args) == 0:  # normally print the list
                for participant in all_participants:
                    print(participant)
            elif len(args) == 2:  # printing the list depending on the average
                list_conditions(all_participants, args)
            elif len(args) == 1 and args[0] == "sorted":  # printing the list sorted
                all_participants_sorted = sort_list(all_participants, 4)
                print("The participants sorted in decreasing order are:")
                for participant in all_participants_sorted:
                    print(participant)
            else:
                print("This option is not implemented yet,", args[0])
        except ValueError as ve:
            print("Invalid input,", ve)


def read_command():
    # reading the command and
    # putting the written command in a list and getting rid of extra spaces
    command = input(">")
    pos = command.find(" ")

    if pos == -1:
        return command, []

    cmd = command[:pos]
    args = command[pos + 1:]

    args = args.split()
    args = [s.strip() for s in args]

    return cmd, args


def add_participant(all_participants, args):
    if len(args) > 3:
        print("Invalid input!")
    else:
        try:
            id = int(len(all_participants))
            P1 = int(args[0])
            P2 = int(args[1])
            P3 = int(args[2])

            new_participant = create_participant(id, P1, P2, P3)
            all_participants.append(new_participant)

        except ValueError as ve:
            print("Invalid input", ve)
        except IndexError as ie:
            print("Invalid input", ie)


def insert_participant(all_participants, args):
    if len(args) > 5:
        print("Invalid input")
    else:
        try:
            P1 = int(args[0])
            P2 = int(args[1])
            P3 = int(args[2])
            id = int(args[4])

            if args[3] == 'at':
                if id <= len(all_participants) - 1:

                    new_participant = create_participant(id, P1, P2, P3)
                    all_participants[id] = new_participant

                else:
                    print("Invalid input!")
            else:
                print(f"Invalid input:", args[3], "instead of at")
        except ValueError as ve:
            print("Invalid input", ve)
        except IndexError as ie:
            print("Invalid input", ie)


def remove_scores(all_participants, args):
    if len(args) > 3:
        print("Invalid input!")
    else:
        try:
            if len(args) == 3:  # clearing the score between two ids including them
                try:

                    if args[1] == "to":
                        id = int(args[0])
                        id_fin = int(args[2])

                        if max(id, id_fin) == id:
                            id, id_fin = id_fin, id

                        for i in range(id, id_fin + 1):
                            clear_scores(all_participants, i)

                    else:
                        print(f"Invalid input:", args[1], "instead of to")
                except IndexError as ie:
                    print("Invalid input,", ie)
            elif len(args) == 1:  # clearing the score from an id
                try:

                    i = int(args[0])
                    clear_scores(all_participants, i)

                except IndexError as ie:
                    print("Invalid input,", ie)

            elif len(args) == 2:  # removing the score depending on some conditions
                try:

                    remove_conditions(all_participants, args)

                except IndexError as ie:
                    print("Invalid input,", ie)

        except ValueError as ve:
            print("Invalid input,", ve)


def replace_scores(all_participants, args):
    if len(args) > 4 or len(args) < 4:
        print("Invalid input!")
    else:
        try:
            if len(args) == 4:
                if args[2] == "with":
                    id = int(args[0])
                    if args[1] == 'P1':
                        set_p1(all_participants[id], int(args[3]))

                    elif args[1] == 'P2':
                        set_p2(all_participants[id], int(args[3]))

                    elif args[1] == 'P3':
                        set_p3(all_participants[id], int(args[3]))

                    else:
                        print("Input an existing value!")

                else:
                    print(f"Invalid input:", args[2], "instead of with.")

        except IndexError as ie:
            print("Invalid input,", ie)


def top_scores(all_participants, args):
    if len(args) > 2 or len(args) == 0:
        print("Invalid input!")
    else:
        try:
            if len(args) == 1:
                aux = int(args[0])
                all_participants_sorted = sort_list(all_participants, 4)
                for i in range(0, aux):
                    print(all_participants_sorted[i])
            elif len(args) == 2:
                top_scores_conditions(all_participants, args)
        except ValueError as ve:
            print("Invalid input,", ve)
        except IndexError as ie:
            print("Invalid input,", ie)


def undo_operation(undo_list, all_participants):
    if len(undo_list) > 1:
        undo_list.pop(-1)
        all_participants = undo_list[len(undo_list) - 1]
        return all_participants
    else:
        print("Cannot undo any more!")
        return all_participants


def setup():
    all_participants = [create_participant(0, 10, 20, 30), create_participant(1, 15, 25, 35),
                        create_participant(2, 17, 27, 37), create_participant(3, 12, 22, 32)]
    return all_participants


def test_add_participant():
    all_participants = setup()
    add_participant(all_participants, ["20", "30", "35"])
    assert (len(all_participants) == 5)
    assert (get_id(all_participants[4]) == 4)
    assert (get_p1(all_participants[4]) == 20)
    assert (get_p2(all_participants[4]) == 30)
    assert (get_p3(all_participants[4]) == 35)


def test_insert_participant():
    all_participants = setup()
    insert_participant(all_participants, ["10", "10", "10", "at", "2"])
    assert (len(all_participants) == 4)
    assert (get_p1(all_participants[2]) == 10)
    assert (get_p2(all_participants[2]) == 10)
    assert (get_p3(all_participants[2]) == 10)


def test_remove_scores():
    all_participants = setup()
    remove_scores(all_participants, ["3"])
    assert (get_p1(all_participants[3]) == 0)
    assert (get_p2(all_participants[3]) == 0)
    assert (get_p3(all_participants[3]) == 0)
    all_participants = setup()
    remove_scores(all_participants, ["1", "to", "3"])
    for i in range(1, 4):
        assert (get_p1(all_participants[i]) == 0)
        assert (get_p2(all_participants[i]) == 0)
        assert (get_p3(all_participants[i]) == 0)


def test_replace_scores():
    all_participants = setup()
    replace_scores(all_participants, ["3", "P3", "with", "4"])
    replace_scores(all_participants, ["2", "P2", "with", "4"])
    replace_scores(all_participants, ["1", "P1", "with", "4"])
    assert (get_p1(all_participants[1]) == 4)
    assert (get_p2(all_participants[2]) == 4)
    assert (get_p3(all_participants[3]) == 4)


def test_all():  # test if the functions work
    test_add_participant()
    test_insert_participant()
    test_remove_scores()
    test_replace_scores()
