"""
1.A number of n coins are given, with values of a1, ..., an and a value s.
 Display all payment modalities for the sum s.
 If no payment modality exists print a message.
"""
import random


def show_options1():
    print("Do you want to initialise the value of the coins yourself?")
    print("1.Yes")
    print("2.No")


def show_options2():
    print("Choose an option")
    print("1.Solve the problem using the recursive solution")
    print("2.Solve the problem using the iterative solution")
    print("3.Re-initialise the values of the coins manually")
    print("4.Re-initialise the values of the coins randomly")
    print("0.Exit")


def the_sum(payment):  # Calculates the sum of the current coin values in "payment"
    sum1 = 0
    for i in range(0, len(payment)):
        sum1 = sum1 + payment[i]
    return sum1


def recursive_sol(coinv, s, payment, pos):  # Starts the backtracking
    sum = the_sum(payment)
    if sum == s:  # the sum of the current elements is equal to the initial given sum
        print(payment)  # add solution
    for i in range(pos, len(coinv)):
        payment.append(coinv[i])
        recursive_sol(coinv, s, payment, i + 1)
        payment.pop(-1)


def iterative_sol(coinv, s):
    payment = [0] * len(coinv)
    payment_non_bin = []
    condition = 0
    while True:
        # Gets all the binary numbers representable on len(coinv) bits
        i = len(payment) - 1
        while payment[i] == 1:
            payment[i] = 0
            i -= 1
        condition += 1
        payment[i] = 1
        sum = 0
        """
        Idk how to explain so here is an example of what the next loop does:
        If we have:
        payment=[1, 0, 0, 0, 1]
                     and
        coinv  =[5, 2, 1, 3 ,1]
        then:
        sum= 5 + 1= 6
        payment_non_binary=[5, 1]
        """
        for j in range(0, len(payment)):
            if payment[j] == 1:
                sum = sum + coinv[j]
                payment_non_bin.append(coinv[j])
        if sum == s:  # Checks if the sum from above is equal to the initial given sum
            print(payment_non_bin)  # Prints the solution
        payment_non_bin.clear()
        if condition == (pow(2, len(payment)) - 1):
            break


def menu():
    n = int(input("Input a number of coins: "))
    s = int(input("Input the sum: "))
    coinv = []
    show_options1()
    opt1 = int(input())
    if opt1 == 1:
        print("Please type the values of the coins here: ")
        for i in range(0, n):
            value = int(input())
            coinv.append(value)
    if opt1 == 2:  # Generates the list randomly with values between 1 and n
        for i in range(0, n):
            value = random.randint(1, n)
            coinv.append(value)
        print("The random list: ", coinv)
    while True:
        show_options2()
        opt = int(input())
        if opt == 1:
            payment = []
            pos = 0
            print("Initial list of coin values: ", coinv)
            print("Solutions:")
            recursive_sol(coinv, s, payment, pos)  # Starts the recursive solution with backtracking
        if opt == 2:
            print("Initial list of coin values: ", coinv)
            print("Solutions:")
            iterative_sol(coinv, s)  # Starts the iterative solution
        if opt == 3:
            coinv.clear()
            print("Type the values of the coins here: ")
            for i in range(0, n):
                value = int(input())
                coinv.append(value)
        if opt == 4:
            coinv.clear()
            for i in range(0, n):
                value = random.randint(1, n)
                coinv.append(value)
            print("The random list: ", coinv)
        if opt == 0:
            break


menu()
