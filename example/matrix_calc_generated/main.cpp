#include "parser.hpp"

#include <iostream>
#include <fstream>

void print_vector(std::ostream& os, vector const& v) {
  os << '[';
  auto first = true;
  for (auto const& e : v) {
    if (!first) {
      os << ' ';
    }
    first = false;
    os << e;
  }
  os << ']';
}

void print_matrix(std::ostream& os, matrix const& m) {
  os << '[';
  auto first = true;
  for (auto const& v : m) {
    if (!first) {
      os << ", ";
    }
    first = false;
    print_vector(os, v);
  }
  os << ']';
}

int main() {
  try {
    auto res = parser(std::cin).parse();
    std::cout << "Successfully parsed the expression. Text representation:\n" << res << std::endl;
    std::cout << "Result: ";
    print_matrix(std::cout, res.val);
    std::cout << std::endl;
    auto extport = [&res](node::graph_mode mode, char const* fname) {
      auto fs = std::ofstream(fname);
      fs << res.full_dot_repr(mode);
      fs.close();
      std::cout << "\nDOT (Graphviz) AST representation is saved to " << fname << std::endl;
    };
    extport(node::graph_mode::FULL, "full_ast.dot");
    extport(node::graph_mode::SKIP_EMPTY_NODES, "prettified_ast.dot");
    std::cout << "\nNow run `dot -Tsvg -O full_ast.dot prettified_ast.dot` "
                 "(Graphviz package need to be installed)"
              << std::endl;
  } catch (const std::exception& e) {
    std::cerr << "error happened: " << e.what() << std::endl;
  }
}