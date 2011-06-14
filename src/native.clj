(ns native
  (:import com.sun.jna.Pointer))

(System/setProperty "jna.library.path" "/opt/local/lib:/usr/lib")

(defn import-llvm-c-func [lib name ret-type num-args]
  (let [fn-name (symbol name)]
    (eval
     `(let [function# (com.sun.jna.Function/getFunction ~lib ~name)]
        (defn ~fn-name [& args#]
          (assert (= ~num-args (count args#)))
          (.invoke function# ~ret-type (to-array args#)))))))

;; -------------------
;; Deprecated
(defmacro llvm-call [func ret & args] 
  `(let [library#  "LLVM-2.9"
         function# (com.sun.jna.Function/getFunction library# ~(str "LLVM" func))] 
     (.invoke function# ~ret (to-array [~@args]))))

(defmacro jna-call [lib func ret & args] 
  `(let [library#  (name ~lib)
         function# (com.sun.jna.Function/getFunction library# ~func)] 
     (.invoke function# ~ret (to-array [~@args]))))

(defmacro jna-malloc [size] 
  `(let [buffer# (java.nio.ByteBuffer/allocateDirect ~size)
         pointer# (com.sun.jna.Native/getDirectBufferPointer buffer#)]
     (.order buffer# java.nio.ByteOrder/LITTLE_ENDIAN)
     {:pointer pointer# :buffer buffer#}))

