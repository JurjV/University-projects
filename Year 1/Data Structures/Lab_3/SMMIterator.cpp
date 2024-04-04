#include "SMMIterator.h"
#include "SortedMultiMap.h"

SMMIterator::SMMIterator(const SortedMultiMap& d) : map(d) {
    this->current = d.head;
    this->all_elements = d.all_elems;
    this->count = 0;
    //Theta(1) complexity
}

void SMMIterator::first() {
    this->current = this->map.head;
    this->count = 0;
    //Theta(1) complexity
}

void SMMIterator::next() {
    if (valid()) {
        if (count == all_elements[current].values.size() - 1) {
            current = this->all_elements[current].next;
            this->count = 0;
        } else {
            this->count++;
        }
    } else {
        throw exception();
    }
    //Theta(1) complexity
}

bool SMMIterator::valid() const {
    return current >= 0 && current < this->all_elements.size() && count < all_elements[current].values.size();
    //Theta(1) complexity
}

TElem SMMIterator::getCurrent() const {
    if (valid()) {
        return std::make_pair(all_elements[current].key, all_elements[current].values[count]);
    } else {
        throw std::exception();
    }
    //Theta(1) complexity
}