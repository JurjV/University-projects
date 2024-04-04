# Solve the problem from the second set here

# second_set_pb10
def palindrome(n):
    aux = n
    m = 0
    while aux != 0:
        c = aux % 10
        m = m * 10 + c
        aux = aux // 10
    return m


if __name__ == '__main__':
    n = int(input("Choose the natural number you want to reverse: "))
    m = palindrome(n)
    print("The palindrome of " + str(n) + " is " + str(m) + ".")
