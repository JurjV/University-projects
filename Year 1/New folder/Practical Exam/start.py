from repository import Repo
from service import Service
from ui import UI

repo = Repo()
service = Service(repo)
ui = UI(service)
ui.run_console()
