class Service:
    def __init__(self, repo):
        self.repo = repo

    def check_area(self, x, y, include_self=False, test=False):
        """
        This is the function that checks if the square is valid for what we
        want to place.
        :param test: if True then it means we are running a test
        :param x: x coordinate
        :param y: y coordinate
        :param include_self: True if we want to check [x,y] as well
        :return: True if the place is not valid,False otherwise
        """
        dx = [-1, -1, -1, 0, 0, 1, 1, 1]
        dy = [-1, 0, 1, 1, -1, -1, 0, 1]
        if include_self is True:
            dx.append(0)
            dy.append(0)
        if test is False:
            for i in range(0, len(dx)):
                if self.repo.secret_matrix[x + dx[i]][y + dy[i]] == 1:
                    return True
            return False
        else:
            for i in range(0, len(dx)):
                if self.repo.aux_matrix[x + dx[i]][y + dy[i]] == 1:
                    return True
            return False

    def check_if_place_valid(self, x, y):
        if self.repo.secret_matrix[x][y] == 1 or self.repo.secret_matrix[x][y] == 2 or \
                self.repo.secret_matrix[x][y] == 3:
            return True
        return False

    def show_cruisers(self):
        for i in range(1, 9):
            for j in range(1, 9):
                if self.repo.secret_matrix[i][j] == 3:
                    self.repo.matrix[i][j] = "B"

    def show_neighbours(self, x, y):
        dx = [-1, -1, -1, 0, 0, 1, 1, 1]
        dy = [-1, 0, 1, 1, -1, -1, 0, 1]
        for i in range(0, len(dx)):
            if self.repo.secret_matrix[x + dx[i]][y + dy[i]] == 3:
                self.repo.matrix[x + dx[i]][y + dy[i]] = "B"

    def change_char_to_integer(self, char):
        if char == "A":
            char = 1
        elif char == "B":
            char = 2
        elif char == "C":
            char = 3
        elif char == "D":
            char = 4
        elif char == "E":
            char = 5
        elif char == "F":
            char = 6
        elif char == "G":
            char = 7
        elif char == "H":
            char = 8
        else:
            return -1
        return char

    def destroy_ship(self, x, y):
        if self.repo.secret_matrix[x][y] == 3:
            self.repo.secret_matrix[x][y] = 0
            self.repo.ships = self.repo.ships - 1
            self.repo.matrix[x][y] = ""
            return True
        return False

    def erase_cruisers(self):
        for i in range(1, 9):
            for j in range(1, 9):
                if self.repo.secret_matrix[i][j] == 3:
                    self.repo.matrix[i][j] = ""
                    self.repo.secret_matrix[i][j] = 0

    def check_if_close(self, x, y, x1, y1):
        # x and y are the coordinates of the endeavour
        # x1 and y1 are the coordinates of the location
        dx = [-1, -1, -1, 0, 0, 1, 1, 1]
        dy = [-1, 0, 1, 1, -1, -1, 0, 1]
        for i in range(0, len(dx)):
            if x + dx[i] == x1 and y + dy[i] == y1:
                return True
        return False

    def check_if_warp_valid(self, x, y, x1, y1):
        # x and y are the coordinates of the endeavour
        # x1 and y1 are the coordinates of the location
        if x == x1 or y == y1:
            return True
        if x - y == x1 - y1:
            return True
        if x + y == x1 + y1:
            return True
        return False

    def check_if_cruiser(self, x, y):
        if self.repo.secret_matrix[x][y] == 3:
            return True
        return False

    def test_star_placement(self):
        self.repo.aux_matrix[1][1] = 1
        self.repo.aux_matrix[2][2] = 1
        assert self.check_area(1, 1, True, True) is True
        assert self.check_area(1, 5, True, True) is False
        assert self.check_area(1, 2, True, True) is True
