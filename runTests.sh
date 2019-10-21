#!/bin/sh

mvn clean test -Dtest=SendForm allure:serve -Dmaven.test.failure.ignore=true