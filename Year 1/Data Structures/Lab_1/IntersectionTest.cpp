#include "IntersectionTest.h"
#include "SortedSet.h"
#include "SortedSetIterator.h"
#include <cassert>
#include <cstdio>

bool r3(TComp e1, TComp e2) {
    if (e1 <= e2) {
        return true;
    } else {
        return false;
    }
}

void TestIntersection() {
    printf("Test intersection\n");
    SortedSet s1(r3);
    SortedSet s2(r3);
    s1.add(5);
    s2.add(5);
    s1.add(1);
    s2.add(3);
    s1.add(10);
    s2.add(11);
    s1.intersection(s2);
    assert(s1.search(5));
    assert(!s1.search(1));
    assert(!s1.search(10));
}