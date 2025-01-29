#pragma once

#include "tree.hpp"

#include <cctype>
#include <istream>
#include <stdexcept>
#include <string>
#include <regex>
#include <vector>

using namespace std::string_literals;

enum class token { VAR, OR, XOR, AND, SHL, SHR, NOT, LP, RP, _END };

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
      if (greedy_regex_match(rVAR)) {
        t = token::VAR;
        break;
      }
      if (buf == "|") {
        t = token::OR;
        break;
      }
      if (buf == "^") {
        t = token::XOR;
        break;
      }
      if (buf == "&") {
        t = token::AND;
        break;
      }
      if (buf == "<<") {
        t = token::SHL;
        break;
      }
      if (buf == ">>") {
        t = token::SHR;
        break;
      }
      if (buf == "~") {
        t = token::NOT;
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

  const std::regex rVAR{"[a-zA-Z_][a-zA-Z_0-9]*"};

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
