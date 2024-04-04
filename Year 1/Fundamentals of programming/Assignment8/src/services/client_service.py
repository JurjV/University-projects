from src.domain.entities import Client


class ClientService:
    def __init__(self, client_repo):
        self.repo = client_repo

    def add_client(self, name):
        client = Client(str(len(self.repo.all_clients) + 1), name)
        self.repo.add_client(client)

    def display_all(self):
        return self.repo.get_all_clients()

    def remove_by_id(self, remove_id):
        new_list = []
        ok = 1
        for client in self.repo.all_clients:
            if client.client_id != remove_id:
                new_list.append(client)
            else:
                ok = 0
        self.repo.all_clients = new_list
        if ok == 1:
            return False
        return True

    def update_client_indexes(self):
        i = 1
        for client in self.repo.all_clients:
            client.client_id = str(i)
            i += 1

    def check_if_client_exists(self, rental_c_id):
        ok = 0
        for client in self.repo.all_clients:
            if client.client_id == rental_c_id:
                ok = 1
        return ok

    def search_by_index(self, search_id):
        for client in self.repo.all_clients:
            if client.client_id == search_id:
                return f"The client you are searching for is: {client}."
        return "The client you are searching for does not exist!"

    def search_by_name(self, search_text):
        matches = []
        ok = 0
        for client in self.repo.all_clients:
            if search_text.lower() in client.name.lower():
                matches.append(client)
                ok = 1
        if ok == 0:
            return []
        return matches
