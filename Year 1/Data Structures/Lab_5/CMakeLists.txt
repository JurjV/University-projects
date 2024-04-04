cmake_minimum_required(VERSION 3.25)
project(Lab_5)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(Lab_5
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        ShortTest.cpp
        ShortTest.h
        SortedBag.cpp
        SortedBag.h
        SortedBagIterator.cpp
        SortedBagIterator.h)
