(ns example-ast)

(def ast1
     '((:func foo [] Integer
              (:call printf "bar\n")
              (:return 3))

       (:func main [] Integer
              (:call printf "foo\n")
              (:call printf "hello\n")
               (:call foo)
              (:return 1))))
