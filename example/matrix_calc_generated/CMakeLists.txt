cmake_minimum_required(VERSION 3.20)

project(parser)

set(CMAKE_CXX_STANDARD 20)

add_executable(main main.cpp matrix.cpp)

add_compile_options(-Wall -Wextra -Wpedantic)

if (CMAKE_BUILD_TYPE MATCHES "Debug")
    add_compile_options(-D_GLIBCXX_DEBUG)
endif ()
