# Salesloft_Assignment

## Overview
- This is a Java based automation framework. It uses Maven to build and run, Selenium webdriver to interact with the browser and Junit5 to run tests and report results.

## Assumptions before running tests
- Source code cloned from the repository.
- Installed Java JDK 17.
- Installed Maven.
- Installed IntelliJ Community Edition.

## To run tests
- Go to the directory containing pom.xml in command propmt and run `mvn clean test-compile` to make sure tests compile.
- Run `mvn clean test` to run tests.
- `mvn clean test surefire-report:report` runs tests and writes test results to .xml and .txt files in \target\surefire-reports folder.
- To get a html test report run `mvn surefire-report:report-only` after running the above command. Go to \target\site folder to view the html file.

## Test cases
- There are 6 test cases in `AmazonSearchTest` class
  - `searchProduct` : Opens amazon.com and searches for "Magnifying glass" under "Industrial & Scientific" category. Asserts that product name and category are popluated after performing a search.
  - `findFilterByPrimeWithoutLoggingIn`: Performs a search and checks for Prime only filter in the seaarch results page. This test case fails as Prime filter is displayed only for logged in prime users.
  - `searchProductAsLoggedInUser`: Performs search as a logged in prime user. Checks that product name, category and hello user greeting are populated correctly.
  - `searchProductAndFilterByPrime`: Searches for "Magnifying glass" under "Industrial & Scientific" category as logged in user and selects Prime only filter. Checks if all the search results have prime logo.
  This test case sometimes fails as few search results have "Free delivery for prime members" text instead of a prime logo.
  - `searchProductAndAddToCart`: Performs a search, selects a search result, adds quantity of 7 to cart and navigates to cart page. Checks if the right quantity is added in the cart page.
  - `addAProductToCartAndCompare`: After adding items to cart, clicks on Compare similar items link and checks if the items in the pop up are similar to product in the cart.
  
## To run tests in different environments
 - Change the `environment` property in `FrameworkConfig.properties` file. For example, if environment is set to `stage`, url is constructed as `https://stage.amazon.com`.
