(ns foo.eval
  (:require [cljs.js :as cljs]))

(enable-console-print!)

(defn try-def []
  (cljs/eval (cljs/empty-state)
             '(do
                (enable-console-print!)
                (def x 1)
                (prn "x should be here: " x))
             {:eval    (fn [js]
                         (prn (:source js))
                         (cljs/js-eval js))
              :context :expr
              :ns      'foo.core}
              prn))




