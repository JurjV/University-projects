import pickle

from src.repository.complex_repo import Repo


class BinaryRepo(Repo):
    def __init__(self, file_name):
        self.__file_name = file_name
        Repo.__init__(self)
        # self.print_to_binary_file()
        self.read_from_binary_file()

    def read_from_binary_file(self):
        # this function reads the pickled data in the file and appends it to the local list"all_expenses"
        try:
            some_list = pickle.load(open(self.__file_name, "rb"))
            for element in some_list:
                self.all_numbers.append(element)
        except EOFError:  # in the case that the list is empty
            pass
        except pickle.UnpicklingError as pe:
            print("The contents of the file are invalid,", pe)

    def print_to_binary_file(self):
        # this function pickles the list and prints it to the file
        pickle.dump(self.all_numbers, open(self.__file_name, "wb"))
