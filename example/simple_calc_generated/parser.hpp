#pragma once

#include "tree.hpp"

#include <cassert>
#include <cctype>
#include <format>
#include <istream>
#include <stdexcept>
#include <string>
#include <regex>
#include <vector>

using namespace std::string_literals;

enum class token : { NUM, PLUS, MINUS, MUL, DIV, LP, RP, _END };

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
    buf = ch;
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
    while (is.good() && std::isspace(static_cast<unsigned char>(ch))) {
      next_char();
    }
    if (is.eof()) {
      t = token::END;
      return t;
    }
    if (!is) {
      throw lexer_exception{"the stream is corrupted or closed unexpectedly "
                            "(pos=" + std::to_string(pos) + ')'};
    }
    auto match = std::smatch{};
    do {
      buf += ch;
      if (std::regex_match(buf, match, rNUM)) {
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
      if (is.good() && std::isspace(static_cast<unsigned char>(ch))) {
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

  static const std::regex rNUM("[0-9]+");

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
  lexer lexer;

  struct e : node {
    e() : node("e") {
      switch (lexer.cur_token()) {
      case token::LP:
      case token::NUM: {
        auto _1 = t();
        children.emplace_back(_1);
        auto _2 = ep();
        children.emplace_back(_2);
        break;
      }
      default:
        throw parser_exception{"e: unexpected token " + lexer.info()};
      }
    }
  };

  struct ep : node {
    ep() : node("ep") {
      switch (lexer.cur_token()) {
      case token::PLUS: {
        auto _1 = lexer.cur_token_val();
        children.emplace_back(_1);
        auto _2 = t();
        children.emplace_back(_2);
        auto _3 = ep();
        children.emplace_back(_3);
        break;
      }
      case token::MINUS: {
        auto _1 = lexer.cur_token_val();
        children.emplace_back(_1);
        auto _2 = t();
        children.emplace_back(_2);
        auto _3 = ep();
        children.emplace_back(_3);
        break;
      }
      case token::_END:
      case token::RP: {
        empty = true;
        break;
      }
      default:
        throw parser_exception{"ep: unexpected token " + lexer.info()};
      }
    }
  };

  struct t : node {
    t() : node("t") {
      switch (lexer.cur_token()) {
      case token::LP:
      case token::NUM: {
        auto _1 = f();
        children.emplace_back(_1);
        auto _2 = tp();
        children.emplace_back(_2);
        break;
      }
      default:
        throw parser_exception{"t: unexpected token " + lexer.info()};
      }
    }
  };

  struct tp : node {
    tp() : node("tp") {
      switch (lexer.cur_token()) {
      case token::MUL: {
        auto _1 = lexer.cur_token_val();
        children.emplace_back(_1);
        auto _2 = f();
        children.emplace_back(_2);
        auto _3 = tp();
        children.emplace_back(_3);
        break;
      }
      case token::DIV: {
        auto _1 = lexer.cur_token_val();
        children.emplace_back(_1);
        auto _2 = f();
        children.emplace_back(_2);
        auto _3 = tp();
        children.emplace_back(_3);
        break;
      }
      case token::_END:
      case token::RP:
      case token::PLUS:
      case token::MINUS: {
        empty = true;
        break;
      }
      default:
        throw parser_exception{"tp: unexpected token " + lexer.info()};
      }
    }
  };

  struct f : node {
    f() : node("f") {
      switch (lexer.cur_token()) {
      case token::NUM: {
        auto _1 = lexer.cur_token_val();
        children.emplace_back(_1);
        break;
      }
      case token::LP: {
        auto _1 = lexer.cur_token_val();
        children.emplace_back(_1);
        auto _2 = e();
        children.emplace_back(_2);
        auto _3 = lexer.cur_token_val();
        children.emplace_back(_3);
        break;
      }
      default:
        throw parser_exception{"f: unexpected token " + lexer.info()};
      }
    }
  };

public:
  parser(std::istream& is) : lexer(is) {}

  node parse() {
    lexer.next_token();
    return e();
  }
};
