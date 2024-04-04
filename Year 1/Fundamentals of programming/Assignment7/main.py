from src.repository.binary_repo import BinaryRepo
from src.repository.complex_repo import Repo
from src.repository.text_repo import TextRepo
from src.services.complex_serv import Service
from src.tests.add_tests import test_all
from src.ui.complex_ui import Console

if __name__ == '__main__':
    test_all()
    while True:
        print("What type of repository would you like to use?")
        print("1.Memory repository.")
        print("2.Text file repository.")
        print("3.Binary file repository.")
        try:
            opt1 = int(input("Input the number of your choice:"))
            if opt1 not in [1, 2, 3]:
                print("Choose a valid option!")
            elif opt1 == 1:
                repo = Repo()
                break
            elif opt1 == 2:
                repo = TextRepo("memory.txt")
                break
            elif opt1 == 3:
                repo = BinaryRepo("binary_memory.pkl")
                break
        except ValueError as ve:
            print("Invalid input, ", ve)
    serv = Service(repo)
    console = Console(serv)
    console.run_console()
    if opt1 == 2:  # prints the list to the text file if the option was selected
        repo.print_list_to_file()
    elif opt1 == 3:  # prints the list to the binary text file if the option was selected
        repo.print_to_binary_file()
