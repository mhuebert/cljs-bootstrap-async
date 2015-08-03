(ns foo.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
    [foo.async]
    [foo.http :refer [GET]]
    [cljs.core.async :refer [put! chan <!]]
    [cljs.js :as cljs]
    [cljs.reader :refer [read-string]]))

(enable-console-print!)

(defn load-cache [cstate s]
  (go
    (let [path (str "js/compiled/" (clojure.string/replace (name s) "." "/") ".cljs.cache.edn")
          cache-edn (<! (GET path))
          cache (read-string cache-edn)]
      (cljs.js/load-analysis-cache! cstate s cache))))

(def compiler-opts {:eval #(do (println (str "source: " (:source %))) (cljs/js-eval %))
                    :context :expr})

(defn eval
  [st forms opts]
  (let [c (chan)]
    (cljs/eval st forms opts (fn [{:keys [value error] :as result}]
                               (if error (.log js/console (some-> result :error .-cause .-stack)))
                               (put! c (or result false))))
    c))

(go
  (let [st (cljs/empty-state)
        eval-in-ns (fn [ns sexpr] (eval st sexpr (assoc compiler-opts :ns ns)))]

    (<! (load-cache st 'foo.async))

    ; works - call fn in 'foo.async
    (<! (eval-in-ns 'foo.async
                    '(greeting "fred.") ))

    ; works - call fn in 'foo.async which uses `go` macro
    (<! (<! (eval-in-ns 'foo.async
                        '(async-greeting "susan."))))

    ; works - use a referred var in 'foo.async
    (<! (eval-in-ns 'foo.async
                    '(GET "http://www.apple.com")))

    ; does not work - eval `go` macro in 'foo.async
    (<! (eval-in-ns 'foo.async
                    '(go (<! (wait 1000 "async worked")))))))