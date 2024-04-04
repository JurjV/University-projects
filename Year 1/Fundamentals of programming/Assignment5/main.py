import math


# A.5,B.9

#
# Write the implementation for A5 in this file
#

#
# Write below this comment
# Functions to deal with complex numbers -- list representation
# -> There should be no print or input statements in this section
# -> Each function should do one thing only
# -> Functions communicate using input parameters and their return values
#


def read_ten_random_complex_nrs_list():
    import random
    rl = []
    im = []
    for i in range(0, 10):
        auxrl = random.randint(-30, 30)
        rl.append(auxrl)
        auxim = random.randint(-30, 30)
        im.append(auxim)
        # creates two lists of 10 random numbers
        # rl the list with the real part of the numbers
        # rl the list with the imaginary part of the numbers
    return rl, im


def print_complex_nrs_lists(n, rl, im):
    for i in range(0, n + 10):
        auxrl = rl[i]
        auxim = im[i]
        print_conditions(auxrl, auxim, i)


#
# Write below this comment
# Functions to deal with complex numbers -- dict representation
# -> There should be no print or input statements in this section
# -> Each function should do one thing only
# -> Functions communicate using input parameters and their return values
#

def print_complex_nrs_dict(n, z):
    for i in range(0, n):
        auxrl = int(z[i].real)
        auxim = int(z[i].imag)
        print_conditions(auxrl, auxim, i)


def read_ten_random_complex_nrs_dictionary():
    import random
    z = []
    for i in range(0, 10):
        auxrl = random.randint(-30, 30)
        auxim = random.randint(-30, 30)
        number = complex(auxrl, auxim)
        z.append(number)
    return z


def convert_list_to_dict(rl, im, n):
    # here we convert the complex numbers in lists rl and im
    # to the complex numbers list z
    z = []
    for i in range(0, n + 10):
        auxrl = rl[i]
        auxim = im[i]
        number = complex(auxrl, auxim)
        z.append(number)
    return z


#
# Write below this comment
# Functions that deal with subarray/subsequence properties
# -> There should be no print or input statements in this section
# -> Each function should do one thing only
# -> Functions communicate using input parameters and their return values
#

def make_modulus_list(z):
    mod = []
    # puts all the modulus of the complex numbers from
    # z into the array mod
    for i in z:
        auxrl = int(i.real)
        auxim = int(i.imag)
        modulus = int(math.sqrt(auxrl * auxrl + auxim * auxim))
        mod.append(modulus)
    return mod


def transform_modulus_list(mod):
    mod_bin = []
    # transforms the modulus list into a binary one
    # where if the modulus is in [0,10] then mod_bin[i]=1
    # 0 otherwise
    for i in mod:
        if 0 <= i <= 10:
            mod_bin.append(1)
        else:
            mod_bin.append(0)
    return mod_bin


def max_subarray_positions(mod_bin):
    max_start, max_stop = 0, 0
    start, stop, max1 = -1, -1, 0

    for i in range(len(mod_bin)):
        if mod_bin[i] == 1 and start == -1:
            start = i
        elif mod_bin[i] != 1 and start != -1:
            stop = i - 1
            if stop - start + 1 > max1:
                max_start = start
                max_stop = stop
                max1 = stop - start + 1  # Update max1
                start = -1
        elif mod_bin[i] == 1 and start != -1 and i == len(mod_bin) - 1:
            stop = i
            if stop - start + 1 > max1:
                max_start = start
                max_stop = stop

    return max_start, max_stop


def max_subarray(z):
    mod = make_modulus_list(z)
    mod_bin = transform_modulus_list(mod)
    start, stop = max_subarray_positions(mod_bin)
    arr = []
    for i in range(start, stop + 1):
        arr.append(z[i])
    return arr, mod_bin


def longest_increasing_subsequence(z, arr):
    """

    :param z: complex numbers list
    :param arr: list of the modulus of the complex numbers list
    :return: longest increasing subsequence in complex and modulus
    """
    if not arr:
        return []

    n = len(arr)
    lis = [1] * n

    for i in range(1, n):
        for j in range(0, i):
            if arr[i] > arr[j] and lis[i] < lis[j] + 1:
                lis[i] = lis[j] + 1

    # Find the maximum length and the corresponding index
    max_length = max(lis)
    max_index = lis.index(max_length)

    # Reconstruct the longest increasing subsequence with complex numbers, and their modulus
    longest_subsequence_moduli = [arr[max_index]]
    longest_subsequence_complex = [z[max_index]]
    current_length = max_length - 1

    for i in range(max_index - 1, -1, -1):
        if lis[i] == current_length and arr[i] < arr[max_index]:
            longest_subsequence_moduli.insert(0, arr[i])
            longest_subsequence_complex.insert(0, z[i])
            current_length -= 1

    return longest_subsequence_moduli, longest_subsequence_complex


