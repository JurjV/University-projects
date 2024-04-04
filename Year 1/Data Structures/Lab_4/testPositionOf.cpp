#include "testPositionOf.h"
#include "Matrix.h"
#include <cassert>
#include <iostream>

using namespace std;

void testPositionOf() {
    cout << "Test positionOf" << endl;
    Matrix m(100, 100);
    m.modify(1, 2, 300);
    m.modify(5, 2, 200);
    assert(m.positionOf(200) == make_pair(5, 2));
    assert(m.positionOf(300) == make_pair(1, 2));
    int line = m.positionOf(300).first;
    int column = m.positionOf(300).second;
    assert(m.element(line, column) == 300);
    m.modify(1, 2, 0);
    assert(m.positionOf(300) == make_pair(-1, -1));
    m.modify(1, 2, NULL_TELEM);
    assert(m.positionOf(NULL_TELEM) == make_pair(1, 2));
}
