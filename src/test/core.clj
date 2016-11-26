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

(defn find-closest-point[points, reference]
  ; find out how to get the min from dists
  (let [pointsWithDist
    (map points (fn [point, reference]
      (let [xDist (abs (- :x point :x reference))
            yDist (abs (- :y point :y reference))
            dist (+ xDist yDist)]
      (assoc point :distance dist))))]
      (print-str point)
      (apply min (map pointsWithDist (fn[pointWithDist] (:distance pointWithDist))))))

(defn draw [points]
  (let [target-point (last points)
        close-point (find-closest-point points target-point)]
  (point (:x target-point) (:y target-point))
  (triangle (:x target-point) (:y target-point) (:x close-point) (:y close-point) 130 130)))

(defsketch example
  :title "yo"
  :setup setup
  :draw draw
  :size [500 500]
  :features [:keep-on-top]
  :update update
  :middleware [m/fun-mode])

(defn -main [& args])
