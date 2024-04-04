class Nod:
    def __init__(self, e):
        self.e = e
        self.urm = None


class Lista:
    def __init__(self):
        self.prim = None


def creareLista():
    lista = Lista()
    lista.prim = creareLista_rec()
    return lista


def creareLista_rec():
    x = int(input("x="))
    if x == 0:
        return None
    else:
        nod = Nod(x)
        nod.urm = creareLista_rec()
        return nod


def tipar(lista):
    tipar_rec(lista.prim)


def tipar_rec(nod):
    if nod is not None:
        print(nod.e)
        tipar_rec(nod.urm)


def are_equal(list1, list2):
    return are_equal_rec(list1.prim, list2.prim)


def are_equal_rec(node1, node2):
    if node1 is None and node2 is None:
        return True
    if node1 is None or node2 is None:
        return False
    return node1.e == node2.e and are_equal_rec(node1.urm, node2.urm)


def intersection(list1, list2):
    intersection_list = Lista()
    intersection_list.prim = intersection_rec(list1.prim, list2.prim)
    return intersection_list


def intersection_rec(node1, node2):
    if node1 is None or node2 is None:
        return None

    if node1.e < node2.e:
        return intersection_rec(node1.urm, node2)
    if node1.e > node2.e:
        return intersection_rec(node1, node2.urm)

    intersection_node = Nod(node1.e)
    intersection_node.urm = intersection_rec(node1.urm, node2.urm)

    return intersection_node


def main():
    list1 = creareLista()
    list2 = creareLista()

    tipar(list1)
    tipar(list2)

    equal = are_equal(list1, list2)
    if equal:
        print("The lists are equal.")
    else:
        print("The lists are not equal.")

    intersection_list = intersection(list1, list2)
    print("Intersection of the lists:")
    tipar(intersection_list)


main()
