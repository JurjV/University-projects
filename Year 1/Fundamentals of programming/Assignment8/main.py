from src.repository.repository import BooksRepo, ClientsRepo, RentalRepo
from src.services.book_service import BookService
from src.services.client_service import ClientService
from src.services.rental_service import RentalService
from src.ui.console import Console

if __name__ == "__main__":
    book_repo = BooksRepo()
    client_repo = ClientsRepo()
    rental_repo = RentalRepo()
    book_serv = BookService(book_repo)
    client_serv = ClientService(client_repo)
    rental_serv = RentalService(rental_repo, book_repo, client_repo)
    console = Console(book_serv, client_serv, rental_serv)
    console.run_console()