#
# Write below this comment
# UI section
# Write all functions that have input or print statements here
# Ideally, this section should not contain any calculations relevant to program functionalities
#

def menu():
    show_options_1()
    while True:
        opt1 = input()
        if opt1 == "1":
            # the representation of the complex numbers using two lists for the
            # real and imaginary part of the number
            rl, im = read_ten_random_complex_nrs_list()
            n = int(input("How many complex numbers do you want to input? "))
            rl, im = read_complex_nrs_list(n, rl, im)
            print("The list of the complex numbers is:")
            print_complex_nrs_lists(n, rl, im)
            # here we convert the complex numbers list to a dictionary so that we
            # don't make two separate functions for every exercise
            z = convert_list_to_dict(rl, im, n)
            break
        elif opt1 == "2":
            # the representation of the complex numbers using a dictionary
            z = read_ten_random_complex_nrs_dictionary()
            n = int(input("How many complex numbers do you want to input? "))
            z = read_complex_nrs_dictionary(n, z)
            print_complex_nrs_dict(n + 10, z)
            break
        else:
            print("Input a valid option!")
    while True:
        show_options_2()
        opt2 = input()
        if opt2 == "0":
            print("Leaving application...")
            break
        if opt2 == "1":
            arr, mod_bin = max_subarray(z)
            print(f"The initial array is:\n{z}")
            print(f"The binary modulus array is:\n{mod_bin}")
            print(f"The length of the subarray is: {str(len(arr))}.")
            print(f"The subarray is:\nComplex:{arr}\nModulus:{make_modulus_list(arr)}")
        elif opt2 == "2":
            mod = make_modulus_list(z)
            arr_m, arr_c = longest_increasing_subsequence(z, mod)
            print(f"The initial array is:\n{z}")
            # print(f"The modulus array is:\n{mod}")
            print(f"The length of the subsequence is:{str(len(arr_c))}.")
            print(f"The subsequence is:\nComplex:{arr_c}\nModulus:{arr_m}")
        elif opt2 == "3":
            print(f"The complex numbers are:\n{z}")
        else:
            print("Invalid input!")


def show_options_1():
    print("Choose an option from the following:")
    print("1.Represent the complex numbers using lists.")
    print("2.Represent the complex numbers using a dictionary.")


def show_options_2():
    print("What would you like to do?")
    print("0.Exit application")
    print(
        "1.Find the length and elements of a longest subarray of numbers where each number's modulus is in the [0, "
        "10] range.")
    print(
        "2.Find the length and elements of a longest increasing subsequence, when considering each number's modulus.")
    print("3.Display the complex numbers.")


def read_complex_nrs_list(n, rl, im):
    # this function reads n complex numbers using lists
    for i in range(10, n + 10):
        auxrl = int(input("Input the real part of the number: "))
        rl.append(auxrl)
        auxim = int(input("Input the imaginary part of the number: "))
        im.append(auxim)
        # creates two lists
        # rl the list with the real part of the numbers
        # im the list with the imaginary part of the numbers
        print_conditions(auxrl, auxim, i)
    return rl, im


def read_complex_nrs_dictionary(n, z):
    # reading n complex numbers using a dictionary
    for i in range(10, n + 10):
        auxrl = int(input("Input the real part of the number: "))
        auxim = int(input("Input the imaginary part of the number: "))
        number = complex(auxrl, auxim)
        z.append(number)
    return z


def print_conditions(auxrl, auxim, i):
    # this function prints the complex numbers taking into account some particular cases
    # for example if auxrl=5 and auxim=1 then instead of
    # z = 5 + 1i we will have z = 5 + i
    if auxim == -1:
        print("z" + str(i + 1) + " = " + str(auxrl) + " - " + "i")
    elif auxim == 1 and auxrl == 0:
        print("z" + str(i + 1) + " = i")
    elif auxim < 0:
        print("z" + str(i + 1) + " = " + str(auxrl) + " - " + str(abs(auxim)) + "i")
    elif auxim == 0:
        print("z" + str(i + 1) + " = " + str(auxrl))
    elif auxrl == 0:
        print("z" + str(i + 1) + " = " + str(auxim) + "i")
    elif auxim == 1:
        print("z" + str(i + 1) + " = " + str(auxrl) + " + " + "i")
    elif auxim > 0:
        print("z" + str(i + 1) + " = " + str(auxrl) + " + " + str(auxim) + "i")


if __name__ == "__main__":
    menu()
