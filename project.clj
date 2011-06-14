(defproject hangman "1.0.0-SNAPSHOT"
  :repositories {"java.net" "http://download.java.net/maven/2/"}

  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]

                 [net.java.dev.jna/jna "3.3.0"]
                 ;[clj-native "0.9.1-SNAPSHOT"]

                 ;[clarsec/clarsec "0.0.1-SNAPSHOT"]
                 [org.clojars.jasonjckn/clarsec "0.0.1-SNAPSHOT"]

                 [matchure "0.10.1"]
                 ]
  :dev-dependencies [[swank-clojure "1.2.1"]]
  :main run)

