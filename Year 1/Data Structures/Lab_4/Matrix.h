#pragma once
#include <utility>

//DO NOT CHANGE THIS PART
typedef int TElem;
#define NULL_TELEM 0

struct Triple {
    int line;
    int column;
    TElem value;
};

class Matrix {
private:
    int nr_lines;
    int nr_columns;
    int capacity;
    //nr_lines * nr_columns == capacity
    Triple *hashTable;
    float max_load_factor;
    int nr_elements;

    int hashFunction(int i, int j) const;

    int quadraticProbing(int i, int j, int counter) const;

    void resize();

    float loadFactor() const;


public:
    //constructor
    Matrix(int nrLines, int nrCols);

    //returns the number of lines
    int nrLines() const;

    //returns the number of columns
    int nrColumns() const;

    //returns the element from line i and column j (indexing starts from 0)
    //throws exception if (i,j) is not a valid position in the Matrix
    TElem element(int i, int j);

    //modifies the value from line i and column j
    //returns the previous value from the position
    //throws exception if (i,j) is not a valid position in the Matrix
    TElem modify(int i, int j, TElem e);

    std::pair<int,int> positionOf(TElem elem) const;

};
