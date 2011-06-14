(ns parser
  (:use [eu.dnetlib.clojure clarsec monad]))

(defn <* [a b] (let-bind [r a
                          _ b] (result r)))

(def p-exp
     (<|> natural
          stringLiteral))

(def p-return
     (let-bind [_ (symb "return")
                n p-exp]
               (result `(:return ~n))))

(def p-call
     (let-bind [name identifier
                args (parens (sep-by p-exp comma))]
               (result `(:call ~(symbol name) ~@args))))

(def p-stmt (<|> p-call p-return))

(def p-func-body (braces (many (<* p-stmt semi))))

(def p-func-proto
     (let-bind [_ (symb "func")
                name (<$> symbol identifier)
                params (parens (<|> (<$> (comp list keyword) (symb "..."))
                                    (sep-by (<$> symbol identifier) comma)))
                ret-type (<$> symbol identifier)]
               (result `(:func ~name ~params ~ret-type))))
(def p-func
     (let-bind [proto p-func-proto
                body p-func-body]
               (result (concat proto body))))

(def parser (many (<|> p-func (<* p-func-proto semi))))

(defn parse-str [str]
  (:value (parse parser str)))

#_ (parse p-call "print(3, 5, \"foo\")")
#_ (parse p-func-proto "func cos(Double) Double")
#_ (parse p-func-proto "func cos(...) Double")
#_ (parse p-func "func main() Integer { print(3); print(3); return 3; } ")
#_ (parse parser input)
