from src.domain.entities import Complex
from src.repository.complex_repo import Repo


class TextRepo(Repo):
    def __init__(self, file_name):
        Repo.__init__(self)
        self.file_name = file_name

    def read_list_data(self):
        with open(self.file_name, "r") as f:
            f.seek(0)
            all_text = f.read().strip().split('\n')
        for number in all_text:
            try:
                a_sign = 1
                b_sign = 1
                if number[0] == "-":
                    a_sign = -1
                if number[2] == "-":
                    b_sign = -1
                current_number = Complex(a_sign * int(number[1]), b_sign * int(number[3]))
                self.all_numbers.append(current_number)
            except ValueError as ve:
                if str(ve) == "invalid literal for int() with base 10: ''":
                    pass
                else:
                    print("Incorrect value in file,", ve)

    def print_list_to_file(self):
        with open(self.file_name, "w") as f:
            for number in self.all_numbers:
                a_sign = "+"
                b_sign = "+"
                if number.real < 0:
                    a_sign = "-"
                if number.imaginary < 0:
                    b_sign = "-"
                f.write(f"{a_sign}{abs(number.real)}{b_sign}{abs(number.imaginary)}i\n")
