#include "SortedSetIterator.h"
#include <exception>

using namespace std;

SortedSetIterator::SortedSetIterator(const SortedSet &m) : multime(m) {
    this->current = 0;
    //Theta(1) complexity
}


void SortedSetIterator::first() {
    this->current = 0;
    //Theta(1) complexity
}


void SortedSetIterator::next() {
    if (!valid()) {
        throw exception();
    } else {
        this->current++;
    }
    //Theta(1) complexity
}


TElem SortedSetIterator::getCurrent() {
    if (!valid()) {
        throw exception();
    }
    return this->multime.elems[this->current];
    //Theta(1) complexity
}

bool SortedSetIterator::valid() const {
    if (this->current < this->multime.nr_elements) {
        return true;
    } else {
        return false;
    }
    //Theta(1) complexity
}

