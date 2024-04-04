#include "SortedBag.h"
#include "SortedBagIterator.h"
#include "RemoveAllTest.h"
#include <iostream>
#include "ShortTest.h"
#include "ExtendedTest.h"

using namespace std;

int main() {
	testAll();
	testAllExtended();
    TestRemoveAll();
	
	printf("Test over!\n");
	system("pause");
}
