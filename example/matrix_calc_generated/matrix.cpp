#include "matrix.hpp"

#include <cctype>
#include <functional>
#include <iostream>
#include <limits>
#include <stdexcept>

using namespace std::string_literals;

namespace {
size_t skip_spaces(std::string const& s, size_t start) {
  while (start < s.size() && std::isspace(static_cast<unsigned char>(s[start]))) {
    start++;
  }
  return start;
}
} // namespace

size_t parse_vector(std::string const& s, size_t start, vector& res) {
  start = skip_spaces(s, start);
  if (start >= s.size() || s[start] != '[') {
    throw std::invalid_argument{"vector must start with '['"};
  }
  auto buf = ""s;
  auto i = start + 1;
  for (; i < s.size(); ++i) {
    if (s[i] == ']' || s[i] == ',' || s[i] == ' ') {
      if (!buf.empty() || i != start + 1) {
        if (buf.empty()) {
          res.push_back(0);
        } else {
          res.push_back(stod(buf));
        }
        buf.clear();
      }
      if (s[i] == ']') {
        i++;
        break;
      }
      while (i < s.size() && (s[i] == ' ' || s[i] == ',')) {
        i++;
      }
      i--;
    }
    if (s[i] != ' ' && s[i] != ',') {
      buf += s[i];
    }
  }
  return i;
}

vector parse_vector(std::string const& s) {
  auto res = vector{};
  parse_vector(s, 0, res);
  return res;
}

matrix parse_matrix(std::string const& s) {
  auto start = skip_spaces(s, 0);
  if (start >= s.size() || s[start] != '[') {
    throw std::invalid_argument{"matrix must start with '['"};
  }
  auto res = matrix{};
  for (size_t i = start + 1; i + 1 < s.size();) {
    res.push_back(vector{});
    i = parse_vector(s, i, res.back());
    while (i < s.size() && (s[i] == ',' || s[i] == ' ')) {
      i++;
    }
  }
  return res;
}

std::ostream& print_vector(std::ostream& os, vector const& v) {
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
  return os;
}

std::ostream& print_matrix(std::ostream& os, matrix const& m) {
  os << '\n';
  os << '[';
  auto first = true;
  for (auto const& v : m) {
    if (!first) {
      os << ", ";
      os << "\n ";
    }
    first = false;
    print_vector(os, v);
  }
  os << ']';
  return os;
}

namespace {
matrix elemwise(matrix a, matrix const& b, auto op) {
  if (a.empty()) {
    if (b.empty()) {
      return {};
    }
  }
  if (a.size() != b.size() || b.empty() || a[0].size() != b[0].size()) {
    throw std::invalid_argument{"matrices sizes not equal"};
  }
  for (size_t i = 0; i < a.size(); ++i) {
    if (a[i].size() != a[0].size() || b[i].size() != a[0].size()) {
      throw std::invalid_argument{"invalid matrix (not rectangular)"};
    }
    for (size_t j = 0; j < b[i].size(); ++j) {
      a[i][j] = op(a[i][j], b[i][j]);
    }
  }
  return a;
}
} // namespace

matrix add(matrix const& a, matrix const& b) {
  return elemwise(a, b, std::plus<double>{});
}

matrix sub(matrix const& a, matrix const& b) {
  return elemwise(a, b, std::minus<double>{});
}

matrix prod(matrix const& a, matrix const& b) {
  return elemwise(a, b, std::multiplies<double>{});
}

matrix div(matrix const& a, matrix const& b) {
    return elemwise(a, b, std::divides<double>{});
}

matrix product(matrix const& a, matrix const& b) {
  if (a.empty()) {
    if (b.empty()) {
      return {};
    }
  }
  auto c1 = a[0].size();
  auto r2 = b.size();
  if (c1 != r2) {
    throw std::invalid_argument{"matrices sizes are invalid: C1 != R2"};
  }
  auto r1 = a.size();
  auto c2 = b[0].size();
  auto res = matrix(r1, vector(c2));
  for (size_t i = 0; i < r1; ++i) {
    for (size_t j = 0; j < c2; ++j) {
      for (size_t k = 0; k < c1; ++k) {
        res[i][j] += a[i][k] * b[k][j];
      }
    }
  }
  return res;
}

matrix inverse(matrix m) {
  if (m.empty()) {
    throw std::runtime_error{"matrix is empty"};
  }
  auto n = m.size();
  auto res = matrix(n, vector(n));
  for (size_t i = 0; i < n; ++i) {
    res[i][i] = 1;
  }
  auto det = 1.0;
  auto const EPS = std::numeric_limits<double>::epsilon();
  // Gauiss-Jordan algorithm
  // create lower triangular matrix
  // on the second step clear numbers above the diagonal
  for (size_t i = 0; i < n; ++i) {
    if (m[i].size() != m.size()) {
      throw std::runtime_error{"matrix is not square"};
    }
    // find max element below, in the i'th column
    auto r = i;
    auto maxv = std::abs(m[i][i]);
    for (size_t j = i + 1; j < n; ++j) {
      auto val = std::abs(m[j][i]);
      if (val > maxv) {
        r = j;
        maxv = val;
      }
    }
    if (r != i) {
      // swap rows i, r
      for (size_t j = 0; j < n; ++j) {
        std::swap(m[i][j], m[r][j]); // iterations 0..i are useless, actually
        std::swap(res[i][j], res[r][j]);
      }
      det = -det;
      // std::cerr << "step " << i + 1 << ": matrices after swapping rows " << i << ' ' << r << '\n';
      // print_matrix(std::cerr, m) << std::endl;
      // print_matrix(std::cerr, res) << std::endl;
    }
    if (maxv < EPS) {
      throw std::runtime_error{"matrix is not invertible"};
    }
    auto pivot = m[i][i];
    for (size_t j = 0; j < n; ++j) {
      if (j == i) {
        continue;
      }
      auto mult = m[j][i] / pivot;
      for (size_t k = 0; k < n; ++k) {
        m[j][k] -= mult * m[i][k]; // iterations 0..i are useless, actually
        res[j][k] -= mult * res[i][k];
      }
    }
    for (size_t j = 0; j < n; ++j) {
      m[i][j] /= pivot; // iterations 0..i are useless, actually
      res[i][j] /= pivot;
    }
    det *= pivot;
    // std::cerr << "matrices after step " << i + 1 << '\n';
    // print_matrix(std::cerr, m) << std::endl;
    // print_matrix(std::cerr, res) << std::endl;
  }
  return res;
}

matrix division(matrix const& a, matrix const& b) {
    return product(a, inverse(b));
}
