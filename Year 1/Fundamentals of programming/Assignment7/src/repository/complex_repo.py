import random

from src.domain.entities import Complex


class Repo:
    def __init__(self):
        self.all_numbers = []
        self.random_complex_numbers()

    def random_complex_numbers(self):
        for _ in range(10):
            complex_nr = Complex(random.randint(-100, 100), random.randint(-100, 100))
            self.all_numbers.append(complex_nr)

    def get_all(self):
        return self.all_numbers

    def add_complex(self, new_number: Complex):
        """
        This function appends the complex number to the list.
        :param new_number: complex number
        """
        self.all_numbers.append(new_number)
