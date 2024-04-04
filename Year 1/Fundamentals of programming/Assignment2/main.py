import random


def gnome_sort(l):
    i = 0
    step = input("Choose a step:")
    totals = 0
    while i < len(l):
        if i == 0 or l[i] >= l[i - 1]:
            i += 1
        else:
            l[i - 1], l[i] = l[i], l[i - 1]
            i -= 1
            totals += 1
            if totals % int(step) == 0:
                print(l)
    print(l)


def selection_sort(l, n):
    step = input("Choose a step:")
    totals = 0
    for j in range(n):
        imin = j
        for i in range(j + 1, n):
            if l[i] < l[imin]:
                imin = i
        if imin != j:
            l[j], l[imin] = l[imin], l[j]
            totals += 1
            if totals % int(step) == 0:
                print(l)
    print(l)


def generate_list(n):
    l = [None] * n
    for i in range(n):
        l[i] = random.randint(0, 100)
    return l


def show_options():
    print("1.Generate a random list")
    print("2.Sort the list using the Selection Sort")
    print("3.Sort the list using the Gnome Sort")
    print("x.Exit")


def menu():
    n = input("Choose the length of the list:")
    n = int(n)
    l = [0] * n
    while True:
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
        if opt == 3:
            gnome_sort(l)


menu()
