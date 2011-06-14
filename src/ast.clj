(ns ast
  (:use [llvm]
        [matchure]
        [clojure.walk]))


(defn wrap-value! [b v]
  (cond
   (string? v) (LLVMBuildGlobalString b v "")
   :else  (wrap-value v)))

(def fmap (atom {}))

(defn populate! [b mymod ast]
  (let [build! (fn [f]
                 (cond-match f
                             [:func ?name ?params ?ret & ?body]
                             (do 
                               (let [func (add-function mymod (str name) (eval [ret (vec params)]))]
                                 (swap! fmap #(assoc % name func))
                                 (when (seq body)
                                   (let [block (LLVMAppendBasicBlock func "")]
                                     (LLVMPositionBuilderAtEnd b block)))))

                             [:call ?name & ?args]
                             (do
                                (build-call b (@fmap name) (map (partial wrap-value! b) args)))

                             [:return ?code] (LLVMBuildRet b (wrap-value! b code)))
                 f
                 )]
    (prewalk build! ast)))





