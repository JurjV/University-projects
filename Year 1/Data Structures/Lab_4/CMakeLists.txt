cmake_minimum_required(VERSION 3.24)
project(Lab_4)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(Lab_4
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        Matrix.cpp
        Matrix.h
        ShortTest.cpp
        ShortTest.h testPositionOf.h testPositionOf.cpp)
