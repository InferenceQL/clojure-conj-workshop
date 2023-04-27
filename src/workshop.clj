(ns workshop
  (:require [gen]
            [clojure.string :as str]
            [gen.distribution.fastmath :as fastmath]
            [gen.dynamic :refer [gen]]
            [inferenceql.query.db :as db]
            [inferenceql.query.permissive :as iql-query]
            [nextjournal.clerk :as clerk]))

^{::clerk/visibility {:code :hide :result :hide}}
(def data
  [{'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm false}
   {'high-salary false 'clojure false 'experienced false 'urm true}
   {'high-salary false 'clojure false 'experienced false 'urm true}
   {'high-salary false 'clojure false 'experienced false 'urm true}
   {'high-salary false 'clojure false 'experienced false 'urm true}
   {'high-salary false 'clojure false 'experienced false 'urm true}
   {'high-salary false 'clojure false 'experienced true 'urm false}
   {'high-salary false 'clojure false 'experienced true 'urm false}
   {'high-salary false 'clojure false 'experienced true 'urm false}
   {'high-salary false 'clojure false 'experienced true 'urm false}
   {'high-salary false 'clojure false 'experienced true 'urm false}
   {'high-salary false 'clojure false 'experienced true 'urm false}
   {'high-salary false 'clojure false 'experienced true 'urm false}
   {'high-salary false 'clojure false 'experienced true 'urm false}
   {'high-salary false 'clojure false 'experienced true 'urm true}
   {'high-salary false 'clojure false 'experienced true 'urm true}
   {'high-salary false 'clojure true 'experienced false 'urm false}
   {'high-salary false 'clojure true 'experienced false 'urm false}
   {'high-salary false 'clojure true 'experienced false 'urm false}
   {'high-salary false 'clojure true 'experienced false 'urm false}
   {'high-salary false 'clojure true 'experienced false 'urm false}
   {'high-salary false 'clojure true 'experienced false 'urm false}
   {'high-salary false 'clojure true 'experienced false 'urm false}
   {'high-salary false 'clojure true 'experienced false 'urm true}
   {'high-salary false 'clojure true 'experienced false 'urm true}
   {'high-salary false 'clojure true 'experienced false 'urm true}
   {'high-salary false 'clojure true 'experienced false 'urm true}
   {'high-salary false 'clojure true 'experienced true 'urm false}
   {'high-salary false 'clojure true 'experienced true 'urm false}
   {'high-salary false 'clojure true 'experienced true 'urm false}
   {'high-salary false 'clojure true 'experienced true 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm false}
   {'high-salary true 'clojure false 'experienced false 'urm true}
   {'high-salary true 'clojure false 'experienced true 'urm false}
   {'high-salary true 'clojure false 'experienced true 'urm false}
   {'high-salary true 'clojure false 'experienced true 'urm false}
   {'high-salary true 'clojure false 'experienced true 'urm false}
   {'high-salary true 'clojure false 'experienced true 'urm false}
   {'high-salary true 'clojure false 'experienced true 'urm false}
   {'high-salary true 'clojure false 'experienced true 'urm false}
   {'high-salary true 'clojure false 'experienced true 'urm true}
   {'high-salary true 'clojure true 'experienced false 'urm false}
   {'high-salary true 'clojure true 'experienced false 'urm false}
   {'high-salary true 'clojure true 'experienced false 'urm false}
   {'high-salary true 'clojure true 'experienced false 'urm false}
   {'high-salary true 'clojure true 'experienced false 'urm false}
   {'high-salary true 'clojure true 'experienced false 'urm false}
   {'high-salary true 'clojure true 'experienced false 'urm false}
   {'high-salary true 'clojure true 'experienced false 'urm false}
   {'high-salary true 'clojure true 'experienced false 'urm false}
   {'high-salary true 'clojure true 'experienced false 'urm false}
   {'high-salary true 'clojure true 'experienced false 'urm false}
   {'high-salary true 'clojure true 'experienced false 'urm true}
   {'high-salary true 'clojure true 'experienced true 'urm false}
   {'high-salary true 'clojure true 'experienced true 'urm false}
   {'high-salary true 'clojure true 'experienced true 'urm false}
   {'high-salary true 'clojure true 'experienced true 'urm false}
   {'high-salary true 'clojure true 'experienced true 'urm false}
   {'high-salary true 'clojure true 'experienced true 'urm false}
   {'high-salary true 'clojure true 'experienced true 'urm false}
   {'high-salary true 'clojure true 'experienced true 'urm true}])

^{::clerk/visibility {:code :hide :result :hide}}
(def background [
        {'probability 0.4 'high-salary false 'urm false}
        {'probability 0.2 'high-salary false 'urm true}
        {'probability 0.3 'high-salary true  'urm false}
        {'probability 0.1 'high-salary true  'urm true}])

^{::clerk/visibility {:code :hide :result :hide}}
(def skills [
        {'probability 0.33 'experienced false 'clojure false}
        {'probability 0.01 'experienced false 'clojure true }
        {'probability 0.33 'experienced true  'clojure false}
        {'probability 0.33 'experienced true  'clojure true }])


^{::clerk/visibility {:code :hide :result :hide}}
(def db (-> (db/empty)
            (db/with-table 'data data)
            (db/with-table 'skills skills)
            (db/with-table 'background background)
            (atom)))

^{::clerk/visibility {:code :hide :result :hide}}
(defn query [q]
  (let [result (iql-query/query (str/trim q) db)]
    (when-not (nil? result) (clerk/table result))))



;; # Exercises
;;; Basic documentation of the language is here: https://inferenceql-documentation.fly.dev/query/main/iql-permissive.html

;; Queries can be run via:
^{::clerk/visibility {:code :hide}}
(clerk/md "(query \"my query\")")

;; ### Exercise: Show the generative table called "background"

;; ### Solution:
(query "SELECT * FROM background")

;;In the above the rows correspond to the probabilities of generating
;;certain values for columns. With probability 0.4 we generate rows where
;;`high-salary` is false and `urm` is false.

;; ### Exercise: Show generate synthetic data from this query.

;; Hint: The keyword `UNDER` turns a generative table expression into a model
;; expression.

;; ### Solution:
(query "
  SELECT *
  FROM
    GENERATE *
    UNDER GENERATIVE TABLE background
  LIMIT 5")

;; ## Inference
;; ### Exercise: Marginalization

;; Implement the following via a SQL query on `background`
^{::clerk/visibility {:code :hide}}
(clerk/tex "P(X=x) = \\sum_{y} P(X=x, Y=y)")

;; ### Solution:
(query "
  SELECT
    SUM(probability) as marginal, urm
  FROM background
  GROUP BY urm")

;; A keyword allows you to assess this probability directly
(query "
  SELECT
       PROBABILITY OF urm UNDER (GENERATIVE TABLE background) AS marginal,
       urm
  FROM
    SELECT DISTINCT urm FROM background")

;; ### Exercise: Explain why we use `SELECT DISTINCT urm FROM background` here.

;; ### Solution: Messy name-matching; like in SQL.

;; ## Conditioning
;; What's the probability of an event, given another event? According to Bayes' Rule it's:
^{::clerk/visibility {:code :hide}}
(clerk/tex "P(X=x | Y=y) = \\frac{P(X=x, Y=y)}{P(Y=y)}")
;; ### Exercise: how do you generate conditional samples with this table?

;; Hint: check the reference manual for `GIVEN`.


;; ### Solution:
(query "
  SELECT *
  FROM
    GENERATE *
    UNDER GENERATIVE TABLE background
    GIVEN urm=true
  LIMIT 5")

;; ## Synthetic data generation

;; This allows you to generate synthetic data jointly from independent distributions

(query "
  CREATE TABLE joint AS
    SELECT
      background.probability * skills.probability AS probability,
      experienced,
      high-salary,
      clojure,
      urm
    FROM background CROSS JOIN skills!")

;; ### Exercise: Generate data from the new generative table `joint`.

;; ### Solution:
(query "
  SELECT *
  FROM
    GENERATE *
    UNDER GENERATIVE TABLE joint
  LIMIT 5")

;; ### Exercise: Use the generative table `joint` to generate a synthetic data set that is balanced in that is has 50 % rows with `urm = true` and 50 % `urm = false`.

;; ### Solution:
(query "
  INSERT INTO

    SELECT *
    FROM
      (GENERATE *
       UNDER GENERATIVE TABLE joint
       GIVEN urm = true)
    LIMIT 50

    SELECT *
    FROM
      (GENERATE *
       UNDER GENERATIVE TABLE joint
       GIVEN urm = false)
    LIMIT 50")

;; ## Editing and customization

;; The model above implies that one's salary depends on the fact that one is a
;; URM. That's problematic, even that it's the historical data we learned from.
;; If we want to change that, we can create new tables and generate from the
;; joint distribution of them.


;; ### Exercise: create tables for the marginal distributions of `urm` and `high-salary`. Then join them to create a new generative table that treats independently.

;; ### Solution:
(query "
  CREATE TABLE minority AS
    SELECT
      SUM(probability) as marginal, urm
    FROM background
    GROUP BY urm!")

(query "
  CREATE TABLE salary AS
    SELECT
      SUM(probability) as marginal, high-salary
    FROM background
    GROUP BY high-salary!")

(query "
  CREATE TABLE new-background AS
    SELECT
      minority.marginal * salary.marginal AS probability,
      high-salary,
      urm
    FROM salary CROSS JOIN minority!")

;; ### Exercise: Write a few generate queries to validate your solution.


;; ### Solution:
(query "
  SELECT COUNT(*), high-salary, urm FROM (
    SELECT * FROM
      (GENERATE * UNDER GENERATIVE TABLE new-background GIVEN urm=true)
    LIMIT 10000)
  GROUP BY high-salary")

(query "
  SELECT COUNT(*), high-salary, urm FROM (
    SELECT * FROM
      (GENERATE * UNDER GENERATIVE TABLE new-background GIVEN urm=false)
    LIMIT 10000)
  GROUP BY high-salary")

;; Compare the above to the below:

(query "
  SELECT COUNT(*), high-salary, urm FROM (
    SELECT * FROM
      (GENERATE * UNDER GENERATIVE TABLE background GIVEN urm=true)
    LIMIT 10000)
  GROUP BY high-salary")

(query "
  SELECT COUNT(*), high-salary, urm FROM (
    SELECT * FROM
      (GENERATE * UNDER GENERATIVE TABLE background GIVEN urm=false)
    LIMIT 10000)
  GROUP BY high-salary")

;; ## Anomaly detection

;; Assume this is your data
(clerk/table data)

;; ### Exercise: Return the three least likely values for high-salary and
;; Clojure in this data, given the row-wise context, i.e the values of for `urm`
;; and `experienced`

;; ### Solution:
(query "
  SELECT
       PROBABILITY OF high-salary AND clojure UNDER
        (GENERATIVE TABLE
          (
            SELECT
                background.probability * skills.probability AS probability,
                experienced,
                high-salary,
                clojure,
                urm
            FROM background CROSS JOIN skills
       )
       GIVEN urm AND experienced) AS probability_anomaly,
       experienced,
       high-salary,
       clojure,
       urm
  FROM data
  ORDER BY probability_anomaly
  LIMIT 3")

;; ## How do I get a data generator?

;; How would you, from scratch, create a model that generates synthetic data
;; that's consistent with the real data?

;; One way to is by counting.

;; ### Exercise: In pure Clojure, try to create a generative table via counting
;; values in `data`.

;; ### Solution:
(defn frequency-table
  [rows]
  (->> (frequencies rows)
       (map (fn [[row count]]
              (assoc row 'count count)))))

(defn normalize
  [col rows]
  (let [normalizing-constant (transduce (map #(get % col))
                                        +
                                        rows)]
    (mapv (fn [row]
            (update row col / normalizing-constant))
          rows)))


(clerk/table (normalize 'count (frequency-table data)))

;; ### Exercise: What's the problem with this approach?

;; ### Solution: Overfitting (+independence), privacy
