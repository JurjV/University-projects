#include "RemoveAllTest.h"
#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <cassert>
#include <cstdio>

bool relation4(TComp r1, TComp r2) {
    return r1 <= r2;
}

void TestRemoveAll(){
    printf("Test Remove All\n");
    SortedBag sb(relation4);
    assert(sb.isEmpty() == true);
    assert(sb.size() == 0);
    sb.add(3);
    sb.add(3);
    sb.add(3);
    sb.add(5);
    sb.add(0);
    sb.add(10);
    int num=sb.removeAll();
    assert(num==1);
    assert(sb.size()==3);
    sb.add(5);
    num=sb.removeAll();
    assert(num==1);
    assert(sb.size()==2);
}
