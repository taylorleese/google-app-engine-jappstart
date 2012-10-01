# Getting Started Guide
jappstart is a Java framework for Google App Engine built on Spring, Spring Security, and Sitemesh. The project aims to be the starting point for Google App Engine Java applications. It's not a framework in the traditional sense -- rather it is designed to provide a solid foundation for GAE/Java applications that can be easily extended by developers.

## Features
* Appstats Support
* Google AJAX Library API (jQuery)
* Gravatar Integration
* JRebel Support
* Local Development Console Support (http://localhost:8080/_ah/admin)
* Maven Support
  * CSS/JS minification via the yuicompressor-maven-plugin
  * Uses the maven-gae-plugin
* Remote API/Bulk Loader Support
* Sitemesh Integration
* Spring 3
  * JPA support
  * JSON/AJAX integration (Jackson)
  * JSR-303 validation
  * Localization support
* Spring Security 3
  * Authentication
  * Expression based access control
  * Fully integrated with the App Engine Datastore and Memcache
  * Login/create account functionality with e-mail confirmation
  * Remember Me
  * Support for hierarchical roles
* Static Error Handler Support
* Task Queue Support
* URL Rewrite Integration

## Versions
* Goole App Engine SDK for Java 1.7.0
* jQuery 1.7.1
* Maven GAE Plugin 0.9.2
* Spring 3.1.0
* Spring Security 3.1.0

## Configure Property Files
Configure the following properties in `filter-local.properties`, `filter-dev.properties`, and `filter-prod.properties`.

    google.app.id
    google.jsapi.http.key
    google.jsapi.https.key
    application.hostname
    mail.fromAddress

Notes:

* See [Sending Mail](http://code.google.com/appengine/docs/java/mail/overview.html#Sending_Mail) for details regarding e-mail address restrictions.
* JSAPI keys can be obtained [here](http://code.google.com/apis/ajaxlibs/documentation/index.html#sign_up_for_an_api_key).

## Configure Settings
Create a `settings.xml` and a `settings-security.xml` in your local .m2 folder. See [Maven - Password Encryption](http://maven.apache.org/guides/mini/guide-encryption.html) for instructions on how to encrypt your GAE password.

```xml
<!-- settings.xml -->
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
    http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <servers>
    <server>
      <id>yourapp.com</id> <!-- this should match the google.app.id property and is the GAE application id -->
      <username>you@yourapp.com</username> <!-- GAE username -->
      <password>encryptedpass</password> <!-- Encrypted GAE password -->
    </server>
  </servers>

</settings>
```

```xml
<!-- settings-security.xml -->
<settingsSecurity>
  <master>masterpass</master> <!-- Encrypted master password -->
</settingsSecurity>
```

## Build/Deploy
* Run `mvn clean package -P local` to create a new local build
* Run `mvn gae:run -P local` to run locally
* Run `mvn gae:stop -P local` to stop the local jetty server
* Run `mvn clean package -P dev` to create a new dev build
* Run `mvn gae:deploy -P dev` to deploy to your dev app engine app
* Run `mvn clean package -P prod` to create a new prod build
* Run `mvn gae:deploy -P prod` to deploy to your prod app engine app

## Localization
To demonstrate the localization functionality just append the following to any url: 

    /some/url?locale=en_US or /some/url?locale=tl_PH. 
    
To support additional locales just create a new `messages_xx_YY.properties` and set the new locale via:

    /some/url?locale=xx_YY.

## JRebel Usage
* Install the [JRebel Nightly Build](http://www.zeroturnaround.com/jrebel/early-access/).
* Define a `REBEL_HOME` environment variable
* Run `mvn clean compile -P local` to create a new local build
* Run `mvn gae:run -P local-jrebel` to run locally with JRebel support

## Remote API/Bulk Loader Usage
Below is an example of how to use the bulk loader to copy all data from the production server to the local development server. Omit the "--kind" option to dump/restore all kinds. See [this](http://code.google.com/appengine/docs/python/tools/uploadingdata.html) for more info.

Backup:

    appcfg.py download_data --application=<app-id> --kind=UserAccount --url=https://<app-id>.appspot.com/remote_api --filename=backup.dat

Restore:

    appcfg.py upload_data --application=<app-id> --kind=UserAccount --url=http://localhost:8080/remote_api --filename=backup.dat

## Google Plugin for Eclipse Usage
See the [Google App Engine Blog](http://googlewebtoolkit.blogspot.com/2010/08/how-to-use-google-plugin-for-eclipse.html).

# Copyright
Copyright (C) 2010-2012 Taylor Leese

This file is part of jappstart.

jappstart is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

jappstart is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with jappstart.  If not, see <http://www.gnu.org/licenses/>.
