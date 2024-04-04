#include "SortedBag.h"
#include "SortedBagIterator.h"


SortedBag::SortedBag(Relation r) {
    this->r = r;
    this->head = nullptr;
    this->nr_elems = 0;
    //Theta(1) complexity
}

void SortedBag::add(TComp e) {
    this->nr_elems++;
    if (this->head == nullptr) {
        this->head = new Node(e, 1);
        return;
    }
    if (search(e)) {
        Node *curr = head;
        while (curr->element != e)
            curr = curr->next;
        curr->frequency++;
        return;
    }
    Node *curr = head;
    while (curr != nullptr && !this->r(e, curr->element))
        curr = curr->next;
    Node *new_node = new Node(e, 1);
    if (curr != nullptr) {
        new_node->prev = curr->prev;
        new_node->next = curr;
        curr->prev = new_node;
        if (head == curr)
            head = new_node;
        else {
            new_node->prev->next = new_node;
        }
    } else {
        Node *tail = head;
        while (tail->next != nullptr) {
            tail = tail->next;
        }
        new_node->prev = tail;
        tail->next = new_node;
    }
    //Best case happens when the element that we want to add
    //already exists in the DLL and is on the first position
    //so we just have to increase its frequency
    //complexity Theta(1)
    //worst case happens when the element does not exist inside the DLL
    //and by the relation it should be put at the tail of the DLL
    //in this case the complexity is O(n)

    ///Overall complexity is O(n)

}


bool SortedBag::remove(TComp e) {
    if (this->head == nullptr) {
        return false;
    }
    if (!search(e))
        return false;
    this->nr_elems--;
    Node *curr = head;
    while (curr->next != nullptr && curr->element != e)
        curr = curr->next;
    if (curr->frequency > 1) {
        curr->frequency--;
        return true;
    } else {
        if (curr->prev != nullptr) {
            curr->prev->next = curr->next;
        } else {
            this->head = curr->next;
        }
        if (curr->next != nullptr)
            curr->next->prev = curr->prev;
        delete curr;
        return true;
    }
    //Best case happens when the element is in the head of the DLL
    //and has more than 1 frequency
    //complexity Theta(1)
    //Worst case happens when the element is the last in the DLL
    //complexity O(n)

    ///Overall complexity O(n)
}


bool SortedBag::search(TComp elem) const {
    if (this->head != nullptr) {
        Node *curr = head;
        while (curr->next != nullptr) {
            if (curr->element == elem)
                return true;
            curr = curr->next;
        }
        if (curr->element == elem)
            return true;
    }
    return false;
    //Best case happens when the element is located in the head of the DLL
    //in this case the complexity is Theta(1)
    //Worst case happens when the element is not located in the DLL
    //in this case the complexity is O(n),where n is the number of nodes

    ///So the overall complexity is O(n)
}


int SortedBag::nrOccurrences(TComp elem) const {
    if (!search(elem))
        return 0;
    else {
        Node *curr = head;
        while (curr->element != elem)
            curr = curr->next;
        return curr->frequency;
    }
    //Best case happens when the element is not  located in the DLL
    //in this case the complexity is Theta(1)
    //Worst case happens when the element is located in the tail of the DLL
    //in this case the complexity is O(n),where n is the number of nodes

    ///So the overall complexity is O(n)
}


int SortedBag::size() const {
    return this->nr_elems;
    //Theta(1) complexity
}


bool SortedBag::isEmpty() const {
    if (this->head == nullptr)
        return true;
    return false;
    //Theta(1) complexity
}

int SortedBag::removeAll() {
    int removed_elem = 0;
    if (this->head != nullptr) {
        Node *curr = head;
        while (curr != nullptr) {
            if (curr->frequency > 1) {
                while (curr->frequency > 1) {
                    remove(curr->element);
                }
                remove(curr->element);
                removed_elem++;
            }
            curr = curr->next;
        }
        return removed_elem;
    } else
        return 0;
    //The best case happens when the frequency of all elements in the
    //DLL is 1
    //complexity O(n)
    //The worst case happens when all of the elements in the
    //DLL have more than 1 complexity
    //complexity O(n^2)

    ///Overall complexity O(n^2)
}


SortedBagIterator SortedBag::iterator() const {
    return SortedBagIterator(*this);
    //Theta(1) complexity
}


SortedBag::~SortedBag() = default;
//Theta(1) complexity
