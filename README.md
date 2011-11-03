# OGM Kitchensink

## What is it?

ogm-kitchensink is a [OGM](http://www.hibernate.org/subprojects/ogm.html) demo app for [AS 7](http://www.jboss.org/jbossas) based on
[kitchensink](https://github.com/jbossas/quickstart/tree/master/kitchensink)

# Prerequisites

* JDK 6
* [Maven 3](http://maven.apache.org/)
* [Git](http://git-scm.com/)
* [JBoss AS 7 "Zap"](http://www.jboss.org/jbossas)


## How to build it

* Set the environment variable JBOSS_HOME and ensure that it points to the installation directory of a JBoss AS 7 installation (tested against JBoss AS 7.0.1.Final "Zap")
* Install the Hibernate 3 and OGM modules (needs to be only executes once)

         $ mvn groovy:execute

* Start JBoss AS 7

         $ $JBOSS_HOME/bin/standalone.sh

* Build:

         $ mvn clean package

* Deploy:

         $ mvn jboss-as:deploy

## Running tests

### Managed container

    $ mvn test

### Remote container

    $ mvn test -DremoteTests=true

## How to deploy on OpenShift Express

* Sign up for OpenShift account at - [https://openshift.redhat.com]([https://openshift.redhat.com])
* Install OpenShift Express command line tools

        $ gem install rhc

* Create OpenShift domain and app

        $ rhc-create-domain -n <domain>
        $ rhc-create-app -a <app> -t jbossas-7.0 --nogit

* Add the git repo created by rhc-create-app as remote

        $ git remote add openshift <repo-url>

* Push to Openshift

        $ git push -f openshift master

* Demo site - http://\<app\>-\<domain\>.rhcloud.com