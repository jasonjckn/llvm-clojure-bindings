(ns main
  (:require [example-ast])
  (:use [ast ]
        [parser]
        [llvm]
        [clojure.java.shell :only [sh]]))

(defn link-and-run [obj-file]
  (let [{:keys [out err exit]} (sh "/opt/local/bin/llvm-ld" "--native" obj-file)]
    (when (not= exit 0)
      (println "Linker Exit Code: " exit))
    (when (= exit 0)
      (println "Linking binary...")
      (println "Executing binary a.out:")
      (println (:out (sh "./a.out")))
      (println "Finished"))))

(defn run-test [ast]
  (let [[b mymod] (create)]

    (populate! b mymod ast)

    (println (repeat 30 \=))
    (LLVMDumpModule mymod)
    (println (repeat 30 \=))
    (LLVMWriteBitcodeToFile mymod "main.o")
    (link-and-run "main.o")
    (LLVMDisposeModule mymod)))


(defn -main []
  (let [input (slurp "input.txt")]
   (run-test (parse-str input))
   #_ (run-test example-ast/ast1)
    ))



