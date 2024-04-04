#include "addIfNotPresentTest.h"
#include <assert.h>

#include "SortedMultiMap.h"
#include "SMMIterator.h"
#include <exception>
#include <vector>
#include <iostream>

bool relation10(TKey cheie1, TKey cheie2) {
    if (cheie1 <= cheie2) {
        return true;
    } else {
        return false;
    }
}

void TestAddIfNotPresent(){
    printf("Test addIfNotPresent\n");
    SortedMultiMap smm1 = SortedMultiMap(relation10);
    SortedMultiMap smm2 = SortedMultiMap(relation10);
    smm1.add(1, 2);
    smm1.add(1, 3);
    smm2.add(2,3);
    smm2.add(2,4);
    assert(smm1.addIfNotPresent(smm2)==2);
    assert(smm1.addIfNotPresent(smm2)==0);
    smm2.add(3,1);
    smm2.add(4,1);
    assert(smm1.addIfNotPresent(smm2)==2);
    assert(smm1.addIfNotPresent(smm2)==0);
    smm2.add(4,2);
    assert(smm1.addIfNotPresent(smm2)==0);
    assert(smm1.size()==6);
}
