(ns test.core
  (:require [quil.core :refer :all]
            [quil.middleware :as m]))

(defn draw-initial-triangle []
  (triangle 250 250 150 200 300 250))

(defn setup []
  (smooth)
  (color-mode :rgb)
  (frame-rate 1)
  (draw-initial-triangle)
  [{:x 3 :y 3}])

(defn update [points]
  (let [
        x (rand-int 501)
        y (rand-int 501)]
  (print-str points)
  (conj points {:x x :y y})))

(defn draw [points]
  (point (:x (last points)) (:y (last points))))

(defsketch example
  :title "yo"
  :setup setup
  :draw draw
  :size [500 500]
  :features [:keep-on-top]
  :update update
  :middleware [m/fun-mode])

(defn -main [& args])
