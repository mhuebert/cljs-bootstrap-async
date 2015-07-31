(ns foo.examples
  (:require [cljs.js :as cljs]
            [foo.z]))

(enable-console-print!)

(def log-and-eval #(do (.log js/console (clj->js %)) (cljs/js-eval %)))

; def a var in an existing namespace. defs on null
(cljs/eval (cljs/empty-state)
           '(def x 1)
           {:ns      'foo.core
            :eval    log-and-eval                           ;".x = 1"
            :verbose true
            :context :expr}
            prn)

; create a namespace and def a var in it. var goes in cljs.user
(cljs/eval (cljs/empty-state)
           '(do (ns foo.totally-new)
                (def x 1))
           {:eval    log-and-eval                           ;"(function (){↵goog.provide('foo.totally_new');↵goog.require('cljs.core');↵↵cljs.user.x = 1;↵})()↵"
            :verbose true
            :context :expr}
            prn)

; call function in another namespace using :ns option. calls fn on null
(cljs/eval (cljs/empty-state)
           '(hello)
           {:ns      'foo.core
            :eval    log-and-eval                           ;".hello.call(null)"
            :verbose true
            :context :expr}
           prn)