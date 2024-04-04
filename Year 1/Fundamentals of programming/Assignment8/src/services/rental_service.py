from src.domain.entities import Rental


class RentalService:
    def __init__(self, rental_repo, book_repo, client_repo):
        self.rental_repo = rental_repo
        self.book_repo = book_repo
        self.client_repo = client_repo
        self.rental_durations_by_index = []
        self.all_active_clients = [[x.name, 0] for x in self.client_repo.all_clients]
        self.all_active_titles = [[x.title, 0] for x in self.book_repo.all_books]
        self.all_list = list(set([x.author for x in self.book_repo.all_books]))
        self.all_active_authors = [[x, 0] for x in self.all_list]

    def display_all(self):
        return self.rental_repo.get_all_rentals()

    def change_lists(self):
        self.all_active_clients = [[x.name, 0] for x in self.client_repo.all_clients]
        self.all_active_titles = [[x.title, 0] for x in self.book_repo.all_books]
        self.all_list = list(set([x.author for x in self.book_repo.all_books]))
        self.all_active_authors = [[x, 0] for x in self.all_list]

    def rent_a_book(self, client_id, book_id, rental_date):
        new_rental = Rental(str(len(self.rental_repo.get_all_rentals()) + 1), book_id, client_id, rental_date,
                            "Not returned yet")
        self.rental_repo.add_rental(new_rental)
        self.book_repo.change_book_rent_state(book_id, True)
        self.change_title_and_author_rented_list(book_id)

    def check_if_rental_exists(self, check_id):
        ok = 0
        for rental in self.rental_repo.all_rentals:
            if rental.rental_id == check_id:
                ok = 1
                if rental.returned_date != "Not returned yet":
                    ok = 2
        return ok

    def add_rental_duration_to_list(self, rental_id, time):
        rental_duration = [rental_id, time]
        self.rental_durations_by_index.append(rental_duration)
        # print(self.rental_durations_by_index)

    def return_a_book(self, rental_id, return_date):
        for rental in self.rental_repo.all_rentals:
            if rental.rental_id == rental_id:
                book_id = rental.book_id
                rental.returned_date = return_date
                self.book_repo.change_book_rent_state(book_id, False)
                rental_time = self.calculate_rental_duration_in_days(rental.rented_date, rental.returned_date)
                self.add_rental_duration_to_list(rental.rental_id, rental_time)
                self.change_client_data(rental.client_id, rental_time)

    def change_client_data(self, client_id, rental_time):
        for client in self.client_repo.all_clients:
            if client.client_id == client_id:
                for name in self.all_active_clients:
                    if client.name == name[0]:
                        name[1] += rental_time

    def change_title_and_author_rented_list(self, book_id):
        for book in self.book_repo.all_books:
            if book.book_id == book_id:
                for title in self.all_active_titles:
                    if book.title == title[0]:
                        title[1] += 1
                for author in self.all_active_authors:
                    if book.author == author[0]:
                        author[1] += 1
        # print(self.all_active_titles, self.all_active_authors)

    @staticmethod
    def calculate_rental_duration_in_days(rental_date, return_date):
        rental_list = [int(x) for x in rental_date.split('/')]
        return_list = [int(x) for x in return_date.split('/')]
        suma = 0
        diff = return_list[2] - rental_list[2]
        if diff != 0:
            suma = suma + 365 * diff
        diff = return_list[1] - rental_list[1]
        if diff != 0:
            suma = suma + 30 * diff
        diff = return_list[0] - rental_list[0]
        suma = suma + diff
        # print(f"{return_date}-{rental_date}={suma}")
        return suma

    def get_all_stats(self):
        return sorted(self.all_active_clients, key=lambda x: x[1], reverse=True), sorted(self.all_active_titles, key=lambda x: x[1], reverse=True), sorted(self.all_active_authors, key=lambda x: x[1], reverse=True)
