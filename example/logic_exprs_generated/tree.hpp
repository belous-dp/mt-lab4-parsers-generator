#pragma once

#include <cassert>
#include <format>
#include <ostream>
#include <string>
#include <vector>

using namespace std::string_literals;

struct node {
  std::string value;
  std::vector<node> children;
  bool empty;

  node(std::string const& value, bool empty = false) : value(value), empty(empty) {}

  template <typename... Children,
            typename = std::enable_if_t<
                std::conjunction_v<std::is_convertible<Children, node>...>>>
  node(std::string value, Children&&... children) : value(std::move(value)), empty(false) {
    this->children.reserve(sizeof...(Children));
    ([&]{ this->children.emplace_back(std::forward<Children>(children)); }(), ...);
  }

  bool operator==(node const& other) const = default;

  operator std::string() const {
    auto res = '(' + value;
    for (auto const& c : children) {
      res += ' ' + static_cast<std::string>(c);
    }
    res += ')';
    return res;
  }

  friend std::ostream& operator<<(std::ostream& os, node const& self) {
    os << static_cast<std::string>(self);
    return os;
  }

  std::string str_id() const {
    return std::format("_{}", static_cast<void const*>(this));
  }

  enum class graph_mode { FULL, SKIP_EMPTY_NODES };
  void dot_repr(std::back_insert_iterator<std::string>& out, graph_mode mode) const {
    auto id = str_id();
    if (empty) {
      assert(mode == graph_mode::FULL);
      assert(children.empty());
      std::format_to(out,
                      "\t{0} [label=\"{1}\"];\n"
                      "\t{0}_ [label=ε];\n"
                      "\t{0} -> {0}_;\n",
                      id, value);
      return;
    } else if (children.empty() && mode != graph_mode::FULL) {
      std::format_to(out, "\t{{ rank=max; {} [label=\"{}\"]; }}\n", id, value);
      return;
    }
    std::format_to(out, "\t{} [label=\"{}\"];\n", id, value);
    for (auto const& c : children) {
      if (c.empty && mode == graph_mode::SKIP_EMPTY_NODES) {
        continue;
      }
      std::format_to(out, "\t{} -> {};\n", id, c.str_id());
      c.dot_repr(out, mode);
    }
  }

  std::string full_dot_repr(graph_mode mode) const {
    auto res = "digraph {\n"s;
    auto out = std::back_inserter(res);
    if (mode != graph_mode::SKIP_EMPTY_NODES || !empty) {
      dot_repr(out, mode);
    }
    res += "}\n";
    return res;
  }
};