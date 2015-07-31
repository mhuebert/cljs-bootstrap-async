(ns foo.core
  (:require [foo.examples]))

(defn hello [] (.log js/console "hello was called from foo.core"))