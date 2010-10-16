(ns barista
  (:use clojure.pprint)
  (:import (java.text DecimalFormat)))

;; Consider restructuring the ingredients sub-map to have negative values so you apply it to your
;; ingredients and the function would return a new ingredients list
;; Throw an exception if it couldn't make that drink
(def cookbook {:Coffee          {:coffee 3, :sugar 1, :cream 1},
	       :Decaf-Coffee    {:decaf 3, :sugar 1, :cream 1},
	       :Caffe-Late      {:espresso 2, :steamed-milk 1},
	       :Caffe-Americano {:espresso 3},
	       :Caffe-Moca      {:espresso 1, :cocoa 1, :steamed-milk 1, :cream 1},
	       :Cappuccino      {:espresso 2, :steamed-milk 1, :foamed-milk 1} })

(def menu {1  :Coffee,
	   2  :Decaf-Coffee,
	   3  :Caffe-Late,
	   4  :Caffe-Americano,
	   5  :Caffe-Moca,
	   6  :Cappuccino })

(def cost {:coffee        0.75,
	   :decaf         0.75,
	   :sugar         0.25,
	   :cream         0.25,
	   :steamed-milk  0.35,
	   :foamed-milk   0.35,
	   :espresso      1.00,
	   :cocoa         0.90,
	   :whipped-cream 1.00 })

(def inventory {:coffee        10,
		:decaf         10,
		:sugar         10,
		:cream         10,
		:steamed-milk  10,
		:foamed-milk   10,
		:espresso      10,
		:cocoa         10,
		:whipped-cream 10 })

(def drink-name {:Coffee          "Coffee",
		 :Decaf-Coffee    "Decaf Coffee",
		 :Caffe-Late      "Caffe Late",
		 :Caffe-Americano "Caffe Americano",
		 :Caffe-Moca      "Caffe Moca",
		 :Cappuccino      "Cappuccino" })

(def ingredient-name {:coffee        "coffee",
		      :decaf         "decaf coffee",
		      :sugar         "sugar",
		      :cream         "cream",
		      :steamed-milk  "steamed milk",
		      :foamed-milk   "foamed milk",
		      :espresso      "espresso",
		      :cocoa         "cocoa",
		      :whipped-cream "whipped cream" })

(defn print-inventory [inventory]
  (do
    (println "Inventory:")
    (doseq [[item count] inventory]
      (println (str (ingredient-name item) "," count)))))

(defn price-of-drink [drink]
  (reduce +
	  (map (fn [ingredient] (* (cost (key ingredient)) (val ingredient)))
	       (cookbook drink))))

(defn price-of-drink [cookbook drink]
  (reduce +
	  (map (fn [[ingredient quantity]] (* (cost ingredient) quantity))
	       (cookbook drink))))
  
(defn print-menu [menu]
  (do
    (println "Menu:")
    (doseq [[number drink] menu]
      (println (str number ", " (drink-name drink) ", $"
		    (let [currency (DecimalFormat. "0.00")]
		      (. currency format (price-of-drink drink))))))))


(defn get-drink [drink-number] (menu drink-number))


(defn reduce-inventory-by [inventory drink]
  "Produces a new inventory data structure that has the ingredients for the drink reduced."
  (into {}
   (map (fn [[ingredient quantity]]
	  (let [amount-used (ingredient (cookbook drink)), ingredient-name (ingredient-name ingredient)]
	    (if (and (not (nil? amount-used)) (> quantity amount-used))
	      (vector ingredient (- quantity amount-used))
	      (vector ingredient quantity))))
	inventory)))

(def inventory (ref {:coffee        10,
		     :decaf         10,
		     :sugar         10,
		     :cream         10,
		     :steamed-milk  10,
		     :foamed-milk   10,
		     :espresso      10,
		     :cocoa         10,
		     :whipped-cream 10 }))


(defn has-enough? [[ingredient amount]]
  (dosync (> (ingredient @inventory) amount)))

(defn ingredients-present? [inventory drink]
  "Returns true if the inventory have the necessary ingredients in order to make a drink"
  (every? true?
	  (map has-enough?
	       (cookbook drink))))

(defn reduce-inventory-by-assoc [inventory drink]
  "Produces a new inventory data structure minus the ingredients for the drink."
  (assoc inventory ))
	
	
(defn reduce-inventory-by-ref [inventory drink]
  "Reduced the inventory protected by a ref"
  (dosync
   (alter inventory assoc args...)))

(defn reduce-inventory-by [inventory drink]
  "Produces a new inventory data structure minus the ingredients for the drink."
  (into {}
   (map (fn [[ingredient quantity]]
	  (let [amount-used (ingredient (cookbook drink)), ingredient-name (ingredient-name ingredient)]
	    (if (and (not (nil? amount-used)) (> quantity amount-used))
	      (vector ingredient (- quantity amount-used))
	      (vector ingredient quantity))))
	inventory)))

(defn dont-know-what-to-call-this-yet [[ingredient quantity]]
	  (let [amount-used (ingredient (cookbook drink)), ingredient-name (ingredient-name ingredient)]
	    (if (and (not (nil? amount-used)) (> quantity amount-used))
	      (vector ingredient (- quantity amount-used))
	      (vector ingredient quantity))))

		    
(defn reduce-stock [[ingredient quantity]]
  (do
    (println (str ingredient ", " quantity))
    (let [amount-used (ingredient {:coffee 3, :sugar 1, :cream 1})]
      (if (and (not (nil? amount-used)) (> quantity amount-used)) (println (str "quantity > " amount-used))))))



;; Look the drink up in the menu
;; Based off of the keyword reduce the inventory by its ingredients
;; If all the ingredients are present
(defn purchase-drink [drink-number]

  
  )



