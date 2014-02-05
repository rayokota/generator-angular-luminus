(ns <%= baseName %>.test.handler
  (:use clojure.test
        ring.mock.request
        <%= baseName %>.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
