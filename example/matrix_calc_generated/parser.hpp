#pragma once

#include "tree.hpp"

#include <cctype>
#include <istream>
#include <stdexcept>
#include <string>
#include <regex>
#include <vector>

using namespace std::string_literals;

enum class token { MATRIX, PLUS, MINUS, MMUL, MUL, LP, RP, _END };

struct lexer_exception : std::runtime_error {
  using std::runtime_error::runtime_error;
};

struct lexer {
  static constexpr size_t MAX_BUF_SIZE = 10000;

  lexer(std::istream& is) : is(is) {
    if (!is) {
      throw lexer_exception{"stream is not opened"};
    }
    next_char();
  }

  token cur_token() const noexcept {
    return t;
  }

  std::string cur_token_val() const noexcept {
    return lexeme;
  }

  std::string info() const noexcept {
    return "'" + buf + "' at pos " + std::to_string(pos - 1);
  }

  token next_token() {
    while (is.good() && isspace()) {
      next_char();
    }
    if (is.eof()) {
      t = token::_END;
      return t;
    }
    if (!is) {
      throw lexer_exception{"the stream is corrupted or closed unexpectedly "
                            "(pos=" + std::to_string(pos) + ')'};
    }
    do {
      buf += ch;
      if (greedy_regex_match(rMATRIX)) {
        t = token::MATRIX;
        break;
      }
      if (buf == "+") {
        t = token::PLUS;
        break;
      }
      if (buf == "-") {
        t = token::MINUS;
        break;
      }
      if (buf == "**") {
        t = token::MMUL;
        break;
      }
      if (buf == "*") {
        t = token::MUL;
        break;
      }
      if (buf == "(") {
        t = token::LP;
        break;
      }
      if (buf == ")") {
        t = token::RP;
        break;
      }
      next_char();
      if (!is) {
        throw lexer_exception{"the stream failed or closed unexpectedly while matching " + info()};
      }
      if (is.eof()) {
        throw lexer_exception{"unknown lexeme " + info()};
      }
      if (isspace()) {
        throw lexer_exception{"expected token, got space at pos " + std::to_string(pos)};
      }
    } while (buf.size() < MAX_BUF_SIZE);
    if (buf.size() >= MAX_BUF_SIZE) {
      throw lexer_exception{"unmatched lexeme size exceeded " + std::to_string(MAX_BUF_SIZE)};
    }
    lexeme = std::move(buf);
    buf = "";
    next_char();
    return t;
  }

private:
  void next_char() {
    if (is.get(ch)) {
      pos++;
    }
  }

  bool isspace() const noexcept {
    return std::isspace(static_cast<unsigned char>(ch));
  }

  bool greedy_regex_match(const std::regex& r) {
    if (!std::regex_match(buf, r)) {
      return false;
    }
    do {
      next_char();
      buf += ch;
    } while (is.good() && !isspace() && std::regex_match(buf, r));
    buf.pop_back();
    is.unget();
    pos--;
    return true;
  }

  const std::regex rMATRIX{"\\[\\[ .*? \\]\\]"};

  std::istream& is;
  std::string lexeme;
  std::string buf;
  size_t pos{0};
  token t;
  char ch;
};


#include <functional>
#include <stdexcept>
#include <string>
#include <vector>
#include <variant>

using namespace std::string_literals;

using matrix = std::vector<std::vector<double>>;
using vector = std::vector<double>;
using atype = std::variant<double, vector, matrix>;

size_t parse_vector(std::string const& s, size_t start, vector& res) {
  if (s[start] != '[') {
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
  auto res = matrix{};
  for (size_t i = 1; i + 1 < s.size();) {
    res.push_back(vector{});
    i = parse_vector(s, i, res.back());
    while (i < s.size() && (s[i] == ',' || s[i] == ' ')) {
      i++;
    }
  }
  return res;
}

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
    for (size_t j = 0; j < b.size(); ++j) {
      a[i][j] = op(a[i][j], b[i][j]);
    }
  }
  return a;
}

