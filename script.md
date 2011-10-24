
# JPA

* Show structure and build
* Deploy using the JBoss command line tools:
       `$ mvn clean package
       $ jboss-admin.sh
       > connect
       > deploy target/ogm-kitchensink.war`
* Demo site - [ogm-kitchensink](http://localhost:8080/ogm-kitchensink)
* Undeploy:
  `> undeploy target/ogm-kitchensink.war
   > quit`


# OGM
* Switch to OGM
* Deploy:
  `$ mvn clean package
   $ mvn jboss-as:deploy`
* Deploy maven plugin


# OpenShift

* Explain OpenShift
* Explain rvm
* Add profile
* Push to Openshift

# Links

* []([AS 7 Command Line Interface](http://www.hibernate.org/subprojects/ogm.html))