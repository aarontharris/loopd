loopd
=====

Loopd Server v0.1 BETA

Wiki @ https://github.com/aarontharris/loopd/wiki

Installation of loopd server:
=============================

Download MongoDB for your OS @ http://www.mongodb.org/downloads<br>

Create a home for MongoDB<br>
``$> mkdir ~/dev/tools/mongo``<br>
``$> mkdir ~/dev/tools/mongo/data``

Move mongodb into tools/mongo<br>
``$> cd ~/dev/tools/mongo``<br>
``$> cp -R ~/Downloads/mongodb-osx-x86_64-2.4.6 ./``

Set Mongo Environment (you can copy and paste -- it should run?)<br>
```
echo "" >> ~/.bash_profile
echo "# MONGO ENV" >> ~/.bash_profile
echo "export MONGO_PORT=26017" >> ~/.bash_profile
echo "export MONGO_HOME='~/dev/tools/mongo/mongodb-osx-x86_64-2.4.6'" >> ~/.bash_profile
echo "export MONGO_DATA='~/dev/tools/mongo/data'" >> ~/.bash_profile
echo "export PATH=$PATH:$MONGO_HOME/bin" >> ~/.bash_profile
echo "" >> ~/.bash_profile
source ~/.bash_profile
```

Run MongoDB with: (lets call this alias 'mongorun')<br>
``mongod --port 26017 --dbpath $MONGO_DATA/loopd --auth --setParameter supportCompatibilityFormPrivilegeDocuments=0``



Pull down the server code<br>
``$> git clone git@github.com:aarontharris/loopd.git``
<br>
<br>
Pull down libraries and build using maven<br>
``$> mvn clean install``