matrix add(matrix const& a, matrix const& b) {
  return elemwise(a, b, std::plus<double>{});
}

matrix sub(matrix const& a, matrix const& b) {
  return elemwise(a, b, std::minus<double>{});
}

matrix prod(matrix const& a, matrix const& b) {
  return elemwise(a, b, std::multiplies<double>{});
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

struct parser_exception : std::runtime_error {
  using std::runtime_error::runtime_error;
};

class parser {
  struct lexer lexer;

  struct e : node {
    e() : node("e") {};
    matrix val;
  };
  e e() {
    auto _res = (struct e){};
    switch (lexer.cur_token()) {
    case token::MATRIX:
    case token::LP: {
      auto _1 = t();
      _res.children.emplace_back(_1);
      auto _2 = ep(_1.val);
      _res.children.emplace_back(_2);
      _res.val = _2.val;
      break;
    }
    default:
      throw invalid_token("e");
    }
    return _res;
  }

  struct ep : node {
    ep() : node("ep") {};
    matrix val;
  };
  ep ep(matrix acc) {
    auto _res = (struct ep){};
    switch (lexer.cur_token()) {
    case token::PLUS: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = t();
      _res.children.emplace_back(_2);
      auto _3 = ep(add(acc, _2.val));
      _res.children.emplace_back(_3);
      _res.val = _3.val;
      break;
    }
    case token::MINUS: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = t();
      _res.children.emplace_back(_2);
      auto _3 = ep(sub(acc, _2.val));
      _res.children.emplace_back(_3);
      _res.val = _3.val;
      break;
    }
    case token::_END:
    case token::RP: {
      _res.empty = true;
      _res.val = acc;
      break;
    }
    default:
      throw invalid_token("ep");
    }
    return _res;
  }

  struct t : node {
    t() : node("t") {};
    matrix val;
  };
  t t() {
    auto _res = (struct t){};
    switch (lexer.cur_token()) {
    case token::MATRIX:
    case token::LP: {
      auto _1 = f();
      _res.children.emplace_back(_1);
      auto _2 = tp(_1.val);
      _res.children.emplace_back(_2);
      _res.val = _2.val;
      break;
    }
    default:
      throw invalid_token("t");
    }
    return _res;
  }

  struct tp : node {
    tp() : node("tp") {};
    matrix val;
  };
  tp tp(matrix acc) {
    auto _res = (struct tp){};
    switch (lexer.cur_token()) {
    case token::MMUL: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = f();
      _res.children.emplace_back(_2);
      auto _3 = tp(product(acc, _2.val));
      _res.children.emplace_back(_3);
      _res.val = _3.val;
      break;
    }
    case token::MUL: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = f();
      _res.children.emplace_back(_2);
      auto _3 = tp(prod(acc, _2.val));
      _res.children.emplace_back(_3);
      _res.val = _3.val;
      break;
    }
    case token::_END:
    case token::RP:
    case token::PLUS:
    case token::MINUS: {
      _res.empty = true;
      _res.val = acc;
      break;
    }
    default:
      throw invalid_token("tp");
    }
    return _res;
  }

  struct f : node {
    f() : node("f") {};
    matrix val;
  };
  f f() {
    auto _res = (struct f){};
    switch (lexer.cur_token()) {
    case token::MATRIX: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      _res.val = parse_matrix(_1);
      break;
    }
    case token::LP: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = e();
      _res.children.emplace_back(_2);
      auto _3 = lexer.cur_token_val();
      _res.children.emplace_back(_3);
      lexer.next_token();
      _res.val = _2.val;
      break;
    }
    default:
      throw invalid_token("f");
    }
    return _res;
  }

  parser_exception invalid_token(std::string&& n) {
    auto reason = lexer.cur_token() == token::_END
                      ? "expected a token, but got EOF"
                      : ("unexpected token " + lexer.info());
    return parser_exception{n + reason};
  }

public:
  parser(std::istream& is) : lexer(is) {}

  struct e parse() {
    lexer.next_token();
    return e();
  }
};
