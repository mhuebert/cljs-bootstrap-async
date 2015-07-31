(ns foo.core
  (:require [foo.other]))

(defn hello [] (.log js/console "hello was called from foo.core"))