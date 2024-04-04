"""
5.Maximize the profit when selling a rod of length n.
The rod can be cut into pieces of integer lengths and pieces can be sold individually.
The prices are known for each possible length. For example,
if rod length n = 7, and the price array is price = [1, 5, 8, 9, 10, 17, 17]
(the price of a piece of length 3 is 8), the maximum profit is 18,
and is obtained by cutting the rod into 3 pieces, two of length two and one of length 3.
Display the profit and the length of rod sections sold to obtain it.
"""


def maximize_profit_dynamic(price, n):
    max_prf = [0] * (n + 1)
    for i in range(1, n + 1):
        # divide the rod of length i into two rods
        # of length j and i-j and take maximum profit
        for j in range(1, i + 1):
            max_prf[i] = max(max_prf[i], price[j - 1] + max_prf[i - j])
    return max_prf[n]


def maximize_profit_naive(price, n):
    if n == 0:
        return 0
    max_prf = 0
    for i in range(1, n + 1):
        # divide the rod in i and n-i
        # recur for the n-i part
        sum = price[i - 1] + maximize_profit_naive(price, n - i)
        # take the maximum of all parts
        if sum > max_prf:
            max_prf = sum
    return max_prf


def show_options():
    print("What method would you like to be used for solving the problem?")
    print("1.Naive method")
    print("2.Dynamic programing method")


def menu():
    n = int(input("Input the length that you would like your rod to have: "))
    price = []
    print("Please input the prices for each length here:")
    for i in range(0, n):
        aux = int(input())
        price.append(aux)
    show_options()
    while True:
        opt = int(input())
        if opt == 1:
            print("The maximum profit that can be obtained is " + str(maximize_profit_naive(price, n)))
            break
        elif opt == 2:
            print("The maximum profit that can be obtained is " + str(maximize_profit_dynamic(price, n)))
            break
        else:
            print("Please input a valid option!")


menu()
