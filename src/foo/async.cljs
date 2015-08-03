(ns foo.async
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [put! chan <!]]))

(defn greeting [n]
  (prn (str "Ordinary hello to you, " n)))

(defn wait [ms val]
  (let [c (chan)]
    (js/setTimeout (fn [] (put! c val)) ms)
    c))

(defn async-greeting [n]
  (go
    (prn (str "Hello from a go, " (<! (wait 1000 n))))))