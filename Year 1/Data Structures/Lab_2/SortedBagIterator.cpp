#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

SortedBagIterator::SortedBagIterator(const SortedBag &b) : bag(b) {
    this->current = b.head;
    this->count=1;
    //Theta(1) complexity
}

TComp SortedBagIterator::getCurrent() {
    if (valid())
        return current->element;
    else
        throw exception();
    //Theta(1) complexity
}

bool SortedBagIterator::valid() {
    if (current != nullptr)
        return true;
    else
        return false;
    //Theta(1) complexity
}

void SortedBagIterator::next() {
    if (!valid())
        throw exception();
    else
        if(this->count==current->frequency) {
            current = current->next;
            this->count=1;
        }
        else{
            this->count++;
        }
        //Theta(1) complexity
}

void SortedBagIterator::first() {
    this->current = this->bag.head;
    //Theta(1) complexity
}

