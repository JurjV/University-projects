import random

from src.domain.entities import Book, Client, Rental


class BooksRepo:
    def __init__(self):
        self.all_books = []
        self.title_check = []
        for _ in range(10):
            self.get_ten_random_books()

    def get_all_books(self):
        return self.all_books

    def add_book(self, new_book: Book):
        self.all_books.append(new_book)

    def change_book_rent_state(self, book_id, state):
        for book in self.all_books:
            if book.book_id == book_id:
                book.rented = state

    def get_ten_random_books(self):
        while True:
            authors = ["Jane Austen", "F. Scott Fitzgerald", "Harper Lee", "J.D. Salinger", "Herman Melville",
                       "Paulo Coelho", "Oscar Wilde"]
            author = random.choice(authors)
            if author == "Jane Austen":
                titles = ["Pride and Prejudice", "Sense and Sensibility", "Emma", "Mansfield Park", "Northanger Abbey"]
            elif author == "F. Scott Fitzgerald":
                titles = [
                    "The Great Gatsby", "Tender is the Night", "The Beautiful and Damned",
                    "The Love of the Last Tycoon",
                    "This Side of Paradise"]
            elif author == "Harper Lee":
                titles = ["To Kill a Mockingbird", "Go Set a Watchman", "The Reverend", "Summer", "Christmas to Me"]
            elif author == "J.D. Salinger":
                titles = ["The Catcher in the Rye", "Franny and Zooey", "Nine Stories",
                          "Raise High the Roof Beam, Carpenters and Seymour: An Introduction", "The Glass Family"]
            elif author == "Herman Melville":
                titles = ["Moby-Dick", "Typee", "Omoo", "Redburn", "White-Jacket"]
            elif author == "Paulo Coelho":
                titles = ["The Alchemist", "The Pilgrimage", "The Valkyries", "Brida", "Veronika Decides to Die"]
            else:
                titles = ["The Picture of Dorian Gray", "The Importance of Being Earnest", "An Ideal Husband",
                          "The Happy Prince and Other Stories", "The Ballad of Reading Gaol"]
            title = random.choice(titles)
            if title not in self.title_check:
                self.title_check.append(title)
                book = Book(str(len(self.all_books) + 1), title, author, False)
                self.all_books.append(book)
                # print(book.__dict__)
                break


class ClientsRepo:
    def __init__(self):
        self.all_clients = []
        self.name_check = []
        for _ in range(10):
            self.get_ten_random_clients()

    def add_client(self, new_client: Client):
        self.all_clients.append(new_client)

    def get_all_clients(self):
        return self.all_clients

    def get_ten_random_clients(self):
        names = ["Emily Williams", "Michael Smith", "Jessica Johnson", "Matthew Brown", "Ashley Davis",
                 "Christopher Garcia", "Sarah Rodriguez", "William Thompson", "Olivia Martinez", "James Anderson",
                 "Abigail Taylor", "John Davis", "Madison Jones", "David Gonzalez", "Rachel Wilson", "Joseph Martin",
                 "Madison White", "Christopher Baker", "Lauren Davis", "William Taylor", "Anna Baker",
                 "Michael Williams", "Olivia Johnson", "Christopher Rodriguez", "Sarah Jones", "Matthew Brown",
                 "Jessica Davis", "James Wilson", "Emily Martin", "Madison Smith", "Abigail Jones", "Rachel Brown",
                 "Michael Taylor", "Olivia Anderson", "Christopher Wilson", "James Davis", "Sarah Baker",
                 "William White", "Jessica Martin", "Madison Gonzalez", "Emily Baker", "Michael Davis",
                 "Olivia Jones", "Christopher Taylor", "Sarah White", "James Rodriguez", "Rachel Anderson",
                 "Jessica Smith", "William Martin", "Olivia Taylor"]
        while True:
            name = random.choice(names)
            if name not in self.name_check:
                self.name_check.append(name)
                client = Client(str(len(self.all_clients) + 1), name)
                self.all_clients.append(client)
                # print(client.__dict__)
                break


class RentalRepo:
    def __init__(self):
        self.all_rentals = []

    def get_all_rentals(self):
        return self.all_rentals

    def add_rental(self, new_rental: Rental):
        self.all_rentals.append(new_rental)
