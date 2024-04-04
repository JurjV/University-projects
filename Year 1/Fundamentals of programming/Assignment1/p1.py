# Solve the problem from the first set here

# first_set_pb4
def largest_number(n):
    v = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    while n > 0:
        c = n % 10
        n = n // 10
        v[c] = v[c] + 1
    p = 1
    num = 0
    for i in range(0, 10):
        while v[i] != 0:
            num = i * p + num
            v[i] = v[i] - 1
            p = p * 10
    return num


if __name__ == '__main__':
    n = int(input("Choose the number that you want to change:"))
    aux = n
    m = largest_number(n)
    print("The largest number made from the digits of number " + str(aux) + " is " + str(m) + " .")
