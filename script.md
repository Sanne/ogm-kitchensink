# JPA

* Show structure and build
* Run Arquillian test
* Deploy using the JBoss command line tools: 
 
        $ mvn clean package
        $ jboss-admin.sh
        > connect
        > deploy target/ogm-kitchensink.war

* Demo site - [ogm-kitchensink](http://localhost:8080/ogm-kitchensink)
* Create some test members:

        $ruby member-generator.rb -a http://localhost:8080/ogm-kitchensink -c 20`
        
* Undeploy:

        > undeploy target/ogm-kitchensink.war
        > quit`

# OGM
* Switch to OGM
       * Change persistence provider in _persistence.xml_
       * Enable Hibernate 3 module
       * Switch to UUID as entity ids
       * Switch to Seach for querying
* Deploy w/ maven plugin:

        $ mvn clean package
        $ mvn jboss-as:deploy

# OpenShift

* Explain OpenShift
* Explain rvm
* Add profile
* Push to Openshift

# Links

* ([AS 7 Command Line Interface](http://www.hibernate.org/subprojects/ogm.html)