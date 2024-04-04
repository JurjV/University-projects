import copy

from src.domain.entities import Complex


class Service:
    def __init__(self, repository):
        self.repo = repository
        self.history = []
        self.history.append(copy.deepcopy(self.repo.all_numbers))

    def add(self, real, imaginary):
        """
        This function builds the complex number with the two parameters and passes it to the repo.
        :param real: the real part of the complex number
        :param imaginary: the imaginary part of the complex number
        """
        new_number = Complex(real, imaginary)
        self.repo.add_complex(new_number)
        self.history.append(copy.deepcopy(self.repo.all_numbers))

    def display_all(self):
        return self.repo.get_all()

    def filter_numbers(self, start, end):
        new_numbers = []
        for i in range(0, len(self.repo.all_numbers)):
            if start <= i <= end:
                new_numbers.append(copy.deepcopy(self.repo.all_numbers[i]))
        self.repo.all_numbers = copy.deepcopy(new_numbers)
        self.history.append(copy.deepcopy(self.repo.all_numbers))

    def undo(self):
        if len(self.history) <= 1:
            return "Cannot undo anymore!"
        else:
            self.history.pop(-1)
            self.repo.all_numbers = copy.deepcopy(self.history[len(self.history) - 1])
            return "Undo successful!"
