(ns foo.eval
  (:require [cljs.js :as cljs]))

(enable-console-print!)

(defn def-in-existing-ns []
  (cljs/eval (cljs/empty-state)
             '(def x 1)
             {:ns 'foo.other
              :eval    (fn [js]
                         (prn (:source js)) ;".x = 1"
                         (cljs/js-eval js))
              :verbose true
              :context :expr}
             prn))

(defn def-in-new-namespace []
  (cljs/eval (cljs/empty-state)
             '(do (ns foo.totally-new)
                  (def x 1))
             {:eval    (fn [js]
                         (prn (:source js)) ;"(function (){\ngoog.provide('foo.totally_new');\ngoog.require('cljs.core');\n\ncljs.user.x = 1;\n})()\n"
                         (cljs/js-eval js))
              :verbose true
              :context :expr}
              prn))


