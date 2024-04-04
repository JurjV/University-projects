from src.domain.entities import Book


class BookService:
    def __init__(self, book_repo):
        self.repo = book_repo

    def add_book(self, title, author):
        # adds a new book to the list all_books
        book = Book(str(len(self.repo.all_books) + 1), title, author, False)
        self.repo.add_book(book)

    def display_all(self):
        return self.repo.get_all_books()

    def remove_by_id(self, remove_id):
        new_list = []
        ok = 1
        for book in self.repo.all_books:
            if book.book_id != remove_id:
                new_list.append(book)
            else:
                ok = 0
        self.repo.all_books = new_list
        if ok == 1:
            return False
        return True

    def update_book_indexes(self):
        i = 1
        for book in self.repo.all_books:
            book.book_id = str(i)
            i += 1

    def check_if_book_can_be_rented(self, rental_b_id):
        ok = 0
        for book in self.repo.all_books:
            if book.book_id == rental_b_id:
                ok = 1
                if book.rented is True:
                    ok = 2
        return ok

    def search_by_index(self, search_id):
        for book in self.repo.all_books:
            if book.book_id == search_id:
                return f"The book you are searching for is: {book}."
        return "The book you are searching for does not exist!"

    def search_by_author(self, search_text):
        matches = []
        ok = 0
        for book in self.repo.all_books:
            if search_text.lower() in book.author.lower():
                matches.append(book)
                ok = 1
        if ok == 0:
            return []
        return matches

    def search_by_title(self, search_text):
        matches = []
        ok = 0
        for book in self.repo.all_books:
            if search_text.lower() in book.title.lower():
                matches.append(book)
                ok = 1
        if ok == 0:
            return []
        return matches
