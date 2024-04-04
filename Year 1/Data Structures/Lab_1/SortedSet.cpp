#include "SortedSet.h"
#include "SortedSetIterator.h"

SortedSet::SortedSet(Relation r) {
    this->r = r;
    this->capacity = 1;
    this->elems = new TElem[1];
    this->nr_elements = 0;
}


bool SortedSet::add(TComp elem) {
    if (search(elem)) {
        return false;
    }
    if (nr_elements == capacity)
        Resize();
    elems[nr_elements] = elem;
    nr_elements++;
    for (int i = nr_elements - 1; i > 0; --i) {
        if (r(elems[i], elems[i - 1])) {
            TElem aux = elems[i];
            elems[i] = elems[i - 1];
            elems[i - 1] = aux;
        }
    }
    return true;
    //let the number of elements in the array be n
    //Worst case complexity is happening when the
    //element is not in the array and the added element is the smallest
    //and the nr of elements in the array is equal to the capacity
    //in this case the complexity is O(n)
    //
    //For the best case,it happens when the element was already added
    // and it is on the first position of the array
    //in this case we have O(1)
    //
    //So the overall complexity is Omega(n)
}


bool SortedSet::remove(TComp elem) {
    if (!search(elem))
        return false;
    int i = 0;
    for (int j = 0; j < nr_elements; ++j) {
        if (elems[j] == elem) {
            i = j;
            break;
        }
    }
    for (int j = i; j < nr_elements - 1; ++j) {
        elems[j] = elems[j + 1];
    }
    this->nr_elements--;
    return true;
    //Best case when the element is not in the array
    // O(n)
    //Worst case when the element is in the array on any position
    // O(n)
    //
    //So the overall complexity is Theta(n)
}


bool SortedSet::search(TComp elem) const {
    for (int i = 0; i < nr_elements; ++i) {
        if (elems[i] == elem)
            return true;
    }
    return false;
    //Best case when the element is first in the array
    // O(1)
    //Worst case when the element is not in the array
    //O(n)
    //So the overall complexity is Omega(n)
}


int SortedSet::size() const {
    return nr_elements;
    // Theta(1) complexity
}


bool SortedSet::isEmpty() const {
    return nr_elements == 0;
    // Theta(1) complexity
}

SortedSetIterator SortedSet::iterator() const {
    return SortedSetIterator(*this);
}


SortedSet::~SortedSet() {
    delete[] elems;
}

void SortedSet::Resize() {
    this->capacity *= 2;
    auto *new_arr = new TElem[capacity];
    for (int i = 0; i <= this->nr_elements; ++i) {
        new_arr[i] = this->elems[i];
    }
    delete[] this->elems;
    this->elems = new_arr;
    //Complexity O(n) every time
    // So the complexity is Theta(n)
}

void SortedSet::intersection(const SortedSet &s) {
    if (!s.isEmpty() && !this->isEmpty()) {
        for (int i = 0; i < this->nr_elements; ++i) {
            if (!s.search(this->elems[i])) {
                this->remove(this->elems[i]);
            }
        }
    }
    //Best case is when s or the set is empty
    //In this case the complexity is Theta(1)
    //Worst case happens when the two sets have the same nr of elements
    //In this case the complexity is O(n)
    //So the overall complexity is Omega(n)
}
