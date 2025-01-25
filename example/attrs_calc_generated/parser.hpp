#pragma once

#include "tree.hpp"

#include <cctype>
#include <istream>
#include <stdexcept>
#include <string>
#include <regex>
#include <vector>

using namespace std::string_literals;

enum class token { NUM, PLUS, MINUS, MUL, DIV, LP, RP, _END };

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
      if (greedy_regex_match(rNUM)) {
        t = token::NUM;
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
      if (buf == "*") {
        t = token::MUL;
        break;
      }
      if (buf == "/") {
        t = token::DIV;
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
      if (is.good() && isspace()) {
        throw lexer_exception{"expected token, got space at pos " + std::to_string(pos)};
      }
      if (is.eof()) {
        throw lexer_exception{"unknown lexeme " + info()};
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

  const std::regex rNUM{"[0-9]+(\\.[0-9]*)?"};

  std::istream& is;
  std::string lexeme;
  std::string buf;
  size_t pos{0};
  token t;
  char ch;
};

struct parser_exception : std::runtime_error {
  using std::runtime_error::runtime_error;
};

class parser {
  struct lexer lexer;

  struct e : node {
    e() : node("e") {};
    double val;
  };
  e e() {
    auto _res = (struct e){};
    switch (lexer.cur_token()) {
    case token::LP:
    case token::NUM: {
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
    double val;
  };
  ep ep(double acc) {
    auto _res = (struct ep){};
    switch (lexer.cur_token()) {
    case token::PLUS: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = t();
      _res.children.emplace_back(_2);
      auto _3 = ep(acc + _2.val);
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
      auto _3 = ep(acc - _2.val);
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
    double val;
  };
  t t() {
    auto _res = (struct t){};
    switch (lexer.cur_token()) {
    case token::LP:
    case token::NUM: {
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
    double val;
  };
  tp tp(double acc) {
    auto _res = (struct tp){};
    switch (lexer.cur_token()) {
    case token::MUL: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = f();
      _res.children.emplace_back(_2);
      auto _3 = tp(acc * _2.val);
      _res.children.emplace_back(_3);
      _res.val = _3.val;
      break;
    }
    case token::DIV: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = f();
      _res.children.emplace_back(_2);
      auto _3 = tp(acc / _2.val);
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
    double val;
  };
  f f() {
    auto _res = (struct f){};
    switch (lexer.cur_token()) {
    case token::NUM: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      _res.val = stod(_1);
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
      //example of multi-line code;
      _res.val = _2.val;
      _res.val += 0.0;
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
