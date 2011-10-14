# OGM Kitchensink

## What is it?

ogm-kitchensink is a [OGM](http://www.hibernate.org/subprojects/ogm.html) demo app for [AS 7](http://www.jboss.org/jbossas) based on
[kitchensink](https://github.com/jbossas/quickstart/tree/master/kitchensink)

## How to build it

* Set that the environment variable JBOSS_HOME is set and points to the installation directory of a JBoss AS 7 installation
*     mvn package
                                                  
                                                  
## Running tests

### Managed container

### Remote container

## How to run it ?

### Locally

* Start AS 7
    $JBOSS_AS/bin/standalone.sh

* Build and deploy the webapp
    mvn package jboss-as:deploy

### OpenShift
