class Console:
    def __init__(self, service):
        self.serv = service

    def ui_add(self):
        try:
            real = int(input("Input the real part of the number:"))
            imaginary = int(input("Input the imaginary part of the number:"))
            self.serv.add(real, imaginary)
        except ValueError:
            print("Input should be an integer!")

    def ui_display(self):
        print("\n")
        for number in self.serv.display_all():
            print(number)
        print("\n")

    def ui_filter(self):
        try:
            start = int(input("Input the starting index:"))
            end = int(input("Input the ending index:"))
            if start >= end:
                print("The start index should be lower then the end index!\n")
            elif start < 0 or end > len(self.serv.display_all()):
                print("Indexes are out of range!")
            else:
                self.serv.filter_numbers(start, end)
        except ValueError:
            print("Input should be an integer!")

    def ui_undo(self):
        return self.serv.undo()

    @staticmethod
    def ui_print_options():
        print("1.Add complex number")
        print("2.Display all complex numbers")
        print("3.Filter the list by two indexes")
        print("4.Undo operation")
        print("x.Exit")

    def run_console(self):
        while True:
            self.ui_print_options()
            opt = input("Choose an option:")
            try:
                if opt == "x":
                    break
                opt = int(opt)
                if opt == 1:
                    self.ui_add()
                elif opt == 2:
                    self.ui_display()
                elif opt == 3:
                    self.ui_filter()
                elif opt == 4:
                    msg = self.ui_undo()
                    print(msg)
                else:
                    print("Option not implemented yet!")
            except ValueError as ve:
                print("Invalid input, ", ve)
            except KeyError as ke:
                print("Invalid option, ", ke)
