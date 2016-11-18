Java Web Service Template - EscobarMiranda.com
===================

> **Description:**

> - Web service template.
> - Using maven, hibernate and jersey.

Required software
-------------
 1. Maven

Setup development environment
-------------

 1. Create database in postgres  
 `CREATE DATABASE sampledb;`  

 2. Clone repo this repo:  
 `git clone https://github.com/kescobar-pernix/pernix-dashboard-backend`

Build the project
-------------

 - Build the project using maven command:
`mvn package`

 - Build the project using maven but without tests run: 
`mvn package -Dmaven.test.skip=true`

- In windows: 
`mvn package "-Dmaven.test.skip=true"`


Run project  
-------------  
- Run project in terminal  
`mvn tomcat7:run`  

- Open url  
`http://localhost:9090/`  

----------
[![EscobarMiranda.com](http://escobarmiranda.com/EscobarMirandaLogoLittle.png)](http://EscobarMiranda.com)