(ns foo.http
  (:require
    [cljs.core.async :refer [put! chan]]
    [goog.net.XhrIo :as xhr]))

(defn GET [path]
  (let [c (chan)]
    (xhr/send path #(put! c (-> % .-target .getResponseText)) "GET")
    c))