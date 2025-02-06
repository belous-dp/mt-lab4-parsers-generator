#pragma once

#include "tree.hpp"
#include "ctre.hpp"

#include <cctype>
#include <istream>
#include <stdexcept>
#include <string>
#include <string_view>
#include <vector>

using namespace std::string_literals;

enum class token { NUM, PLUS, MINUS, MUL, DIV, LP, RP, _END };

struct lexer_exception : std::runtime_error {
  using std::runtime_error::runtime_error;
};

struct lexer {

  lexer(std::istream& is) {
    if (!is) {
      throw lexer_exception{"stream is not opened"};
    }
    buf_ = gulp(is);
    input = buf_;
  }

  token cur_token() const noexcept {
    return t;
  }

  std::string cur_token_val() const noexcept {
    return std::string{lexeme};
  }

  std::string info() const noexcept {
    return "'"s + input.front() + "' at pos " + std::to_string(pos);
  }

  token next_token() {
    while (!is_eof() && is_space()) {
      input.remove_prefix(1);
      pos++;
    }
    if (is_eof()) {
      t = token::_END;
      return t;
    }
    if (auto m = ctre::starts_with<"[0-9]+">(input)) {
        t = token::NUM;
        take(m.size());
    } else if (input.starts_with("+")) {
        t = token::PLUS;
        take(1);
    } else if (input.starts_with("-")) {
        t = token::MINUS;
        take(1);
    } else if (input.starts_with("*")) {
        t = token::MUL;
        take(1);
    } else if (input.starts_with("/")) {
        t = token::DIV;
        take(1);
    } else if (input.starts_with("(")) {
        t = token::LP;
        take(1);
    } else if (input.starts_with(")")) {
        t = token::RP;
        take(1);
    }
    return t;
  }

private:
  std::string gulp(std::istream &in) {
    std::string ret;
    char buffer[4096];
    while (in.read(buffer, sizeof(buffer)))
      ret.append(buffer, sizeof(buffer));
    ret.append(buffer, in.gcount());
    return ret;
  }

  bool is_eof() const noexcept {
    return input.empty();
  }

  bool is_space() const noexcept {
    return std::isspace(static_cast<unsigned char>(input.front()));
  }

  void take(size_t n) {
    lexeme = input.substr(0, n);
    input.remove_prefix(n);
    pos += n;
  }

  std::string buf_;
  std::string_view input;
  std::string_view lexeme;
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
  };
  e e() {
    auto _res = (struct e){};
    switch (lexer.cur_token()) {
    case token::LP:
    case token::NUM: {
      auto _1 = t();
      _res.children.emplace_back(_1);
      auto _2 = ep();
      _res.children.emplace_back(_2);
      break;
    }
    default:
      throw invalid_token("e");
    }
    return _res;
  }

  struct ep : node {
    ep() : node("ep") {};
  };
  ep ep() {
    auto _res = (struct ep){};
    switch (lexer.cur_token()) {
    case token::PLUS: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = t();
      _res.children.emplace_back(_2);
      auto _3 = ep();
      _res.children.emplace_back(_3);
      break;
    }
    case token::MINUS: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = t();
      _res.children.emplace_back(_2);
      auto _3 = ep();
      _res.children.emplace_back(_3);
      break;
    }
    case token::_END:
    case token::RP: {
      _res.empty = true;
      break;
    }
    default:
      throw invalid_token("ep");
    }
    return _res;
  }

  struct t : node {
    t() : node("t") {};
  };
  t t() {
    auto _res = (struct t){};
    switch (lexer.cur_token()) {
    case token::LP:
    case token::NUM: {
      auto _1 = f();
      _res.children.emplace_back(_1);
      auto _2 = tp();
      _res.children.emplace_back(_2);
      break;
    }
    default:
      throw invalid_token("t");
    }
    return _res;
  }

  struct tp : node {
    tp() : node("tp") {};
  };
  tp tp() {
    auto _res = (struct tp){};
    switch (lexer.cur_token()) {
    case token::MUL: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = f();
      _res.children.emplace_back(_2);
      auto _3 = tp();
      _res.children.emplace_back(_3);
      break;
    }
    case token::DIV: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = f();
      _res.children.emplace_back(_2);
      auto _3 = tp();
      _res.children.emplace_back(_3);
      break;
    }
    case token::_END:
    case token::RP:
    case token::PLUS:
    case token::MINUS: {
      _res.empty = true;
      break;
    }
    default:
      throw invalid_token("tp");
    }
    return _res;
  }

  struct f : node {
    f() : node("f") {};
  };
  f f() {
    auto _res = (struct f){};
    switch (lexer.cur_token()) {
    case token::NUM: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
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
    return parser_exception{n + ": " + reason};
  }

public:
  parser(std::istream& is) : lexer(is) {}

  struct e parse() {
    lexer.next_token();
    return e();
  }
};
