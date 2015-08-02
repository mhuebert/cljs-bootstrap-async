(ns foo.core
  (:require [cljs.js :as cljs]
            [cljs.pprint :refer [pprint]]
            [goog.net.XhrIo :as xhr]))

(enable-console-print!)

(defn load-source [{:keys [_ macros path] :as x} cb]
      (let [path (str "resources/public/" path "." (if macros "clj" "cljs"))]
           (prn path)
           (xhr/send path (fn [e]
                              (let [source (-> e .-target .getResponseText)]
                                   (cb {:lang :clj
                                        :source source}))))))

(cljs/eval
  (cljs/empty-state)
  '(ns foo.some-async-ns
     (:require-macros [cljs.core.async.macros :refer [go]])
     (:require [cljs.core.async :refer [put! chan <! >!]]))
  {:eval    (fn [{:keys [source name] :as to-eval}]
                (cljs/js-eval to-eval))
   :load    load-source
   :context :expr}

  (fn [{:keys [error value]}]
      (if error (do (.log js/console error))
                (prn value))))
