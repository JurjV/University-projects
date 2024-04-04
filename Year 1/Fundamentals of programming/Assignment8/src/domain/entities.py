from dataclasses import dataclass


@dataclass
class Book:
    book_id: str
    title: str
    author: str
    rented: bool

    def __str__(self):
        return f"ID: {self.book_id};Title: {self.title};Author: {self.author};Rented: {self.rented}"


@dataclass
class Client:
    client_id: str
    name: str

    def __str__(self):
        return f"ID: {self.client_id};Name: {self.name}"


@dataclass
class Rental:
    rental_id: str
    book_id: str
    client_id: str
    rented_date: str
    returned_date: str

    def __str__(self):
        return f"Rental_ID: {self.rental_id};Book_ID: {self.book_id};Client_ID: {self.client_id};Rented date: {self.rented_date};Returned date: {self.returned_date}"
