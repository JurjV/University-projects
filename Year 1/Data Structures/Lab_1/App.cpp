#include "ShortTest.h"
#include "ExtendedTest.h"
#include "SortedSet.h"
#include "SortedSetIterator.h"
#include "IntersectionTest.h"
#include <iostream>

using namespace std;

int main() {
	testAll();
	testAllExtended();
    TestIntersection();

	cout << "Test end" << endl;
	system("pause");
}