import random


def gnome_sort(l):
    i = 0
    while i < len(l):
        if i == 0 or l[i] >= l[i - 1]:
            i += 1
        else:
            l[i - 1], l[i] = l[i], l[i - 1]
            i -= 1


def selection_sort(l, n):
    for j in range(n):
        imin = j
        for i in range(j + 1, n):
            if l[i] < l[imin]:
                imin = i
        if imin != j:
            l[j], l[imin] = l[imin], l[j]


def generate_list(n):
    l = [None] * n
    for i in range(n):
        l[i] = random.randint(0, 100)
    return l


def generate_worst_case_list(n):
    l = [None] * n
    for i in range(n):
        l[i] = n - i
    return l


def generate_best_case_list(n):
    l = [None] * n
    for i in range(n):
        l[i] = i + 1
    return l


def generate_average_case_list(n):
    l = [None] * n
    for i in range(n):
        if i < n // 2:
            l[i] = i + 1
        else:
            l[i] = n // 2 + n - i
    return l


def timeing_selection_sort(l, n):
    import time
    start1 = time.time()
    selection_sort(l, n)
    stop1 = time.time()
    time1 = stop1 - start1  # the time that it takes for selection sort to sort the list
    return time1


def timeing_gnome_sort(l, n):
    import time
    start2 = time.time()
    gnome_sort(l)
    stop2 = time.time()
    time2 = stop2 - start2  # the time that it takes for gnome sort to sort the list
    return time2


def worst_case_timer(n):
    l = generate_worst_case_list(n)
    time1 = timeing_selection_sort(l, n)
    l = generate_worst_case_list(n)
    time2 = timeing_gnome_sort(l, n)
    print("For a list of " + str(n) + " elements, in the worst case scenario,selection sort compiles in " + str(
        time1) + " seconds and gnome sort compiles in " + str(time2) + " seconds")


def best_case_timer(n):
    l = generate_best_case_list(n)
    time1 = timeing_selection_sort(l, n)
    time2 = timeing_gnome_sort(l, n)
    print("For a list of " + str(n) + " elements, in the best case scenario,selection sort compiles in " + str(
        time1) + " seconds and gnome sort compiles in " + str(time2) + " seconds")


def average_case_timer(n):
    l = generate_average_case_list(n)
    time1 = timeing_selection_sort(l, n)
    l = generate_average_case_list(n)
    time2 = timeing_gnome_sort(l, n)
    print("For a list of " + str(n) + " elements, in the averagec case scenario,selection sort compiles in " + str(
        time1) + " seconds and gnome sort compiles in " + str(time2) + " seconds")


def show_options():
    print("1.Generate a random list")
    print("2.Sort the list using the Selection Sort")
    print("3.Sort the list using the Gnome Sort")
    print("4.Show the best case complexity of the algorithms")
    print("5.Show the average case complexity of the algorithms")
    print("6.Show the worst case complexity of the algorithms")
    print("x.Exit")


def menu():
    n = input("Choose the length of the list:")
    n = int(n)
    l = [0] * n
    aux = n
    while True:
        n = aux
        show_options()
        opt = input("Choose an option:")
        if opt == "x":
            break
        opt = int(opt)
        if opt == 1:
            l = generate_list(n)
            print(l)
        if opt == 2:
            selection_sort(l, n)
            print(l)
        if opt == 3:
            gnome_sort(l)
            print(l)
        if opt == 4:
            for k in range(1, 6):
                best_case_timer(n)
                n = n * 2
        if opt == 5:
            for k in range(1, 6):
                average_case_timer(n)
                n = n * 2
        if opt == 6:
            for k in range(1, 6):
                worst_case_timer(n)
                n = n * 2


menu()
