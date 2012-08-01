OpenYourEyes
============


What is
-------
Yet another augmented reality framework for Android

Introduction
------------
The basic idea is giving to develpers the possibility ignoring all the aspects based on GIS. So a developer can ignore the GPS, gyroscope, compass sensors and everything used to draw on a Canvas the augmented reality items.

How it works (if it works)
--------------------------
The main architecture used by openyoureyes it's based on MVC pattern: a Controller read the configuration file under res/xml/configure.xml with the _n_ content providers (Model) of geo datas, then it informs the canvas(View) that some new items are ready to draw. Below the architecture schema:

![Architecture](https://github.com/paspao/openyoureyes/raw/master/arc.png "Architecture")

You can add your layers by adding a new ContentProvider into xml file, like the example

	<ContentProviderOfGeoItems class="it.eng.na.eyes.test.panoramio.PanoramioModel"/>

A ContentProvider must implement _ContentProviderOfGeoItems_ interface or more simply extends _AbstractContentProviderOfGeoItems_

In addition you have to define your geo item implementing _GeoItem_ or simply extending _AbstractGeoItem_, so you can define what drawing and the onclick action.


Known bugs
==========
* Managemnt of the screen rotation