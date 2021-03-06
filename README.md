# OGM Kitchensink

## What is it?

ogm-kitchensink is a [OGM](http://www.hibernate.org/subprojects/ogm.html) demo app for [AS 7](http://www.jboss.org/jbossas) based on
[kitchensink](https://github.com/jbossas/quickstart/tree/master/kitchensink)

## How to build it

* Set the environment variable JBOSS_HOME and ensure that it points to the installation directory of a JBoss AS 7 installation (tested against JBoss AS 7.0.1.Final "Zap")
* Build:

         $ mvn clean package

* Start JBoss AS 7

         $ $JBOSS_HOME/bin/standalone.sh

* Deploy:

         $ mvn jboss-as:deploy

## Running tests

### Managed container

    $ mvn test

### Remote container

    $ mvn test -DremoteTests=true

## How to run it ?

* Start AS 7: `$JBOSS_AS/bin/standalone.sh`
* Build and deploy the webapp: `mvn package jboss-as:deploy`

See also [script.md]
