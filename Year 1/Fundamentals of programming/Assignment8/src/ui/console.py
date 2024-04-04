class Console:
    def __init__(self, book_service, client_service, rental_service):
        self.book_service = book_service
        self.cl_service = client_service
        self.rental_service = rental_service

    def ui_add_book(self):
        title = input("Input the title of a book: ")
        author = input("Name the author: ")
        self.book_service.add_book(title, author)
        self.rental_service.change_lists()

    def ui_add_client(self):
        name = input("Input the name of the client: ")
        self.cl_service.add_client(name)
        self.rental_service.change_lists()

    def ui_rent_book(self):
        book_id = input("Input the id of the book: ")
        ok1 = self.book_service.check_if_book_can_be_rented(book_id)
        if ok1 == 1:
            client_id = input("Input the id of the client: ")
            ok2 = self.cl_service.check_if_client_exists(client_id)
            if ok2 == 1:
                try:
                    print("Input the current date:")
                    year = int(input("Year: "))
                    month = int(input("Month: "))
                    day = int(input("Day: "))
                    rental_date = f'{day}/{month}/{year}'
                    self.rental_service.rent_a_book(client_id, book_id, rental_date)
                except ValueError as ve:
                    print(f"Invalid input, {ve}. Only use numbers!")
            else:
                print("There is no client with that index!")
        elif ok1 == 0:
            print("There is no book with that index!")
        else:
            print("That book is already rented!")

    def ui_return_a_book(self):
        rental_id = input("Input the id of the rental: ")
        ok = self.rental_service.check_if_rental_exists(rental_id)
        if ok == 1:
            try:
                print("Input the date of the return:")
                year = int(input("Year: "))
                month = int(input("Month: "))
                day = int(input("Day: "))
                return_date = f'{day}/{month}/{year}'
                self.rental_service.return_a_book(rental_id, return_date)
            except ValueError as ve:
                print(f"Invalid input, {ve}. Only use numbers!")
        elif ok == 0:
            print("There is no rental with that index!")
        else:
            print("That book was already returned!")

    def ui_display_books(self):
        print("\n")
        for book in self.book_service.display_all():
            print(book)
        print("\n")

    def ui_display_clients(self):
        print("\n")
        for client in self.cl_service.display_all():
            print(client)
        print("\n")

    def ui_display_rentals(self):
        for rental in self.rental_service.display_all():
            print(rental)
        print("\n")

    def ui_remove_book_at_index(self):
        remove_id = input("Input the id of the book that you want to remove: ")
        ok = self.book_service.remove_by_id(remove_id)
        if not ok:
            print("Index out of range!")
        self.rental_service.change_lists()

    def ui_remove_client_at_index(self):
        remove_id = input("Input the id of the client you want to remove: ")
        ok = self.cl_service.remove_by_id(remove_id)
        if not ok:
            print("Index out of range!")
        self.rental_service.change_lists()

    def ui_update_book_indexes(self):
        self.book_service.update_book_indexes()
        self.rental_service.change_lists()

    def ui_update_client_indexes(self):
        self.cl_service.update_client_indexes()
        self.rental_service.change_lists()

    def ui_search_book(self):
        print("How would you like to do the search?")
        print("1.By index")
        print("2.By author")
        print("3.By title")
        try:
            opt = input("Your option: ")
            if int(opt) == 1:
                search_id = input("Input the index of the book: ")
                print(self.book_service.search_by_index(search_id))
            elif int(opt) == 2:
                search_text = input("Input the name of the author: ")
                aut_list = self.book_service.search_by_author(search_text)
                if len(aut_list) == 0:
                    print("There are no matches for that input!")
                else:
                    for aut in aut_list:
                        print(aut)
            elif int(opt) == 3:
                search_text = input("Input the title of the book: ")
                title_list = self.book_service.search_by_title(search_text)
                if len(title_list) == 0:
                    print("There are no matches for that input!")
                else:
                    for title in title_list:
                        print(title)
            else:
                print("That is not a valid option!")
        except ValueError as ve:
            print("Invalid input, ", ve)

    def ui_search_client(self):
        print("How would you like to do the search?")
        print("1.By index")
        print("2.By name")
        try:
            opt = input("Your option: ")
            if int(opt) == 1:
                search_id = input("Input the index of the client: ")
                print(self.cl_service.search_by_index(search_id))
            elif int(opt) == 2:
                search_text = input("Input the name of the client: ")
                cl_list = self.cl_service.search_by_name(search_text)
                if len(cl_list) == 0:
                    print("There are no matches in the list!")
                else:
                    for cl in cl_list:
                        print(cl)
            else:
                print("That is not a valid option!")
        except ValueError as ve:
            print("Invalid input,", ve)

    @staticmethod
    def ui_books_options():
        print("1.Add book")
        print("2.Display all books")
        print("3.Remove book by index")
        print("4.Update book indexes")
        print("5.Search for a book")
        print("x.Go back")

    @staticmethod
    def ui_clients_options():
        print("1.Add client")
        print("2.Display all clients")
        print("3.Remove client by index")
        print("4.Update client indexes")
        print("5.Search for a client")
        print("x.Go back")

    @staticmethod
    def ui_rental_options():
        print("1.Rent a book")
        print("2.Display all rentals")
        print("3.Return a book")
        print("x.Go back")

    @staticmethod
    def ui_manage_options():
        print("What would you like to do?")
        print("1.Manage books")
        print("2.Manage clients")
        print("3.Manage rentals")
        print("4.Show current statistics")
        print("x.Exit")

    def ui_books_setup(self):
        options = {
            1: self.ui_add_book,
            2: self.ui_display_books,
            3: self.ui_remove_book_at_index,
            4: self.ui_update_book_indexes,
            5: self.ui_search_book
        }
        return options

    def ui_clients_setup(self):
        options = {
            1: self.ui_add_client,
            2: self.ui_display_clients,
            3: self.ui_remove_client_at_index,
            4: self.ui_update_client_indexes,
            5: self.ui_search_client
        }
        return options

    def ui_rentals_setup(self):
        options = {
            1: self.ui_rent_book,
            2: self.ui_display_rentals,
            3: self.ui_return_a_book
        }
        return options

    def ui_statistics(self):
        all_active_clients_sorted, all_active_titles_sorted, all_active_authors_sorted = self.rental_service.get_all_stats()
        print("\n")
        print("All active authors in descending order:")
        for author in all_active_authors_sorted:
            print(f"{author[0]} had {author[1]} books rented.")
        print("\n")
        print("All active books in descending order: ")
        for title in all_active_titles_sorted:
            print(f'"{title[0]}" was rented {title[1]} times.')
        print("\n")
        print("All active clients in descending order: ")
        for client in all_active_clients_sorted:
            print(f"{client[0]} has rented books for  {client[1]} days.")
        print("\n")

    def run_console(self):
        while True:
            self.ui_manage_options()
            opt1 = input("Print option here: ")
            if opt1 in ["1", "2", "3"]:
                options = self.ui_books_setup()
                if int(opt1) == 2:
                    options = self.ui_clients_setup()
                elif int(opt1) == 3:
                    options = self.ui_rentals_setup()
                while True:
                    if int(opt1) == 1:
                        self.ui_books_options()
                    elif int(opt1) == 2:
                        self.ui_clients_options()
                    elif int(opt1) == 3:
                        self.ui_rental_options()
                    try:
                        opt = input("Choose an option: ")
                        if opt == "x":
                            break
                        options[int(opt)]()
                    except ValueError as ve:
                        print("Invalid input,", ve)
                    except KeyError as ke:
                        print("Option not implemented yet,", ke)
            elif opt1 == "x":
                break
            elif opt1 == "4":
                self.ui_statistics()
            else:
                print("Invalid option!")
