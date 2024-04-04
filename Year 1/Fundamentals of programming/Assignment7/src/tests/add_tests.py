from src.domain.entities import Complex
from src.repository.complex_repo import Repo
from src.services.complex_serv import Service


def test_entities():
    all_numbers = []
    number = Complex(1, 2)
    assert number.real == 1
    assert number.imaginary == 2
    all_numbers.append(number)
    number = Complex(13, 20)
    all_numbers.append(number)
    assert all_numbers[1].real == 13
    assert all_numbers[1].imaginary == 20
    number = Complex(200, 300)
    assert number.real == 200
    number.real = 20
    assert number.real == 20


def test_service_and_repo():
    repo = Repo()
    repo.all_numbers = setup()
    serv = Service(repo)
    number = Complex(12, 77)
    repo.add_complex(number)
    assert len(repo.all_numbers) == 4
    serv.add(77, 12)
    assert len(repo.all_numbers) == 5


def setup():
    all_numbers = []
    number = Complex(1, 2)
    all_numbers.append(number)
    number = Complex(20, 50)
    all_numbers.append(number)
    number = Complex(-21, 70)
    all_numbers.append(number)
    return all_numbers


def test_all():
    test_entities()
    test_service_and_repo()


test_all()
