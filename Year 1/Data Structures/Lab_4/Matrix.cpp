#include "Matrix.h"
#include <exception>
#include <iostream>


using namespace std;


Matrix::Matrix(int nrLines, int nrCols) {

    this->nr_lines = nrLines;
    this->nr_columns = nrCols;
    this->nr_elements = 0;
    this->max_load_factor = 0.75;
    this->capacity = nr_columns * nr_lines;
    this->hashTable = new Triple[capacity];
    for (int i = 0; i < capacity; i++) {
        hashTable[i].value = NULL_TELEM;
        hashTable[i].line = -1;
        hashTable[i].column = -1;
    }
}
/// Best Case = Worst Case = Overall = Theta(capacity)


int Matrix::nrLines() const {
    return this->nr_lines;
}
/// BC = WC = Overall = Theta(1)


int Matrix::nrColumns() const {
    return this->nr_columns;
}
/// BC = WC = Overall = Theta(1)


TElem Matrix::element(int i, int j) {
    if ((i >= this->nr_lines || i < 0) || (j >= this->nr_columns || j < 0))
        throw std::exception();
    else {
        int counter = 0;
        int position = -1;
        while (true) {
            position = quadraticProbing(i, j, counter);
            if (hashTable[position].line == -1 && hashTable[position].column == -1)
                return NULL_TELEM;
            if (hashTable[position].line == i && hashTable[position].column == j)
                return hashTable[position].value;
            counter++;
        }
    }
}
//Best case happens when the element is on the first accessible position by using the hashFunction
//in this case we have Theta(1)
//Worst case happens when the matrix is full and all of the elements are collisions,the searched element being
//at the end, in this case we have Theta(nr_elements)
///Overall complexity is O(n)


TElem Matrix::modify(int i, int j, TElem e) {
    if ((i >= this->nr_lines || i < 0) || (j >= this->nr_columns || j < 0))
        throw std::exception();
    else {
        if (loadFactor() > max_load_factor) resize();

        int counter = 0;
        int position = -1;
        while (true) {
            if (counter > capacity) {
                resize();
                counter = 0;
            }

            position = quadraticProbing(i, j, counter);
            if (hashTable[position].line == -1) {
                hashTable[position].value = e;
                hashTable[position].line = i;
                hashTable[position].column = j;
                nr_elements++;
                return NULL_TELEM;
            } else {
                if (hashTable[position].line == i && hashTable[position].column == j) {
                    TElem old_v = hashTable[position].value;
                    hashTable[position].value = e;
                    return old_v;
                }
            }
            counter++;
        }

    }
}
//Best case happens when the element is on the first accessible position by using the hashFunction
//in this case we have Theta(1)
//Worst case happens when the matrix is full and all of the elements are collisions,the searched element being
//at the end, in this case we have Theta(nr_elements)
///Overall complexity O(n)


int Matrix::quadraticProbing(int i, int j, int counter) const {
    return (hashFunction(i, j) + counter * counter) % capacity;
}
/// BC = WC = Overall = Theta(1)


int Matrix::hashFunction(int i, int j) const {
    return (i * 13 + j) % capacity;
}
/// BC = WC = Overall = Theta(1)

float Matrix::loadFactor() const {
    return (float) (this->nr_elements) / (float) (this->capacity);
}
/// BC = WC = Overall = Theta(1)


void Matrix::resize() {
    this->capacity *= 2;

    auto *newHashTable = new Triple[capacity];
    for (int i = 0; i < capacity; i++) {
        newHashTable[i].value = NULL_TELEM;
        newHashTable[i].line = -1;
        newHashTable[i].column = -1;
    }
    Triple *oldHashTable = hashTable;
    hashTable = newHashTable;

    //rehash
    for (int i = 0; i < capacity / 2; i++) {
        if (oldHashTable[i].value != NULL_TELEM) {
            int line = oldHashTable[i].line, column = oldHashTable[i].column;
            TElem value = oldHashTable[i].value;
            int counter = 0;
            int position = -1;
            while (true) {

                position = quadraticProbing(line, column, counter);
                if (hashTable[position].line == -1) {
                    hashTable[position].value = value;
                    hashTable[position].line = line;
                    hashTable[position].column = column;
                    break;
                }
                counter++;
            }
        }
    }
    delete[] oldHashTable;
}
//Best case happens when none of the elements from the oldHashTable are collisions in the new one
//In this case we have Theta(n) complexity
//Worst case happens when all of the elements from the oldHashTable are collisions in the new one
//In this case we have Theta(n^2) complexity
///Overall complexity is O(n^2)

std::pair<int, int> Matrix::positionOf(TElem elem) const {
    for (int i = 0; i < this->capacity; i++) {
        if (hashTable[i].line != -1) {
            if (hashTable[i].value == elem) {
                int line = hashTable[i].line;
                int column = hashTable[i].column;
                return std::make_pair(line, column);
            }
        }
    }
    return std::make_pair(-1, -1);
}
//Best case happens when the element is on the first position of the hashTable
//in this case we have Theta(1) complexity
//Worst case happens when the element is not inside the hashTable
//in this case we have Theta(capacity) complexity
///Overall complexity is O(capacity)