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
  [])

(defn update [points]
  (let [x (rand-int 501)
        y (rand-int 501)]
  (conj points {:x x :y y})))

(defn maybe-trim[points]
  (if (> (count points) 2) (nthrest points 1) points))

(defn find-closest-point [points, reference]
  (let [pointsWithDist
    (map (fn [point]
      (let [xDist (abs (- (:x point) (:x reference)))
            yDist (abs (- (:y point) (:y reference)))
            dist (+ xDist yDist)]
      (assoc point :distance dist))) points)]
    (into [] (reduce (fn [acc curr]
      (if (or
            (> (:distance (first acc)) (:distance curr))
            (> (:distance (nth acc 1)) (:distance curr)))
        (maybe-trim(conj acc curr)))
        acc
    ) [{:distance 500} {:distance 500}] pointsWithDist))))


(defn draw [points]
  (let [target-point (last points)
        close-points (find-closest-point points target-point)]
  (point (:x target-point) (:y target-point))
  (triangle (:x target-point) (:y target-point)
            (:x (first close-points)) (:y (first close-points))
            (:x (nth close-points 1)) (:y (nth close-points 1)))))

(defsketch example
  :title "yo"
  :setup setup
  :draw draw
  :size [500 500]
  :features [:keep-on-top]
  :update update
  :middleware [m/fun-mode])

(defn -main [& args])
