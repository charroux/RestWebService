# RestWebService


sudo yum install java-1.7.0-openjdk-devel
yum install wget
mkdir database
cd database
wget http://sourceforge.net/projects/hsqldb/files/hsqldb/hsqldb_1_8_1/hsqldb_1_8_1_3.zip
yum install unzip
unzip hsqldb_1_8_1_3.zip
nohup java -cp database/hsqldb/lib/hsqldb.jar org.hsqldb.Server
cd ..
mkdir gradle
cd gradle
wget https://services.gradle.org/distributions/gradle-2.8-bin.zip
unzip gradle-2.8-bin.zip
cd ..

vi .bashrc

ajouter à la fin la commande :

export PATH=$PATH:/root/gradle/gradle-2.8/bin

en utilisant les commandes vi

o 	ouvre une nouvelle ligne en dessous de la ligne courante
i 	insère du texte avant le curseur
ESC pour quitter le mode INSERT
:w 	enregistre le fichier courant
:q 	quitte vi

shutdown


démarrer apache tomcat : startup dans le bin d'apache tomcat
stopper apache tomcat shutdown

déployer avec Gradle : gradle cargoDeployRemote
