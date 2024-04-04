cmake_minimum_required(VERSION 3.24)
project(Lab_2)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(Lab_2
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        ShortTest.cpp
        ShortTest.h
        SortedBag.cpp
        SortedBag.h
        SortedBagIterator.cpp
        SortedBagIterator.h RemoveAllTest.h RemoveAllTest.cpp)
