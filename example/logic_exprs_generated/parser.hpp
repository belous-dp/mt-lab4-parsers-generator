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

enum class token { VAR, OR, XOR, AND, SHL, SHR, NOT, LP, RP, _END };

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
    return "'"s + input.front() + "' at pos " + std::to_string(pos - 1);
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
    if (auto m = ctre::starts_with<"[a-zA-Z_][a-zA-Z_0-9]*">(input)) {
        t = token::VAR;
        take(m.size());
    } else if (input.starts_with("|")) {
        t = token::OR;
        take(1);
    } else if (input.starts_with("^")) {
        t = token::XOR;
        take(1);
    } else if (input.starts_with("&")) {
        t = token::AND;
        take(1);
    } else if (input.starts_with("<<")) {
        t = token::SHL;
        take(2);
    } else if (input.starts_with(">>")) {
        t = token::SHR;
        take(2);
    } else if (input.starts_with("~")) {
        t = token::NOT;
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
    case token::NOT:
    case token::LP:
    case token::VAR: {
      auto _1 = o();
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
    case token::OR: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = o();
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

  struct o : node {
    o() : node("o") {};
  };
  o o() {
    auto _res = (struct o){};
    switch (lexer.cur_token()) {
    case token::NOT:
    case token::LP:
    case token::VAR: {
      auto _1 = x();
      _res.children.emplace_back(_1);
      auto _2 = op();
      _res.children.emplace_back(_2);
      break;
    }
    default:
      throw invalid_token("o");
    }
    return _res;
  }

  struct op : node {
    op() : node("op") {};
  };
  op op() {
    auto _res = (struct op){};
    switch (lexer.cur_token()) {
    case token::XOR: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = x();
      _res.children.emplace_back(_2);
      auto _3 = op();
      _res.children.emplace_back(_3);
      break;
    }
    case token::_END:
    case token::OR:
    case token::RP: {
      _res.empty = true;
      break;
    }
    default:
      throw invalid_token("op");
    }
    return _res;
  }

  struct x : node {
    x() : node("x") {};
  };
  x x() {
    auto _res = (struct x){};
    switch (lexer.cur_token()) {
    case token::NOT:
    case token::LP:
    case token::VAR: {
      auto _1 = a();
      _res.children.emplace_back(_1);
      auto _2 = xp();
      _res.children.emplace_back(_2);
      break;
    }
    default:
      throw invalid_token("x");
    }
    return _res;
  }

  struct xp : node {
    xp() : node("xp") {};
  };
  xp xp() {
    auto _res = (struct xp){};
    switch (lexer.cur_token()) {
    case token::AND: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = a();
      _res.children.emplace_back(_2);
      auto _3 = xp();
      _res.children.emplace_back(_3);
      break;
    }
    case token::_END:
    case token::OR:
    case token::XOR:
    case token::RP: {
      _res.empty = true;
      break;
    }
    default:
      throw invalid_token("xp");
    }
    return _res;
  }

  struct a : node {
    a() : node("a") {};
  };
  a a() {
    auto _res = (struct a){};
    switch (lexer.cur_token()) {
    case token::NOT:
    case token::LP:
    case token::VAR: {
      auto _1 = s();
      _res.children.emplace_back(_1);
      auto _2 = ap();
      _res.children.emplace_back(_2);
      break;
    }
    default:
      throw invalid_token("a");
    }
    return _res;
  }

  struct ap : node {
    ap() : node("ap") {};
  };
  ap ap() {
    auto _res = (struct ap){};
    switch (lexer.cur_token()) {
    case token::SHL: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = s();
      _res.children.emplace_back(_2);
      auto _3 = ap();
      _res.children.emplace_back(_3);
      break;
    }
    case token::SHR: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = s();
      _res.children.emplace_back(_2);
      auto _3 = ap();
      _res.children.emplace_back(_3);
      break;
    }
    case token::_END:
    case token::OR:
    case token::AND:
    case token::XOR:
    case token::RP: {
      _res.empty = true;
      break;
    }
    default:
      throw invalid_token("ap");
    }
    return _res;
  }

  struct s : node {
    s() : node("s") {};
  };
  s s() {
    auto _res = (struct s){};
    switch (lexer.cur_token()) {
    case token::NOT: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      auto _2 = n();
      _res.children.emplace_back(_2);
      break;
    }
    case token::LP:
    case token::VAR: {
      auto _1 = n();
      _res.children.emplace_back(_1);
      break;
    }
    default:
      throw invalid_token("s");
    }
    return _res;
  }

  struct n : node {
    n() : node("n") {};
  };
  n n() {
    auto _res = (struct n){};
    switch (lexer.cur_token()) {
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
    case token::VAR: {
      auto _1 = lexer.cur_token_val();
      _res.children.emplace_back(_1);
      lexer.next_token();
      break;
    }
    default:
      throw invalid_token("n");
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
