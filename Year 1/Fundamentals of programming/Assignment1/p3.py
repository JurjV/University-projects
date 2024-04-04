# Solve the problem from the third set here
# third_set_pb15
def perfect_number(n):
    d = 2
    sum = 1
    while d < n // 2 + 1:
        if n % d == 0:
            sum = sum + d
        d += 1
    if sum == n:
        m = n
        return m
    else:
        return perfect_number(n - 1)


if __name__ == '__main__':
    print("This algorithm searches for the largest perfect number smaller than a given natural number.")
    n = int(input("Choose a natural number:"))
    if n < 6:
        print("The largest perfect number smaller than " + str(n) + " doesn't exist.")
    else:
        aux = n
        n = n - 1
        m = perfect_number(n)
        print("The largest perfect number smaller than " + str(aux) + " is " + str(m) + ".")
