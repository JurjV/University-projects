from dataclasses import dataclass


@dataclass
class Complex:
    real: int
    imaginary: int

    def __str__(self):
        if self.real >= 0:
            a_sign = "+"
        else:
            a_sign = "-"
        if self.imaginary >= 0:
            b_sign = "+"
        else:
            b_sign = "-"
        return f"{a_sign}{abs(self.real)}{b_sign}{abs(self.imaginary)}i"
