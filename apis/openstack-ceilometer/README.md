#OpenStack Ceilometer
================

The Ceilometer project aims to deliver a unique point of contact for billing systems to acquire all of the measurements they need to establish customer billing, 
across all current OpenStack core components with work underway to support future OpenStack components

##Running Live Tests
===
To run the Ceilometer live tests, execute the following command with your credentials and authentication endpoint:

    $ mvn clean install -Plive -Dtest.openstack-ceilometer.identity=<username> -Dtest.openstack-ceilometer.credential=<password> -Dtest.openstack-ceilometer.endpoint=<keystone-auth-url>

##Production ready?
===
No. The OpenStack Ceilometer API is a beta API and is subject to change during it's development. APIs have `@Beta` annotations where applicable.
