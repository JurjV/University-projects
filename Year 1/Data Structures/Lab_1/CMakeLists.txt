cmake_minimum_required(VERSION 3.24)
project(Lab_1)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(Lab_1
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        ShortTest.cpp
        ShortTest.h
        SortedSet.cpp
        SortedSet.h
        SortedSetIterator.cpp
        SortedSetIterator.h
        IntersectionTest.cpp
        IntersectionTest.h)
