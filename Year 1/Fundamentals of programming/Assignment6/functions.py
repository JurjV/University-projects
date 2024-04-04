#
# The program's functions are implemented here. There is no user interaction in this file, therefore no input/print statements. Functions here
# communicate via function parameters, the return statement and raising of exceptions.
#

import random


def create_participant(id, P1, P2, P3):
    return {
        'id': id,
        'P1': P1,
        'P2': P2,
        'P3': P3,

    }


def initialize_ten_participants(all_participants):  # randomly initialize 10 participants
    for i in range(0, 10):
        a = random.randint(0, 100)
        b = random.randint(0, 100)
        c = random.randint(0, 100)
        new_participant = create_participant(i, a, b, c)
        all_participants.append(new_participant)
    return all_participants


def get_id(participant):
    return participant["id"]


def set_id(participant, new_id):
    participant["id"] = new_id


def get_p1(participant):
    return participant["P1"]


def set_p1(participant, new_p1):
    participant["P1"] = new_p1


def get_p2(participant):
    return participant["P2"]


def set_p2(participant, new_p2):
    participant["P2"] = new_p2


def get_p3(participant):
    return participant["P3"]


def set_p3(participant, new_p3):
    participant["P3"] = new_p3


def average_score(participant):  # average score of one participant
    p1 = int(get_p1(participant))
    p2 = int(get_p2(participant))
    p3 = int(get_p3(participant))
    average = (p1 + p2 + p3) // 3
    return average


def sort_list(all_participants, aux):
    if aux == 4:  # sorting the list depending on the average
        for i in range(0, len(all_participants)):
            max_id = i
            for j in range(i + 1, len(all_participants)):
                if average_score(all_participants[max_id]) < average_score(all_participants[j]):
                    max_id = j
            all_participants[i], all_participants[max_id] = all_participants[max_id], all_participants[i]
    elif aux == 1:  # sorting the list depending on P1
        for i in range(0, len(all_participants)):
            max_id = i
            for j in range(i + 1, len(all_participants)):
                if int(get_p1(all_participants[max_id])) < int(get_p1(all_participants[j])):
                    max_id = j
            all_participants[i], all_participants[max_id] = all_participants[max_id], all_participants[i]
    elif aux == 2:  # sorting the list depending on P2
        for i in range(0, len(all_participants)):
            max_id = i
            for j in range(i + 1, len(all_participants)):
                if int(get_p2(all_participants[max_id])) < (get_p2(all_participants[j])):
                    max_id = j
            all_participants[i], all_participants[max_id] = all_participants[max_id], all_participants[i]
    elif aux == 3:  # sorting the list depending on P3
        for i in range(0, len(all_participants)):
            max_id = i
            for j in range(i + 1, len(all_participants)):
                if int(get_p3(all_participants[max_id])) < (get_p3(all_participants[j])):
                    max_id = j
            all_participants[i], all_participants[max_id] = all_participants[max_id], all_participants[i]
    return all_participants


def list_conditions(all_participants, args):
    if args[0] == "<":  # print all averages smaller then a given nr
        for i in all_participants:
            avg = average_score(i)
            if avg < int(args[1]):
                print(i)
    elif args[0] == ">":  # print all averages greater then a given nr
        for i in all_participants:
            avg = average_score(i)
            if avg > int(args[1]):
                print(i)
    elif args[0] == "=":  # print all averages equal to a given nr
        for i in all_participants:
            avg = average_score(i)
            if avg == int(args[1]):
                print(i)
    else:
        print("Invalid input,", args[0])


def remove_conditions(all_participants, args):
    if args[0] == '<':  # remove all scores if average is smaller than a given nr
        for i in all_participants:
            avg = average_score(i)
            if avg < int(args[1]):
                set_p1(i, 0)
                set_p2(i, 0)
                set_p3(i, 0)
    elif args[0] == '>':  # remove all scores if average is greater than a given nr
        for i in all_participants:
            avg = average_score(i)
            if avg > int(args[1]):
                set_p1(i, 0)
                set_p2(i, 0)
                set_p3(i, 0)
    else:
        print("Invalid input,", args[0])


def top_scores_conditions(all_participants, args):
    aux = int(args[0])
    if args[1] == "P1":  # printing the top scorers at P1
        all_participants_sorted = sort_list(all_participants, 1)
        for i in range(0, aux):
            print(all_participants_sorted[i])
    elif args[1] == "P2":  # printing the top scorers at P2
        all_participants_sorted = sort_list(all_participants, 2)
        for i in range(0, aux):
            print(all_participants_sorted[i])
    elif args[1] == "P3":  # printing the top scorers at P3
        all_participants_sorted = sort_list(all_participants, 3)
        for i in range(0, aux):
            print(all_participants_sorted[i])
    else:
        print("Invalid input,", args[1])


def clear_scores(all_participants, i):  # initializes the scores of a participant to 0
    set_p1(all_participants[i], 0)
    set_p2(all_participants[i], 0)
    set_p3(all_participants[i], 0)
