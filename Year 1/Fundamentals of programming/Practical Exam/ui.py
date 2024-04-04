import random

import texttable


class UI:
    def __init__(self, service):
        self.service = service
        self.service.test_star_placement()
        self.generate_10_random_stars()
        self.endeavour = self.place_endeavour()
        self.place_cruisers(3)
        # star=1
        # endeavour=2
        # cruiser=3

    def generate_10_random_stars(self):
        """
        This is the function that places the 10 random stars inside the table
        using the check_area function,if the place is not valid we choose another random place
        and do this until we have placed all ten stars in valid squares.
        :return: nothing
        """
        for _ in range(0, 10):
            i = random.randint(1, 8)
            j = random.randint(1, 8)
            while self.service.check_area(i, j, True) is True:
                i = random.randint(1, 8)
                j = random.randint(1, 8)
            self.service.repo.secret_matrix[i][j] = 1
            self.service.repo.matrix[i][j] = "*"

    def place_endeavour(self):
        i = random.randint(1, 8)
        j = random.randint(1, 8)
        while self.service.check_if_place_valid(i, j):
            i = random.randint(1, 8)
            j = random.randint(1, 8)
        self.service.repo.secret_matrix[i][j] = 2
        self.service.repo.matrix[i][j] = "E"
        return [i, j]

    def place_cruisers(self, num):
        for _ in range(0, num):
            i = random.randint(1, 8)
            j = random.randint(1, 8)
            while self.service.check_if_place_valid(i, j):
                i = random.randint(1, 8)
                j = random.randint(1, 8)
            self.service.repo.secret_matrix[i][j] = 3

    def show_matrix(self, cheat=False):
        table = texttable.Texttable()
        self.service.show_neighbours(self.endeavour[0], self.endeavour[1])
        if cheat is True:
            self.service.show_cruisers()
        table.add_rows(self.service.repo.matrix)
        print(table.draw())

    def fire(self, location):
        try:
            x = self.service.change_char_to_integer(location[0])
            if x == -1:
                print("Invalid input!")
            y = int(location[1])
            if y > 8 or y < 1:
                print("Invalid input!")
            ok = self.service.check_if_close(self.endeavour[0], self.endeavour[1], x, y)
            if ok:
                text = self.service.destroy_ship(x, y)
                if text:
                    print("One ship down!")
                    self.service.erase_cruisers()
                    self.place_cruisers(self.service.repo.ships)
                else:
                    print("You missed!")
            else:
                print("Wrong location!")
        except ValueError:
            print("Invalid input!")

    def warp(self, location):
        try:
            x = self.service.change_char_to_integer(location[0])
            if x == -1:
                print("Invalid input!")
            y = int(location[1])
            if y > 8 or y < 1:
                print("Invalid input!")
            ok = self.service.check_if_warp_valid(self.endeavour[0], self.endeavour[1], x, y)
            if ok:
                destroyed = self.service.check_if_cruiser(x, y)
                if destroyed:
                    return True
                else:
                    self.service.repo.matrix[self.endeavour[0]][self.endeavour[1]] = ""
                    self.service.repo.secret_matrix[self.endeavour[0]][self.endeavour[1]] = 0
                    self.service.repo.secret_matrix[x][y] = 2
                    self.service.repo.matrix[x][y] = "E"
                    self.endeavour = [x, y]
            else:
                print("Cannot warp to that location!")
        except ValueError:
            print("Invalid input!")
        return False

    def run_console(self):
        print("Welcome to the Game!")
        print("The initial layout is:")
        self.show_matrix()
        while True:
            cmd = input("Command>")
            if cmd == "exit":
                break
            cmd_data = cmd.split(' ')
            if cmd_data[0] == "cheat":
                self.show_matrix(True)
            elif cmd_data[0] == "fire":
                self.fire(cmd_data[1])
                self.show_matrix()
                if self.service.repo.ships == 0:
                    print("Game Over!\n You win!!!")
                    break
            elif cmd_data[0] == "warp":
                if len(cmd_data) > 2:
                    print("Invalid input!")
                else:
                    game_over = self.warp(cmd_data[1])
                    if game_over:
                        print("You lost!")
                        break
                    self.show_matrix()
            else:
                self.show_matrix()
