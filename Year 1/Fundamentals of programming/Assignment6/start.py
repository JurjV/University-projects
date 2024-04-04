#
# This module is used to invoke the program's UI and start it. It should not contain a lot of code.
#

from functions import initialize_ten_participants
from ui import add_participant, print_all, insert_participant, remove_scores, replace_scores, top_scores, \
    print_options, read_command, undo_operation, test_all

import copy


def run_console():
    test_all()
    commands = {
        'add': add_participant,
        'list': print_all,
        'insert': insert_participant,
        'remove': remove_scores,
        'replace': replace_scores,
        'top': top_scores,
    }
    all_participants = []
    all_participants = initialize_ten_participants(all_participants)
    undo_list = [copy.deepcopy(all_participants)]
    while True:
        ok = 0
        print_options(commands)
        cmd, args = read_command()
        if cmd == 'exit':
            break
        elif cmd == 'undo':
            all_participants = undo_operation(undo_list, all_participants)
            ok = 1
        try:
            commands[cmd](all_participants, args)

            # the lines below add the current list to the undo list if it has been changed
            if cmd in ['add', 'insert', 'remove', 'replace']:
                undo_list.append(copy.deepcopy(all_participants))

        except KeyError as ke:
            if ok == 0:
                print("This option is not implemented yet!", ke)
        except TypeError as te:
            print("Invalid input", te)


run_console()
