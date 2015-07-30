(ns foo.core
  ^:figwheel-always
  (:require [foo.other]
            [foo.eval]))

(foo.eval/def-in-existing-ns)
(foo.eval/def-in-new-namespace)


