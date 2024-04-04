#include <iostream>

#include "ShortTest.h"
#include "ExtendedTest.h"
#include "addIfNotPresentTest.h"

int main(){
    testAll();
	testAllExtended();
    TestAddIfNotPresent();

    std::cout<<"Finished SMM Tests!"<<std::endl;
	system("pause");
}
