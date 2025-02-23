#include <ostream>
#include <string>
#include <vector>

using matrix = std::vector<std::vector<double>>;
using vector = std::vector<double>;

size_t parse_vector(std::string const& s, size_t start, vector& res);
vector parse_vector(std::string const& s);
matrix parse_matrix(std::string const& s);

std::ostream& print_vector(std::ostream& os, vector const& v);
std::ostream& print_matrix(std::ostream& os, matrix const& m);

matrix add(matrix const& a, matrix const& b);
matrix sub(matrix const& a, matrix const& b);
matrix prod(matrix const& a, matrix const& b);
matrix div(matrix const& a, matrix const& b);

matrix inverse(matrix m);
matrix product(matrix const& a, matrix const& b);
matrix division(matrix const& a, matrix const& b);
